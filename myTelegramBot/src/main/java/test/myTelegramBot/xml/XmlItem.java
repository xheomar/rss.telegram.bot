package test.myTelegramBot.xml;

public class XmlItem 
{
	public XmlItem(String title, String description, String link, String id, String pubDate, String channelName) 
	{
		this.title = title;
		this.description = description;
		this.link = link;
		this.setId(id);
		this.pubDate = pubDate;
		this.channelName = channelName;
	}
	
	public XmlItem(String title, String link, String id, String channelName) 
	{
		this.title = title;
		this.link = link;
		this.setId(id);
		this.channelName = channelName;
	}
	
	private String title;
	private String description;
	private String link;
	private String id;
	private String pubDate;
	private String channelName;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
