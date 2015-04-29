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
public class FriendshipController {

	/**
	 * Action function to render add friend page of application, this page contains a
	 * form with add friend button and a text field to take the friend email as an input 
	 * 
	 * @return add friend page
	 */
	@GET
	@Path("/addFriend")
	public Response addFriendPage(@Context HttpServletRequest req){
		
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		return Response.ok(new Viewable ("/jsp/friends/addFriend")).build();
	}

	/**
	 * a controller that calls the send friend request service and provides it with the 
	 * suitable parameters 
	 * @param email 
	 * 				the friend email to add
	 * @return JSON status
	 */
	@POST
	@Path("/sendFriendReq")
	public Response friendReq (@Context HttpServletRequest req, @FormParam("email") String email)
	{
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		//if the field is empty do nothing
		if(email.equals(""))
		{
			return null ;	
		}
		String serviceUrl = "http://localhost:8888/rest/sendFriendReq";
		String urlParameters = "email=" + email + "&currentEmail=" + currentEmail;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok("Friend Request Sent!").build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok("Error sending friend Request !").build();
	}

	/**
	 * Friend List Controller that's used to call the friendList service which returns an
	 * array list of all the names of the current user's friends and then render the friends page
	 * @return Response of the friends jsp page 
	 */
	@GET
	@Path("/friendList")
	@Produces("text/html")
	public Response FriendList(@Context HttpServletRequest req) 
	{
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		String retJson = Connection.connect(
				"http://localhost:8888/rest/friendList", "currentEmail=" + currentEmail
				,"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			ArrayList<String> friendList = (ArrayList) object.get("friendList");
			 return Response.ok(new Viewable ("/jsp/friends/friends",friendList)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
