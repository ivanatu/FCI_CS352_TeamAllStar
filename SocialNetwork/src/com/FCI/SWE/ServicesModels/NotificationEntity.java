package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class NotificationEntity 
{
	public ArrayList<Map> getNotifications(String currentEmail) 
	{
		ArrayList<Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("RecEmail").toString().equals(currentEmail))
			{
				
				String notID = "";// messageGroupID or FriendrequestSenderEmail or messageID 
				String NotificationMessageOnPage = "";//to print Ahmed sent you..
				String action = "" ;// read or accept
				String href = "" ;//to call the action controller form the page
				
				//Notification of groupMessages
			    if(((String)entity.getProperty("notiClass")).equals("ReadGroupMessageCommand"))
			    {
			
			    	notID = (String)entity.getProperty("notifID");//messageGroupID in this case
					DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
					Query gaeQuery2 = new Query("groupMessages");
					PreparedQuery pq2 = datastore2.prepare(gaeQuery2);
					for (Entity entity2 : pq2.asIterable()) 
					{
					    if((Long.toString(entity2.getKey().getId())).equals(notID))
					    { //datastore.delete(entity2.getKey());
							String SenderEmail = entity2.getProperty("SendEmail").toString();				
							String senderName = UserEntity.getUserNameByEmail(SenderEmail);
							
					    	String chatName = (String)entity2.getProperty("chatName");
					    	
					    	Map notification = new HashMap();//this is to add a single notification to list
					    	NotificationMessageOnPage = senderName + " sent you a message in group " + chatName ;		
					
					    	action = "read";
					    	href = "/social/notificationReaction/"+ notID + "/" + "ReadGroupMessageCommand" ;
					       	notification.put("NotificationMessageOnPage", NotificationMessageOnPage);
					    	notification.put("action", action);
					    	notification.put("href", href);
					    	al.add(notification);
					    }
					}
			    }
			    //Notification of friendRequests
			    else if(((String)entity.getProperty("notiClass")).equals("AcceptFriendCommand"))
			    {
					
			    	notID = (String)entity.getProperty("notifID");//friend email in this case
			    	String senderName = UserEntity.getUserNameByEmail(notID);
			    	Map notification = new HashMap();//this is to add a single notification to list
			    	NotificationMessageOnPage = senderName + " sent you a friend request " ;		
			
			    	action = "Confirm";
			    	href = "/social/notificationReaction/"+ notID + "/" + "AcceptFriendCommand" ;
			       	notification.put("NotificationMessageOnPage", NotificationMessageOnPage);
			    	notification.put("action", action);
			    	notification.put("href", href);
			    	al.add(notification);
			    }
			    //Notification of messages			    
			    else if(((String)entity.getProperty("notiClass")).equals("ReadMessageCommand"))
			    {
			    	notID = (String)entity.getProperty("notifID");//messageID in this case
					DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
					Query gaeQuery2 = new Query("messages");
					PreparedQuery pq2 = datastore2.prepare(gaeQuery2);
					for (Entity entity2 : pq2.asIterable()) 
					{
					    if((Long.toString(entity2.getKey().getId())).equals(notID))
					    { 				
					    	Map notification = new HashMap();//this is to add a single notification to list
					    	String SenderEmail = entity2.getProperty("SendEmail").toString();
					    	String senderName = UserEntity.getUserNameByEmail(SenderEmail);
					    	NotificationMessageOnPage = senderName + " sent you a message " ;		
					    	action = "read";
					    	href = "/social/notificationReaction/"+ notID + "/" + "ReadMessageCommand" ;
					    	
					       	notification.put("NotificationMessageOnPage", NotificationMessageOnPage);
					    	notification.put("action", action);
					    	notification.put("href", href);
					    	al.add(notification);								
					    }
					}			    			    	
			    }		    	    
		    }		 
		 }

		return al ;
	}
}
