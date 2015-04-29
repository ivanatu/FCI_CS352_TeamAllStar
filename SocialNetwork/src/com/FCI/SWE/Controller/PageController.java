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
public class PageController 
{
	@GET
	@Path("/pageOptions")
	public Response pageOptions(@Context HttpServletRequest req) 
	{
		String currentUserEmail = (String) req.getSession().getAttribute("currentEmail");
		if(currentUserEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		String retJson = Connection.connect(
				"http://localhost:8888/rest/pageList", "currentUserEmail="+currentUserEmail
				,"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			ArrayList<Map> pageList = (ArrayList) object.get("pageList");
			 return Response.ok(new Viewable ("/jsp/pageOptions", pageList)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/PageCreationPage")
	public Response creationPageRender() {
		return Response.ok(new Viewable("/jsp/pageCreation")).build();
	}
	@POST
	@Path("/createPage")
	public Response createPage(@Context HttpServletRequest req, @FormParam("pageName") String pageName,
			@FormParam("pageType") String pageType, 
			@FormParam("pageCategory") String pageCategory) {
		String currentUserEmail = (String) req.getSession().getAttribute("currentEmail");
		if(currentUserEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		if(pageName.equals("")  || pageType.equals("") || pageCategory.equals(""))
		{
			return null ;	
		}
		
		String urlParameters = "pageName=" + pageName + "&pageType=" + pageType + "&pageCategory=" + pageCategory + 
				"&currentUserEmail="+currentUserEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/createPage"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/pageOptions")).build();
	}

	@GET
	@Path("/likePage/{pageName}")
	public Response likePage(@Context HttpServletRequest req , @PathParam("pageName") String pageName)
	{
		String currentUserEmail = (String) req.getSession().getAttribute("currentEmail");
		
		if(currentUserEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		if(pageName.equals(""))
		{
			return null ;	
		}
		String urlParameters = "pageName=" + pageName + "&currentUserEmail="+currentUserEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/likePage"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}