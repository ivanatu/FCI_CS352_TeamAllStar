package com.FCI.SWE.ServicesModels;

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

public class GroupMessageEntity 
{
	private String chatName;
	private String Mesg;
	private String recEmail ;
	private String groupMessageID ;
	
	public GroupMessageEntity() {}
	public GroupMessageEntity(String groupMessageID) 
	{
		this.groupMessageID = groupMessageID ;
	}
	public String getMesg()
	{
		return Mesg ;
	}
	public GroupMessageEntity(String chatName, String Mesg)
	{
		this.chatName = chatName ;
		this.Mesg = Mesg ;
	}
	public GroupMessageEntity(long groupMessageID , String currentEmail)
	{
		this.groupMessageID = Long.toString(groupMessageID);
		this.recEmail = currentEmail;
	}	
	/**
	 * this function takes a chatName and returns its ID
	 * @param chatName
	 * 				the chatName to get the matching ID 
	 * @return the ID of the chatName shring this name
	 */
	public boolean sendGroupMessage(String sender)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("messageGroups");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		String Emails="";
		boolean check = false;
 
		for (Entity entity : pq.asIterable()) 
		{
			if (((String)entity.getProperty("chatName")).equals(chatName))//making sure there's such a group
			{				
				Emails = (String) entity.getProperty("Emails");
				check = true;
				break;
			}
		}
		if (check == false)
			return false;
 		
		datastore = DatastoreServiceFactory
				.getDatastoreService();
		gaeQuery = new Query("groupMessages");
		pq = datastore.prepare(gaeQuery);		
		List<Entity> list2 = pq.asList(FetchOptions.Builder.withDefaults());
	
		Entity messages = new Entity("groupMessages");//the id in db will be random number
		messages.setProperty("chatName" , chatName);
		messages.setProperty("SendEmail" , sender);		
		messages.setProperty("Mesg", Mesg);
		datastore.put(messages);
		
		String groupMesgId = Long.toString(messages.getKey().getId()); 
		
		Scanner s = new Scanner(Emails);
		s.useDelimiter(",");
		gaeQuery = new Query("notifications");
		
		while (s.hasNext())
		{
			String RecEmail = s.next();
			if(RecEmail.equals(sender))
				continue ;
			messages = new Entity("notifications");//the id in db will be random number
			messages.setProperty("notiClass" , "ReadGroupMessageCommand"); //name of class to handle reaction
			messages.setProperty("notifID", groupMesgId);//groupmessageID
			messages.setProperty("RecEmail" , RecEmail);			

			datastore.put(messages);
		}
		return true ;
	}
	public boolean createGroupChat (String name , String Emails , String currentEmail)//finished
	{		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("messageGroups");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("chatName").equals(name))//exists before
			{
				return false;
			}	
		}		
		Entity groupChatName = new Entity("messageGroups");//the id in db will be random number
		Emails += "," + currentEmail;
		
		groupChatName.setProperty("chatName", name);
		groupChatName.setProperty("Emails", Emails);
		
		if(datastore.put(groupChatName).isComplete())
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	public boolean readGroupMessage()
	{			
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();// connect to DB
		Query gaeQuery = new Query("notifications");// defining the Query
		PreparedQuery pq = datastore.prepare(gaeQuery);// excuting the query
		for (Entity entity : pq.asIterable()) 
		{
			
			if (entity.getProperty("notifID").equals(groupMessageID) && entity.getProperty("RecEmail").equals(recEmail))
			{
				datastore.delete(entity.getKey());//delete notification
				break ;
			}	
		}
		gaeQuery = new Query("groupMessages");// defining the Query
		pq = datastore.prepare(gaeQuery);// excuting the query
		for(Entity entity2:pq.asIterable())
		{
			if(Long.toString(entity2.getKey().getId()).equals(groupMessageID))
			{
				this.Mesg = (String)entity2.getProperty("Mesg");
				break;
			}
		}
		return true ;
	}		
}
