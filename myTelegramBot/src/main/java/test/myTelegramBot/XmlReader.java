package test.myTelegramBot;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import test.myTelegramBot.xml.XmlItem;

public class XmlReader 
{
	public static XmlItem getRssFeedLastItemId(String rssUrl) throws IOException 
	{
		Document doc;
		String title, description, id="", link="", pubDate, channelName;
		
		doc = Jsoup.connect(rssUrl).get();
		
		// link & id
		Element itemLink = doc.select("item > link").first();
		Pattern r = Pattern.compile("(\\d+)");
		Matcher m = r.matcher(itemLink.html());
	    if (m.find( )) 
	    {				    
	    	id = m.group(0);
	    }
	    link = itemLink.html();
	    
	    // title 
	    Element itemTitle = doc.select("item > title").first();
	    title = itemTitle.html();
	    
	    // description 
	    // Element itemDescription = doc.select("item > description").first();
	    // description = itemDescription.html();
	    
	    // pubDate 
	    // Element itemPubDate = doc.select("item > pubDate").first();
	    // pubDate = itemPubDate.html();
	    
	    // channelName 
	    Element rssChannelName = doc.select("channel > title").first();
	    channelName = rssChannelName.html();
	    
	    //XmlItem lastItem = new XmlItem(title, description, link, id, pubDate, channelName);
	    XmlItem lastItem = new XmlItem(title, link, id, channelName);
	    
    	System.out.println("The last item: \n" + "link = " + lastItem.getLink() + ",\n" + 
    			"title = " +  lastItem.getTitle() + ",\n" +
    			"description = " +  lastItem.getDescription() + ",\n" +
    			"date = " +  lastItem.getPubDate() + ",\n" +
    			"channelName = " +  lastItem.getChannelName()
    			+ "\n\n\n");
    	
		return lastItem;
	} 
}
