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
import java.util.Scanner;

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
import com.FCI.SWE.ServicesModels.HashtagEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.PostEntity;
import com.google.appengine.api.urlfetch.HTTPRequest;
@Path("/")
@Produces(MediaType.TEXT_PLAIN)

public class HashtagServices {
	@POST
	@Path("/hashtagChecker")
	public String HashTagChecker(@FormParam("postContent") String PostContent, @FormParam("postID") String postID )
	{
		Boolean check=false ;
		String HashTagName = "";
		Scanner S = new Scanner(PostContent);
		while (S.hasNext())
		{	
			HashTagName=S.next();
			if (HashTagName.contains("#")){
				check = true ; 
				HashtagEntity Hash = new HashtagEntity(HashTagName , postID );
				Hash.addHashTagPost();
			}
		}
		JSONObject object = new JSONObject();
		if(check!= false)
		{
			object.put("Status","Ok");
		}
		else
			object.put("Status","Failed");
		
		return object.toString();				
	}
	@POST
	@Path("/getHashtagTimeline")	
	public String getHashtagTimeline(@FormParam("HashtagName") String HashtagName, @FormParam("currentEmail") String currentEmail)
	{
		JSONObject object = new JSONObject();
		HashtagEntity hte = new HashtagEntity() ;
		ArrayList<Map> HashTagPosts = hte.getHashtagTimeline(HashtagName, currentEmail);
		
		if(HashTagPosts.isEmpty())
			object.put("Status","Failed");
		object.put("HashTagPosts", HashTagPosts);
		object.put("Status","Ok");
		return object.toString();				
	}		
	@POST
	@Path("/hashTagOptionsPageAndTrends")	
	public String hashTagOptionsPageAndTrends()
	{
		JSONObject object = new JSONObject();
		HashtagEntity hte = new HashtagEntity() ;
		ArrayList<Map> trends = hte.getTrends();
		object.put("Status","Ok");
		object.put("trends", trends);
		return object.toString();				
	}	
}
