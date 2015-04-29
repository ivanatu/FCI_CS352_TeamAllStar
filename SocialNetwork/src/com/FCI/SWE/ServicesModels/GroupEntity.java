package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class GroupEntity {
	private String name;
	private String description;
	private String privacy;
	private String ownerEmail;

	public String getName(){
		return name;
	}
	
	public String getDesc(){
		return description;
	}
	
	public String getPrivacy(){
		return privacy;
	}
	
	public String getOwnerEmail(){
		return ownerEmail;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String desc){
		this.description = desc;
	}
	
	public void setPrivacy(String privacy){
		this.privacy = privacy;
	}
	
	public void setOwnerEmail(String Email){
		this.ownerEmail = Email;
	}

	/**
	 * this function add a group data to the database 
	 * @return true or false
	 */
	public Boolean saveGroup() 
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groups");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity group = new Entity("groups", list.size() + 1);
		
		group.setProperty("name", this.name);
		group.setProperty("description", this.description);
		group.setProperty("privacy", this.privacy);
		group.setProperty("ownerEmail",this.ownerEmail);
		
		if(datastore.put(group).isComplete())
			return true;
		else return false;

	}
	/**
	 * this function adds a usre to a specific group using his ID and the group ID
	 * @param gpID
	 * 				the group ID to join
	 * @return true or false
	 */
	public Boolean joinGroup(String gpID, String currentEmail)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("gp_members");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity memeber = new Entity("memeber", list.size() + 1);

		memeber.setProperty("userEmail", currentEmail );
		memeber.setProperty("groupId", gpID);
		if(datastore.put(memeber).isComplete())
		{
			return true ;
		}
		else
		{
			return false ;
		}
		
	}




}
