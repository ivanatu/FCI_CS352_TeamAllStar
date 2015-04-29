package com.FCI.SWE.Controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class PostController 
{	
	@GET
	@Path("/likePost/{postID}")
	public Response likePsot(@Context HttpServletRequest req ,@PathParam("postID") String postID)
	{
		String currentEmail = (String)req.getSession().getAttribute("currentEmail");
		String serviceUrl = "http://localhost:8888/rest/likePost";
		String urlParameters = "postID=" + postID + "&currentEmail=" + currentEmail ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
						"application/x-www-form-urlencoded;charset=UTF-8");	
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if(object.get("Status").equals("Ok"))
				return null ;
			else 
				return null ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
	@POST
	@Path("/createPagePost")
	public Response createPagePost(@Context HttpServletRequest req, 
			@FormParam("postContent") String postContent ,@FormParam("privacy") String privacy)    
	{
		String pageName = (String)req.getSession().getAttribute("pageName(AdminView)");
		String serviceUrl = "http://localhost:8888/rest/createPagePost";
		String urlParameters = "pageName=" + pageName
				+ "&postContent=" + postContent + "&privacy=" + privacy ;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			//calling the other service
			serviceUrl = "http://localhost:8888/rest/hashtagChecker";
			urlParameters = "postID=" + object.get("postID")
					+ "&postContent=" + postContent ;
			
			retJson = Connection.connect(serviceUrl, urlParameters, "POST",
					"application/x-www-form-urlencoded;charset=UTF-8");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return null;//the same page refreshed only after page creation

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	@POST
	@Path("/createPost")
	public Response createPost(@Context HttpServletRequest req, 
			@FormParam("postContent") String postContent ,
			@FormParam("privacy") String privacy, @FormParam("feeling") String feeling,
			@FormParam("custom") String custom)    
	{
		String currentEmail = (String)req.getSession().getAttribute("currentEmail");
		String serviceUrl = "http://localhost:8888/rest/createPost";
		String urlParameters = "currentEmail=" + currentEmail
				+ "&postContent=" + postContent + "&privacy=" + privacy + "&feeling=" +
				feeling + "&custom=" + custom;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			//calling the other service
			serviceUrl = "http://localhost:8888/rest/hashtagChecker";
			urlParameters = "postID=" + object.get("postID")
					+ "&postContent=" + postContent ;
			
			retJson = Connection.connect(serviceUrl, urlParameters, "POST",
					"application/x-www-form-urlencoded;charset=UTF-8");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return null;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@POST
	@Path("/createPostToFriend")
	public Response createPostToFriend(@Context HttpServletRequest req, 
			@FormParam("postContent") String postContent ,
			 @FormParam("feeling") String feeling)    
	{
		String currentEmail = (String)req.getSession().getAttribute("currentEmail");
		String friendEmail = (String)req.getSession().getAttribute("accountEmail");
		String serviceUrl = "http://localhost:8888/rest/createPostToFriend";
		String urlParameters = "currentEmail=" + currentEmail + "&friendEmail=" + friendEmail 
				+ "&postContent=" + postContent + "&feeling=" +
				feeling ;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			//calling the other service
			serviceUrl = "http://localhost:8888/rest/hashtagChecker";
			urlParameters = "postID=" + object.get("postID")
					+ "&postContent=" + postContent ;
			
			retJson = Connection.connect(serviceUrl, urlParameters, "POST",
					"application/x-www-form-urlencoded;charset=UTF-8");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return null;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}		
	@GET
	@Path("/sharePost/{postID}")
	public Response sharePsot(@Context HttpServletRequest req ,@PathParam("postID") String postID)
	{
		String currentEmail = (String)req.getSession().getAttribute("currentEmail");
		String serviceUrl = "http://localhost:8888/rest/sharePost";
		String urlParameters = "postID=" + postID + "&currentEmail=" + currentEmail ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
						"application/x-www-form-urlencoded;charset=UTF-8");	
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if(object.get("Status").equals("Ok"))
				return null ;
			else 
				return null ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}
	@GET
	@Path("/newsFeed")
	public Response getNewsFeed(@Context HttpServletRequest req )
	{
		String currentEmail = (String)req.getSession().getAttribute("currentEmail");
		String serviceUrl = "http://localhost:8888/rest/getNewsFeed";
		String urlParameters = "currentEmail=" + currentEmail ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
						"application/x-www-form-urlencoded;charset=UTF-8");	
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if(object.get("Status").equals("Ok"))
			{
				ArrayList<Map> newsFeedPosts = (ArrayList<Map>)object.get("newsFeedPosts");
				System.out.println(newsFeedPosts);
				return Response.ok(new Viewable("/jsp/newsFeed", newsFeedPosts)).build();
			}
			else 
				return null ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}	
}