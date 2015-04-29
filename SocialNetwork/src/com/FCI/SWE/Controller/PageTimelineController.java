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
public class PageTimelineController 
{
	@GET
	@Path("/getPageTimelineAsAdmin/{pageName}")
	public Response getPageTimelineAsAdmin(@Context HttpServletRequest req, @PathParam("pageName") String pageName)
	{
		if(pageName.equals(""))
		{
			return null ;	
		}
		String urlParameters = "pageName=" + pageName;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/getPageTimelineAsAdmin"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			ArrayList <Map> myPage = (ArrayList) object.get("myPage");
			if (object.get("Status").equals("Ok"))
			{
				req.getSession(true).setAttribute("pageName(AdminView)", pageName);			
				return Response.ok(new Viewable ("/jsp/adminPageView.jsp", myPage)).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@POST
	@Path("/getPageTimeline")
	public Response getPageTimeline(@Context HttpServletRequest req, @FormParam("pageName") String pageName)
	{	
		if(pageName.equals(""))
		{
			return null ;	
		}
		String currentEmail = (String) req.getSession().getAttribute("currentEmail");
		String urlParameters = "pageName=" + pageName + "&currentEmail=" + currentEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/getPageTimeline"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			ArrayList<Map> pageView = (ArrayList) object.get("pageView");
			if (object.get("Status").equals("Ok"))
			{
				req.getSession(true).setAttribute("pageName", pageName);			
				return Response.ok(new Viewable ("/jsp/pageView.jsp", pageView)).build();
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}	
}