package sieger.model;

import java.util.Date;

public class Notification {
	//content
	private String content;
	//recipient id
	private String recipientId;
	//date to retrieve
	private Date dateToRetrieve;
	//constructor
	public Notification() {
		this.content = null;
		this.dateToRetrieve = null;
		this.recipientId = null;
	}
	//get content
	public String getContent() {
		return this.content;
	}
	//get recipient id
	public String getRecipientId() {
		return this.recipientId;
	}
	//get retrieve date
	public Date getDateToRetrieve() {
		return this.dateToRetrieve;
	}
	//set content
	public void setContent(String content) {
		this.content = content;
	}
	//set recipient id
	public void setRecientId(String recipientId) {
		this.recipientId = recipientId;
	}
	//set date to retrieve 
	public void setDateToRetrieve(Date date) {
		this.dateToRetrieve = date;
	}
}
