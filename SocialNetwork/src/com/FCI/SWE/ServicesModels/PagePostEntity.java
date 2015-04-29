package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PagePostEntity extends PostEntity
{
	private String pageName ;
	private int nOfSeen ;
	private String seeners ;
	
	public PagePostEntity(String pageName, String postContent , String privacy , boolean isShare) 
	{
		this.pageName= pageName ;
		this.postContent = postContent ;
		this.isShare = isShare ;
		this.privacy = privacy ;
	}

	public PagePostEntity() {
		// TODO Auto-generated constructor stub
	}

	public long createPost() 
	{
		postType = "page" ;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
		Entity post = new Entity("posts");
		post.setProperty("owner", pageName );//who added the post
		post.setProperty("postContent", postContent );
		post.setProperty("privacy", privacy );		
		post.setProperty("likes", 0);	
		post.setProperty("likers", "");//who liked the post
		post.setProperty("nOfSeen", 0);	
		post.setProperty("seeners", "");//who seen the post		
		post.setProperty("isShare", isShare);//here it's not a shared page
		post.setProperty("postType", postType);//page post or user post
		
		if(datastore.put(post).isComplete())
		{
			return post.getKey().getId();
		}
		else
			return -1 ;		
	}
	public ArrayList<Map> getTimeLine(String pageName, String currentEmail) 
	{
		ArrayList<Map> al = new ArrayList<Map>() ;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query ("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("owner").toString().equals(pageName) && e.getProperty("postType").toString().equals("page"))
			{
				gaeQuery = new Query ("pages");
				pq = datastore.prepare(gaeQuery);
				String pageLikers = "";//for private post privacy
				for(Entity e2: pq.asIterable())
				{
					if(e2.getProperty("pageName").toString().equals(pageName) )
					{
						pageLikers = e2.getProperty("likers").toString();
					}
					break ;
				}
				if(e.getProperty("privacy").toString().equals("public") 
						|| (e.getProperty("privacy").toString().equals("private") && pageLikers.contains(currentEmail)))
				{
					if(!e.getProperty("seeners").toString().contains(currentEmail))
					{
						this.nOfSeen = Integer.parseInt(e.getProperty("nOfSeen").toString());
						this.seeners = e.getProperty("seeners").toString();
						
						e.setProperty("seeners" , seeners + currentEmail + "," );
						e.setProperty("nOfSeen" , nOfSeen + 1);
						datastore.put(e);
					}
					UserEntity ue = new UserEntity();
					Map <String ,String>  post = new HashMap();					
					post.put("postowner", " " +  pageName + " posted this.");
					post.put("postContent", e.getProperty("postContent").toString());

					post.put("postID", Long.toString(e.getKey().getId()));//this is used for the the like and share link
					post.put("likes",e.getProperty("likes").toString() + " people liked this.");
					al.add(post);
				}
			}
		}
		return al ;			
	}
	
	public ArrayList<Map> getPageTimelineAsAdmin(String pageName) 
	{
		ArrayList<Map> al = new ArrayList<Map>() ;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query ("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("owner").toString().equals(pageName) )
			{
					UserEntity ue = new UserEntity();
					Map <String ,String>  post = new HashMap();					
					post.put("postowner", " " + pageName + " posted this.");
					post.put("postContent", e.getProperty("postContent").toString());
					post.put("likes",e.getProperty("likes").toString() + " people liked this.");
					post.put("seen",e.getProperty("nOfSeen").toString() + " people seen this.");
					al.add(post);
			}
		}
		return al ;			
	}		
}