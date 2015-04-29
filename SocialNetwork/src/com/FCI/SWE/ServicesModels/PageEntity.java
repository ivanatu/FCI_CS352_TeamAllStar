package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PageEntity
{
	private String pageName;
	private String pageType;
	private String pageCategory;
	private int pagelikes;
	private int pagereach;
	private String createdEmail;
	
	public PageEntity() {}
	
	public PageEntity(String pageName, String pageType, String pageCategory, String createdEmail) 
	{
		this.pageName = pageName ;
		this.pageType = pageType ;
		this.pageCategory = pageCategory ;
		pagelikes = 0;
		pagereach = 0;
		this.createdEmail = createdEmail;
	}
	public ArrayList<Map> getPageNameList(String email)
	{
		ArrayList<Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("pages");
		
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//String currentUserEmail = User.currentActiveUser.getEmail();
		for (Entity entity : pq.asIterable()) 
		{
			if ( entity.getProperty("createdEmail").toString().equals(email) )
			{
				Map page = new HashMap();
				page.put("name", (String) entity.getProperty("pageName"));
				page.put("likes", entity.getProperty("pagelikes").toString());
				al.add(page);
			}
		}
		return al;
	}	
	
	public boolean createPage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity page = new Entity("pages");//the id in db will be a random number
	
		page.setProperty("pageName" , pageName);
		page.setProperty("pageType", pageType);
		page.setProperty("pageCategory" , pageCategory );
		page.setProperty("pagelikes" , pagelikes );
		page.setProperty("pagereach" , pagereach );
		page.setProperty("createdEmail" , createdEmail );
		page.setProperty("likers" , "" );
		datastore.put(page);
			
		return true ;
	}
	public ArrayList<Map> searchPage(String pageName)
	{
		ArrayList<Map>al = new ArrayList<Map>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity e: pq.asIterable())
		{
			if(e.getProperty("pageName").equals(pageName))
			{
				Map<String , String> m = new HashMap();
				m.put("Name", pageName);
				al.add(m);
			}
		}
		return al;
	}
	public boolean likePage(String email, String pageName)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("pageName").equals(pageName))
			{
				if(e.getProperty("likers").toString().contains(email))
				{
					return false;
				}
				e.setProperty("likers" , (e.getProperty("likers") + email + ","));
				int pagelikes = Integer.parseInt( e.getProperty("pagelikes").toString() );
				pagelikes++;
				e.setProperty("pagelikes" , pagelikes);
				
				datastore.put(e);
				break ;
			}
		}
		return true ;
	}	
}