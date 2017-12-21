package test.myTelegramBot;

import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

import jersey.repackaged.com.google.common.collect.Lists;
import test.myTelegramBot.xml.XmlItem;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class SimpleBot extends TelegramLongPollingBot 
{
	private static final String BOT_TOKEN_CONFIG_NAME = "botToken";
	private static final String ADMIN_USERNAME_CONFIG_NAME = "adminUserName";
	private static final String RSS_XML_CONFIG_NAME = "rssXmlUrl";
	private static final String BOT_NAME_CONFIG_NAME = "botName";
	private static final String TIME_INTERVAL_IN_SEC_CONFIG_NAME = "timeForUpdatesInSec";	
	private static final String NEED_OF_PROXY_CONFIG_NAME = "isProxyNeeded";
	private static final String PROXY_HOST_CONFIG_NAME = "proxyHost";
	private static final String PROXY_PORT_CONFIG_NAME = "proxyPort";
	private static final String ADMIN_USERID_CONFIG_NAME = "adminUserId";
	private static final String PUBLIC_CHANNEL_CONFIG_NAME = "publicChannel";
	private static final String LAST_ITEM_CONFIG_NAME = "lastItemId";
	
	private static String BOT_TOKEN = "";
	private static String ADMIN_USERNAME = "";
	private static String ADMIN_USERID = "";
	private static String RSS_XML = "";
	private static String BOT_NAME = "";
	private static int TIME_INTERVAL_IN_SEC;	
	private static String NEED_OF_PROXY = "";
	private static String PROXY_HOST = "";
	private static String PROXY_PORT = "";	
	private static String PUBLIC_CHANNEL = "";	
	private static String LAST_ITEM = "";	
	
	private static final String SET_TIME = "/setTime";
	
	private volatile boolean isRunning = true;
	private volatile boolean isDebug = false; 
	
	static File configFile = new File("config.properties");
	static Properties props = new Properties();
	
	private SimpleBot(DefaultBotOptions options) 
	{
        super(options);
    }
	
	private SimpleBot() 
	{
        super();
    }
 
	public static void main(String[] args) 
	{
		try 
		{
			getProperties();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			System.exit(0);
		}
		
		boolean isProxyNeeded = NEED_OF_PROXY.equals("yes") ? true: false;
		if (isProxyNeeded)
		{
			HttpHost proxy = new HttpHost(PROXY_HOST, Integer.parseInt(PROXY_PORT));
		    RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		    DefaultBotOptions options = new DefaultBotOptions();
		    options.setRequestConfig(config);
		    
			ApiContextInitializer.init();
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
			try 
			{ 
				SimpleBot myBot = new SimpleBot(options);
				System.out.println("Bot registration... ");
				telegramBotsApi.registerBot(myBot);
			} 
			catch (TelegramApiException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			ApiContextInitializer.init();
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
			try 
			{ 
				SimpleBot myBot = new SimpleBot();
				System.out.println("Bot registration... ");
				telegramBotsApi.registerBot(myBot);
				
				System.out.println("\n\nBot is running! ");
			} 
			catch (TelegramApiException e) 
			{
				e.printStackTrace();
			}
		}
	}
 

	static private void getProperties() throws Exception 
	{
		System.out.println("Reading configuration... ");
		try 
		{
		    FileReader reader = new FileReader(configFile);
		    props.load(reader);
		 
		    BOT_TOKEN = props.getProperty(BOT_TOKEN_CONFIG_NAME);
		    if (BOT_TOKEN.isEmpty())
		    {
		    	throw new Exception("No BOT_TOKEN configured");
		    }
		    ADMIN_USERNAME = props.getProperty(ADMIN_USERNAME_CONFIG_NAME);
		    if (ADMIN_USERNAME.isEmpty())
		    {
		    	throw new Exception("No ADMIN_USERNAME configured");
		    }
		    ADMIN_USERID = props.getProperty(ADMIN_USERID_CONFIG_NAME);
		    if (ADMIN_USERID.isEmpty())
		    {
		    	throw new Exception("No ADMIN_USERID configured");
		    }
		    RSS_XML = props.getProperty(RSS_XML_CONFIG_NAME);
		    if (RSS_XML.isEmpty())
		    {
		    	throw new Exception("No ADMIN_USERNAME configured");
		    }
		    BOT_NAME = props.getProperty(BOT_NAME_CONFIG_NAME);
		    if (BOT_NAME.isEmpty())
		    {
		    	throw new Exception("No BOT_NAME configured");
		    }
		    try 
		    {
				TIME_INTERVAL_IN_SEC = Integer.parseInt(props.getProperty(TIME_INTERVAL_IN_SEC_CONFIG_NAME));
			} 
		    catch (Exception e) 
		    {
		    	throw new Exception("No TIME_INTERVAL_IN_SEC configured");
			}
		    
		    NEED_OF_PROXY = props.getProperty(NEED_OF_PROXY_CONFIG_NAME);
		    PROXY_HOST = props.getProperty(PROXY_HOST_CONFIG_NAME);
		    if (!NEED_OF_PROXY.isEmpty() && PROXY_HOST.isEmpty())
		    {
		    	throw new Exception("No PROXY_HOST configured");
		    }
		    PROXY_PORT = props.getProperty(PROXY_PORT_CONFIG_NAME);
		    if (!NEED_OF_PROXY.isEmpty() && PROXY_PORT.isEmpty())
		    {
		    	throw new Exception("No PROXY_PORT configured");
		    }
		    PUBLIC_CHANNEL = props.getProperty(PUBLIC_CHANNEL_CONFIG_NAME);
		    if (PUBLIC_CHANNEL.isEmpty())
		    {
		    	throw new Exception("No PUBLIC_CHANNEL configured");
		    }
		    
		    LAST_ITEM = props.getProperty(LAST_ITEM_CONFIG_NAME);

		    System.out.println("The following config were read: ");
		    System.out.println("BOT_TOKEN: " + BOT_TOKEN);
		    System.out.println("ADMIN_USERNAME: " + ADMIN_USERNAME);
		    System.out.println("ADMIN_USERID: " + ADMIN_USERID);
		    System.out.println("RSS_XML: " + RSS_XML);
		    System.out.println("BOT_NAME: " + BOT_NAME);
		    System.out.println("TIME_INTERVAL_IN_SEC: " + TIME_INTERVAL_IN_SEC);
		    System.out.println("NEED_OF_PROXY: " + NEED_OF_PROXY);
		    System.out.println("PROXY_HOST: " + PROXY_HOST);
		    System.out.println("PROXY_PORT: " + PROXY_PORT);
		    System.out.println("PUBLIC_CHANNEL: " + PUBLIC_CHANNEL);
		    System.out.println("LAST_ITEM: " + LAST_ITEM);
		    
		    System.out.print("\n\n");
		    
		    reader.close();
		} 
		catch (FileNotFoundException ex) 
		{
		    System.out.println("File Not Found: " + configFile);
		} 
		catch (IOException ex) 
		{
			System.out.println("I/O error");
		}
	}

	private static void saveProperties() 
	{
		System.out.println("Saving configuration... ");
		try 
		{
			FileWriter writer = new FileWriter(configFile);
			props.store(writer, "bot settings");
			writer.close();
		} 
		catch (FileNotFoundException ex) 
		{
		    System.out.println("File Not Found");
		} 
		catch (IOException ex) 
		{
			System.out.println("I/O error");
		}
	}
	
	public String getBotUsername() 
	{
		return BOT_NAME;
	}
 
	@Override
	public String getBotToken() 
	{
		return BOT_TOKEN;
	}
 

	public void onUpdateReceived(Update update) 
	{
		final Message message = update.getMessage();
		System.out.println("Message received from user = " + message.getFrom().getUserName() + ", userId = " + message.getFrom().getId());
	    if (message.getFrom().getUserName().equals(ADMIN_USERNAME) && message.getFrom().getId() == Integer.parseInt(ADMIN_USERID))
		{
			System.out.println(message.getFrom().getId());
			if (message != null && message.hasText()) 
			{
				System.out.println("Received the message: " + message.getText());
				
				/*if (message.getText().startsWith(SET_TIME)) 
				{
					String newTime = message.getText().split(" ")[1];
					TIME_INTERVAL_IN_SEC = 
					sendMsg(message, "New 1st public is set to " + newPublicId_1);
					props.setProperty("id0", newPublicId_1);
					saveProperties();
				}*/

				if (message.getText().equals("/start") || message.getText().equals("/startDebug")) 
				{
					if (message.getText().equals("/start")) isDebug = false;
					if (message.getText().equals("/startDebug")) isDebug = true;
					isRunning = true;
					new Thread() 
					{
						@Override
						public void run()
						{
							int publicId=0;
							while (isRunning) 
							{
								try 
								{
									Thread.sleep(TIME_INTERVAL_IN_SEC * 1000);
								} 
								catch (InterruptedException e1) 
								{
									e1.printStackTrace();
								}

								// Check new subscriptions
								try 
								{
									XmlItem item = XmlReader.getRssFeedLastItemId(RSS_XML);
									
									getProperties();
									System.out.println("Last saved RSS id in configuration: " + LAST_ITEM);
									System.out.println("Last posted news has id: " + item.getId());
									
									if (!item.getId().equals(LAST_ITEM))
									{
										props.setProperty(LAST_ITEM_CONFIG_NAME, item.getId());
										saveProperties();
										if (isDebug)
										{
											sendMsg(message, "На сайте опубликована новость! "+
																			item.getDescription() + 
																			" " + 
																			item.getLink());
										}
										else
										{
											sendMessageToChannel(PUBLIC_CHANNEL, message, "На сайте опубликована новость! "+
																										item.getDescription() + 
																										" " + 
																										item.getLink());
										}
									}
								} 
								catch (Exception e) 
								{
									e.printStackTrace();
								}
							}
							sendMsg(message, "You has been unsubscribed");
						}
					}.start();
					sendMsg(message, "You has been subscribed");
				}
				else if (message.getText() != null && message.getText().equals("/stop")) 
				{
					isRunning = false;
				}
				else
				{
					sendMsg(message, "/start /startDebug /stop");
				}
			}	
		}
		else 
		{
			sendMsg(message, "You are not my admin :(");
		}
	}
	

	private void sendMessageToChannel(String channelAddress, Message message, String text) 
	{
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(channelAddress);
        sendMessage.setText(text);
        if (channelAddress.equals(PUBLIC_CHANNEL))
        {
        	sendMessage.disableWebPagePreview();
        }
        System.out.println("Sent the message: " + text);
        try 
        {
            sendMessage(sendMessage);
        } 
        catch (TelegramApiException e) 
        {
        	e.printStackTrace();
        }
    }
 
	private void sendMsg(Message message, String text) 
	{
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setText(text);
		sendMessage.disableWebPagePreview();
		System.out.println("Sent the message: " + text);
		try 
		{
			sendMessage(sendMessage);
		} catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}
}
