package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.UserPostEntity;
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserTimelineServices 
{
	@POST
	@Path("/timelinePage")
	public String userTimeline(@FormParam("currentEmail") String currentEmail)
	{
		UserPostEntity upe = new UserPostEntity() ;
		ArrayList<Map> myposts = upe.getTimeLine(currentEmail);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		object.put("posts", myposts);
		return object.toString();
	}
	@POST
	@Path("/searchForAccount")
	public String searchForAccount(@FormParam("accountEmail") String accountEmail, @FormParam("currentEmail") String currentEmail)
	{
		
		JSONObject object = new JSONObject();
		FriendshipEntity fe = new FriendshipEntity();
		if(fe.isFriend(accountEmail , currentEmail))
		{
			object.put("friend", "true");	
		}
		else
			object.put("friend", "false");
		
		UserEntity ue = new UserEntity();
		String accountName = ue.getUserNameByEmail(accountEmail);
		object.put("accountName", accountName);
		UserPostEntity upe = new UserPostEntity() ;
		ArrayList<Map> accountPosts = upe.getAnAccountTimeLine(currentEmail ,accountEmail);
		
		object.put("Status", "OK");
		object.put("accountPosts", accountPosts);
		
		return object.toString();
	}	
	
}
