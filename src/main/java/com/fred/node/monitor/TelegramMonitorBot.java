package com.fred.node.monitor;

import com.fred.node.chain.StatusService;
import com.fred.node.chain.model.Transaction;
import com.fred.node.monitor.model.*;
import com.fred.node.price.PriceService;
import com.fred.node.price.TokenPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
 */
@Service
@Qualifier("telegramBot")
public class TelegramMonitorBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd");
    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    private String botToken;
    private String botName;
    private Long chatID;

    @Autowired
    @Qualifier("monitorService")
    private MonitorService monitorService;

    @Autowired
    @Qualifier("priceService")
    public PriceService priceService;

    private boolean firstMessage= true;

    public TelegramMonitorBot(
            @Value("${telegram.token}") String token,
            @Value("${telegram.name}") String name,
            @Value("${telegram.chatId}") Long chatId) {
        this.botToken = token;
        this.botName = name;
        this.chatID = chatId;

        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for receiving messages.
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if(!this.chatID.equals(update.getMessage().getChatId()) ) {
                log.info("chatID "+update.getMessage().getChatId() +" rejected, only "+this.chatID+" is accepted");
                return;
            }

            String response;
            String message = update.getMessage().getText();
            if("/status".equals(message)) {
                response = this.buildStatusMessage("UBX");

            } else if("/today".equals(message)){
                response = this.OneDay(0,"Today''s rewards");

            } else if("/yesterday".equals(message)){
                response = this.OneDay(1,"Yesterday's rewards");

            } else if("/week".equals(message)){
                TokenPrice price = this.priceService.getPrice("UBX");
                double totalDays = 0;
                DailySummary ds = this.monitorService.getLastDays(7);
                response = "This week's rewards:\n";
                for(DaySummary dd: ds.getDays()) {
                    response += dd.getDay().format(dateFormat)+
                            " - "+ dd.getReward()+"\n";
                    totalDays += dd.getReward();
                }
                response += "Value: "+this.formatPrice(totalDays*price.getPrice())+ price.getCurrencyIsoCode();
            } else {
                response = "yeah... sure...";
            }

            sendMsg(update.getMessage().getChatId(), response);


        }
    }


    public void notifyStatus(String nodeID) {
        String statusDesc = this.buildStatusMessage(nodeID);
        this.sendMsg(this.chatID,statusDesc);
    }

    public void notifyTransactions(ArrayList<Transaction> transactions) {
        String message = "New transaction(s):\n";
        for(Transaction t: transactions) {
            if("Coin Base".equals(t.getFromAddr()) ) {
                message += "   " + t.getValue() + "\n";
            } else if("109129a92fc93151e9d9f203a983a3fd3aa6f0fb".equals(t.getFromAddr()) ) {
                message += "   " + t.getValue() + " (airdrop)\n";
            }else {
                log.info("Notification: ignoring transaction " + t.getId());
            }
        }
        this.sendMsg(chatID, message);
    }

     /**
     * Method for creating a message and sending it.
     * @param chatId chat id
     * @param s The String that you want to send as a message.
     */
    private synchronized void sendMsg(Long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        //sendMessage.enableMarkdown(true); // I deactivate it as there is an issue with the status message format during the first sync
        sendMessage.setChatId(""+chatId);
        sendMessage.setText(s);

        if(this.firstMessage) {
            this.attachCustomKeyboard(sendMessage);
            this.firstMessage = false;
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("ERROR",e);
            e.printStackTrace();

        }
    }


    /**
     * This method returns the bot's name, which was specified during registration.
     * @return bot name
     */
    @Override
    public String getBotUsername() {
        return this.botName;
    }
    /**
     * This method returns the bot's token for communicating with the Telegram server
     * @return the bot's token
     */
    @Override
    public String getBotToken() {
        return this.botToken;
    }

    private void attachCustomKeyboard(SendMessage message) {

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("/status");
        row.add("/week");
        // Add the first row to the keyboard
        keyboard.add(row);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("/today");
        row2.add("/yesterday");
        keyboard.add(row2);


        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

    }

    private String buildStatusMessage(String nodeID) {
        SyncStatus status = this.monitorService.checkStatus(nodeID);
        String response =
                "Status: "+status.getNodeName() + " "+ status.getStatus()+ "\n"+
                        "last sync: "+LocalDateTime.ofEpochSecond(status.getLastSync(),0, ZoneOffset.UTC).format(timeFormat)+"\n"+
                        "last success: "+LocalDateTime.ofEpochSecond(status.getLastSuccessSync(),0, ZoneOffset.UTC).format(timeFormat)+"\n";
        if(status.getStatus().equals(StatusService.STATUS_ERROR))
            response += "error: "+status.getErrorMessage();
        else if (status.getSyncInfo() != null && !status.getSyncInfo().equals(""))
            response += "info: "+status.getSyncInfo();
        return response;
    }

    private String OneDay(int dayID, String title) {
        TokenPrice price = this.priceService.getPrice("UBX");

        DayDetail dd = this.monitorService.getDayDetail(dayID);
        String response = title+":\n"+
                "Total: "+ dd.getTotal()+"--> "+this.formatPrice(dd.getTotal()*price.getPrice())+price.getCurrencyIsoCode()+"\n";
        for(Reward d: dd.getRewards()) {
            response += d.getDate().toLocalTime().format(timeFormat)+
                    //response += d.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+
                    " - "+ d.getValue()+"\n";
        }
        return response;
    }
    private String formatPrice(double price) {
        NumberFormat f = new DecimalFormat("######.00");
        return f.format(price);
    }
}

