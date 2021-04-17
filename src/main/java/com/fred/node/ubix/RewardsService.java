package com.fred.node.ubix;

import com.fred.node.chain.data.TransactionService;
import com.fred.node.chain.model.Transaction;
import com.fred.node.monitor.TransactionComparator;
import com.fred.node.monitor.model.DayDetail;
import com.fred.node.monitor.model.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class RewardsService {

    @Autowired
    @Qualifier("UBIXInMemoryDB")
    private TransactionService dataStore;

    /**
     * get detailed info of rewards for a specific day
     * @param day: 0= today, 1=yesterday, etc..
     * @return
     */
    public DayDetail getInfoForDay(long day) {
        Date today = new Date();
        long total = 0;
        ArrayList<Reward> rewards = new ArrayList<Reward>();
        LocalDate queryDay = LocalDate.now();
        queryDay = queryDay.minusDays(day);

        for(Transaction t: this.dataStore.getTransactionsForDay(queryDay)) {
            if("Coin Base".equals(t.getFromAddr()) ) {
                rewards.add(new Reward(t.getId(), t.getDate(), t.getValue()));
                total += t.getValue();
            }
        }
        rewards.sort(new TransactionComparator());
        return new DayDetail(queryDay, total, rewards);

    }
}
