package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.GroupEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
import com.FCI.SWE.ServicesModels.PagePostEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.PostEntity;
import com.FCI.SWE.ServicesModels.UserPostEntity;
import com.google.appengine.api.urlfetch.HTTPRequest;


@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PostServices 
{
		
	@POST
	@Path("/createPost")
	public String createPost(@FormParam("currentEmail") String currentEmail, 
			@FormParam("postContent") String postContent ,
			@FormParam("privacy") String privacy, @FormParam("feeling") String feeling,
			@FormParam("custom") String custom)
	{
		UserPostEntity upe = new UserPostEntity(currentEmail, postContent, privacy, feeling, custom, false);
		JSONObject object = new JSONObject();
		long postID = upe.createPost() ;
		if(postID != -1)
		{
			object.put("postID", postID);
			object.put("Status","Ok");
		}		
		else
			object.put("Status","Failed");
		return object.toString();				
	}
	@POST
	@Path("/createPostToFriend")
	public String createPostToFriend(@FormParam("currentEmail") String currentEmail, 
			@FormParam("friendEmail") String friendEmail , @FormParam("feeling") String feeling ,@FormParam("postContent") String postContent)
	{
		UserPostEntity upe = new UserPostEntity();
		JSONObject object = new JSONObject();
		long postID = upe.createPostToFriend(currentEmail, postContent, friendEmail, feeling) ;
		if(postID != -1)
		{
			object.put("postID", postID);
			object.put("Status","Ok");
		}		
		else
			object.put("Status","Failed");
		return object.toString();				
	}	
	@POST
	@Path("/createPagePost")
	public String createPost(@FormParam("pageName") String pageName, 
			@FormParam("postContent") String postContent , @FormParam("privacy") String privacy)
	{
		PagePostEntity upe = new PagePostEntity(pageName, postContent, privacy, false);
		JSONObject object = new JSONObject();
		long postID = upe.createPost() ;
		if(postID != -1)
		{
			object.put("postID", postID);
			object.put("Status","Ok");
		}		
		else
			object.put("Status","Failed");
		return object.toString();				
	}		
	@POST
	@Path("/likePost")
	public String likePost(@FormParam("postID") String postID,@FormParam("currentEmail") String currentEmail )
	{
		JSONObject object = new JSONObject();
		UserPostEntity upe = new UserPostEntity() ;
		if(upe.likePost(postID ,currentEmail))//we send the liker email so as not to like twice
			object.put("Status","Ok");
		else
			object.put("Status","Failed");
		return object.toString();
	}
	@POST
	@Path("/sharePost")
	public String sharePost(@FormParam("postID") String postID,@FormParam("currentEmail") String currentEmail )
	{
		JSONObject object = new JSONObject();
		UserPostEntity upe = new UserPostEntity() ;
		if(upe.sharePost(postID ,currentEmail))//we send the liker email so as not to like twice
			object.put("Status","Ok");
		else
			object.put("Status","Failed");
		return object.toString();
	}
	@POST
	@Path("/getNewsFeed")
	public String getNewsFeed(@FormParam("currentEmail") String currentEmail)
	{
		UserPostEntity upe = new UserPostEntity();
		JSONObject object = new JSONObject();
		ArrayList <Map> newsFeedPosts = upe.getNewsFeed(currentEmail);
		object.put("newsFeedPosts", newsFeedPosts);
		object.put("Status","Ok");
		return object.toString();				
	}		
}