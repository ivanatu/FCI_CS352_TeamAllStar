package com.FCI.SWE.ServicesModels;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class MessageEntity 
{
	private String SenderEmail;
	private String RecEmail;
	private String Mesg;
	private String messageID ;
	
	public MessageEntity() {}
	public MessageEntity(String messageID, String currentEmail) 
	{
		this.messageID = messageID ;
		this.RecEmail = currentEmail;
	}
	public String getMesg()
	{
		return Mesg ;
	}
	public MessageEntity(String SenderEmail, String RecEmail, String Mesg)
	{
		this.SenderEmail = SenderEmail ;
		this.RecEmail = RecEmail ;
		this.Mesg = Mesg ;
	}
	public boolean SendMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity message = new Entity("messages");//the id in db will be a random number
	
		message.setProperty("SendEmail" , SenderEmail);
		message.setProperty("notifID", RecEmail);
		message.setProperty("Mesg" ,Mesg );
		datastore.put(message);
		messageID = Long.toString(message.getKey().getId());
		Entity notification = new Entity("notifications");//the id in db will be a random number
		notification.setProperty("notiClass" , "ReadMessageCommand"); //name of class to handle reaction
		notification.setProperty("RecEmail" , RecEmail);
		notification.setProperty("notifID", messageID);
		datastore.put(notification);
	
		return true ;
	}
	public boolean readMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();// connect to DB
		Query gaeQuery = new Query("messages");// defining the Query
		PreparedQuery pq = datastore.prepare(gaeQuery);// excuting the query
		for (Entity entity : pq.asIterable()) 
		{
			if (Long.toString(entity.getKey().getId()).equals(messageID))
			{
				this.Mesg = (String) entity.getProperty("Mesg");
			}	
		}
		//remove from notifications
		gaeQuery = new Query("notifications");
		pq = datastore.prepare(gaeQuery);// excuting the query
		for (Entity entity2 : pq.asIterable())
		{
			if (entity2.getProperty("notifID").equals(messageID) )
			{
				datastore.delete(entity2.getKey());
			}
		}
		return true ;
	}
	
	public ArrayList<Map> getMessages() 
	{
		ArrayList<Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("RecID").toString().equals(RecEmail) &&
					entity.getProperty("isRead").toString().equals("false"))
			{
				String sendID = entity.getProperty("SendID").toString();
				gaeQuery = new Query("users");
			    pq = datastore.prepare(gaeQuery);
				for (Entity entity2 : pq.asIterable())
				{
					if(Long.toString(entity2.getKey().getId()).equals(sendID))
					{
						Map message = new HashMap();
						message.put("name",entity2.getProperty("name") );
						message.put("id", entity.getKey().getId());//the id of the message record
						al.add(message);
					}
				}
			 }
		}
		return al ;
	}

}