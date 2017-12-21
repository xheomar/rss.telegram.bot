# rss.telegram.bot

Confugurable Telegram bot, that can check RSS feed and send updates to Telegram public channel

1. Clone the repository
2. Fill the config.properties file

botToken={Token of your telegram bot that you received after its (or her/his:) creation}
timeForUpdatesInSec={the RSS feed will be updated every N seconds}
adminUserId={your telegram user ID}
adminUserName={your telegram userName}
isProxyNeeded={if the bot will be running in the network with Proxy, set this config to "yes" and set further Host and Port}
lastItemId={here will be stored an ID of last read news from RSS}
rssXmlUrl={URL to RSS Feed}
publicChannel={the name of your Public Channel, starting from "@"}
botName={The name of your Bot}
proxyHost={Proxy IP if needed}
proxyPort={Proxy port if needed}

3. Add your bot as admin to the public channel
4. run.sh
5. Send /start to start reading RSS and send messages to public channel
or /startDebug to start reading RSS and send messages to your private chat with bot

6. To stop reading send /stop
