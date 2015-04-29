package com.FCI.SWE.Controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class UserTimelineController
{	
	@GET
	@Path("/accountSearch")
	public Response accountSearchPage()
	{
		return Response.ok(new Viewable("/jsp/accountSearch.jsp")).build();
	}		
	@GET
	@Path("/timelinePage")
	public Response userTimeline(@Context HttpServletRequest req )
	{
		String currentEmail = (String)req.getSession().getAttribute("currentEmail");
		String serviceUrl = "http://localhost:8888/rest/timelinePage";
		String urlParameters = "currentEmail=" + currentEmail ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
						"application/x-www-form-urlencoded;charset=UTF-8");	
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{
				ArrayList<Map> myposts = (ArrayList<Map>) object.get("posts");
				return Response.ok(new Viewable("/jsp/userTimeline",myposts)).build();//the same page refreshed only after page creation
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	@POST
	@Path("/searchForAccount")
	public Response searchForAccount(@Context HttpServletRequest req, @FormParam("accountEmail") String accountEmail)
	{
		String currentEmail = (String)req.getSession().getAttribute("currentEmail");
		String serviceUrl = "http://localhost:8888/rest/searchForAccount";
		String urlParameters = "accountEmail=" + accountEmail + "&currentEmail=" + currentEmail;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
						"application/x-www-form-urlencoded;charset=UTF-8");	
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{
				ArrayList<Map> accountPosts = (ArrayList<Map>) object.get("accountPosts");
				req.getSession(true).setAttribute("accountName", (String)object.get("accountName"));
				if(object.get("friend").equals("true"))
				{ 
					req.getSession(true).setAttribute("accountEmail", accountEmail);
					return Response.ok(new Viewable("/jsp/friendTimeline.jsp", accountPosts)).build();
				}
				else
				{
					return Response.ok(new Viewable("/jsp/notFriendTimeline.jsp", accountPosts)).build();	
				}
			}

		} catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}		
	
}