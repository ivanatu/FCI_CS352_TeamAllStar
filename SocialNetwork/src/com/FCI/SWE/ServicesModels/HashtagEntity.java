package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import miscellaneous.comparison;

import org.apache.tools.ant.types.Comparison;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class HashtagEntity 
{
    //each record in this table contains the following datafields:
	private String hashTagName ;// this is contains the a specific hash tage name
	private String postID; // the id of the post sharing that hash tage (this id is brought from the posts table)
	int noOfPosts ;
	
	public HashtagEntity() {}
	public HashtagEntity(String hashTagName, String postID) 
	{
		this.hashTagName = hashTagName;
		this.postID = postID;
		this.noOfPosts = 1;
	}

	public boolean addHashTagPost()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("HashTagNames");;//contains the hash tags name and its ID
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String hashTagID = null ; //if found to add it to the HashTagPost table
		boolean found = false ; 
		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("HashTagName").toString().equals(hashTagName))
			{
				found = true ;
				hashTagID = Long.toString(e.getKey().getId());
				noOfPosts = Integer.parseInt(e.getProperty("noOfPosts").toString());
				e.setProperty("noOfPosts", noOfPosts + 1);
				datastore.put(e);
				break ;
			}
		}
		if(!found)
		{
			Entity e = new Entity("HashTagNames");
			e.setProperty("HashTagName", hashTagName);
			e.setProperty("noOfPosts", noOfPosts);
			datastore.put(e);
			hashTagID = Long.toString(e.getKey().getId());
		}
		Entity hashTagPost = new Entity("HashTagPost");
		hashTagPost.setProperty("hashTagID", hashTagID );
		hashTagPost.setProperty("postID", postID);		
		datastore.put(hashTagPost);
		return true;
	}
	public ArrayList<Map> getTrends() 
	{
		ArrayList <Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService() ;
		Query gaQuery = new Query("HashTagNames");
		PreparedQuery pq = datastore.prepare(gaQuery);
		int counter = 0 ;
		for(Entity e : pq.asIterable())
		{
			Map<String, String> hashtag = new HashMap ();
			hashtag.put("Name", e.getProperty("HashTagName").toString());
			hashtag.put("noOfPosts", e.getProperty("noOfPosts").toString());
			al.add(hashtag);
			counter ++ ;
			if(counter == 10)
				break ;
		}
		java.util.Collections.sort(al, new comparison());
		return al;
	}
	public ArrayList<Map> getHashtagTimeline(String HashtagName, String currentEmail) 
	{
			ArrayList<Map> al = new ArrayList<Map>() ;
			DatastoreService datastore_ = DatastoreServiceFactory.getDatastoreService();
			Query gaeQuery_ = new Query ("HashTagNames");
			PreparedQuery pq_ = datastore_.prepare(gaeQuery_);
			String HashtagNameId = "" ;
			for(Entity hashName : pq_.asIterable())
			{
				if(hashName.getProperty("HashTagName").toString().equals(HashtagName))
				{
					HashtagNameId = Long.toString(hashName.getKey().getId());
				}
					
			}
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query gaeQuery = new Query ("HashTagPost");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for(Entity hashTag : pq.asIterable())
			{
				FriendshipEntity fe = new FriendshipEntity();
				if(hashTag.getProperty("hashTagID").toString().equals(HashtagNameId))
				{
				
					String postID = hashTag.getProperty("postID").toString();
					Query gaeQuery2 = new Query ("posts");
					PreparedQuery pq2 = datastore.prepare(gaeQuery2);
					for(Entity post : pq2.asIterable())
					{
						if(Long.toString(post.getKey().getId()).equals(postID))
						{
							if(post.getProperty("postType").toString().equals("user") &&
								(post.getProperty("privacy").toString().equals("public") 
						||(post.getProperty("privacy").toString().equals("private")&& fe.isFriend(post.getProperty("owner").toString(), currentEmail)
							||(post.getProperty("privacy").toString().equals("custom")&& post.getProperty("custom").toString().contains(currentEmail)))))
							{
								UserEntity ue = new UserEntity();
								Map <String ,String>  hashPost = new HashMap();
								String postowner = ue.getUserNameByEmail(((post.getProperty("owner").toString())));
								if(!post.getProperty("postedBy").toString().equals(""))
								{
									String posterdBy = ue.getUserNameByEmail((post.getProperty("postedBy").toString()));
									hashPost.put("postowner", " " + posterdBy + " posted this to " + postowner );
								}
								else
									hashPost.put("postowner", postowner + " posted this.");
								hashPost.put("postContent", post.getProperty("postContent").toString());
								if(!post.getProperty("feeling").toString().equals("--"))//if there's a feeling
								{
									hashPost.put("feeling", "__ feeling " + post.getProperty("feeling"));
								}
								else
									hashPost.put("feeling", "");
								
								hashPost.put("postID", Long.toString(post.getKey().getId()));//this is used for the the like and share link
								hashPost.put("likes", post.getProperty("likes").toString() + " people liked this");
								al.add(hashPost);
							}
							else if(post.getProperty("postType").toString().equals("page"))
							{
								Query gaeQuery3 = new Query ("pages");
								PreparedQuery pq3 = datastore.prepare(gaeQuery3);
								String pageLikers = "";//for private post privacy
								String postowner = post.getProperty("owner").toString();
								for(Entity page: pq3.asIterable())
								{
									if(page.getProperty("pageName").toString().equals(postowner) )
									{
										pageLikers = page.getProperty("likers").toString();
									}
									break ;
								}
								if(post.getProperty("privacy").toString().equals("public") 
										|| (post.getProperty("privacy").toString().equals("private") && pageLikers.contains(currentEmail)))
								{
									UserEntity ue = new UserEntity();
									Map <String ,String>  hashPost = new HashMap();
									
									hashPost.put("postowner",   postowner + " posted this.");
									hashPost.put("postContent", post.getProperty("postContent").toString());
		
									hashPost.put("postID", Long.toString(post.getKey().getId()));//this is used for the the like and share link
									hashPost.put("likes",post.getProperty("likes").toString() + " people liked this");
									al.add(hashPost);
								}
							}
						}
		
					}
				}
			}
		return al;
	}
}
