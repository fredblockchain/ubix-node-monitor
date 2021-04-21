# ubix-node-monitor

## Introduction
This small app will allow monitoring rewards given by a UBIX master node. You will be able to monitor rewards from Telegram app.

This app monitor transactions related to a UBIX address, check every 10 minutes and keep the transaction to make it available by Telegram:
* The app will notify real time of new incoming transactions by Telegram message
* Check rewards of current and previous day
* Check daily reward for the week
* Check status of synchronisation

This is an early version that does its work.

It is not very nicely coded, any comment regarding that or anything will be welcomed.

Thanks to [Ruben Bermudez](https://github.com/rubenlagus) for his great [Telegram bot library](https://github.com/rubenlagus/TelegramBots)

### Disclaimer
* This software is provided with no guarantee of any sort, the user of this software is responsible for anything that can happen using it
* The author of this app has no link with UBIX team
* The author of this app has no obligation to update it in case of change on UBIX network side or in any of the library used in the app (spring, telagram bot API, etc)
* This app doesn't require your UBIX private key or keystore file, only the public address connected to your node and no one related to this app will ever ask you for personal information.
* Being a personal project, I may include functionality in the future to cover some very specific needs, although I will try to make then de activable

### IMPORTANT: security
This app runs without needing access to your node. the only link to the node is the public address, which can be accessed from anywhere.
In order to keep you node safe I recommend no to run this app or any other application in the same server. 

### Project background
I don't have an extended experience in java/spring development and at using platforms like Docker, so there may be a lot of best practices to apply to this project.
There is a lot of room for improvement :)

## Installation
You may do the whole process of compiling and running the app, or use directly use the docker-compose file provided [here](docker/README.md)

### Telegram bot
You need to create a telegram bot before being able to connect the app to telegram.

Start a conversation with @BotFather, send the command "/newbot" and follow the instructions. You will need the token and the name of this bot to configure the app properly

You will also need to check you user ID: this is your personal account from which you will talk with your bot.
This is required to prevent other people to communicate with your bot and have access to you reward.
You can check your ID by using "@userinfobot" from your telegram account.

Don't forget to start a conversation with your bot (search for it on Telegram, open a conversation and send "/start" or push the button "start")

### UBIX token price
The bot check the price in order to provide the value of the rewards. The price is retrieve from Coin Market Cap, in order to use this functionality you will need to create a user and API key in Coin Market Cap web site.
A free account fits our needs here, as the price is checked only when the telegram bot communicate with you (few times a day).

### Miscellaneous information
* The app is using an internal API of Ubikiri website. There is no guarantee that UBIX team will keep this API as is.
* I assume that rewards are transactions coming from "coin Base" sender.
* The app provides a way to set the time to the user timezone. This impact only the visualisation of the transaction time, and you may see transaction times out of the 24h limit of the day because daily info is always calculated based on the UTC time of transactions.
* This app can run in any machine, there is no need to run it in the same machine as the node.

### Configuration
Several parameters have to be set.
they may be configured in the traditional application.properties or, if you use Socker, in the docker-compose file.

* **telegram.name:** the name of your telegram bot
* **telegram.token:** the private token of your bot
* **telegram.chatId:** your telegram user ID. This the telegram account from which you will talk with the bot. We use that in order to prevent other people to connect with your bot.
* **node.ubix.address:** the public address linked to your node
* **price.coinmarketcap.apikey:** The API key you have created on your coin Market Cap account
* **global.time.utc.offset:** UBIX transactions time are in UTC timezone. Set here the number of minutes you want add

## App use
The bot provides 4 queries:
* **/today** access todays reward transactions
* **/yesterday** access yesterday's rewards transactions
* **/week** access daily rewards for the week (last 7 days)
* **/status** check the status of the synchronisation. This status is available during the initial synchronisation at startup. the status will include the number of the last checked transaction page.

The bot will also send a notification when new transactions are detected. Please note that all incoming transaction are notified (including, for example, daily airdrops)

## Versions
### Next steps
Some ideas to improve the app that I will probably implement soon:
* Use Fluentd as a log gateway (in my case to use Kibana as central log repo)
* Add currency conversion
* Use SQLite to implement persistence (instead of the current on memory Arraylist)
* Add more info available in the bot
* Improve telegram messages by using Markdown format instead of plain text
* Optimise docker image size

### 0.1
First version with basic functionality

