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
public class HashtagController 
{
	@GET
	@Path("/getHashtagOptions")//returns trends when called
	public Response hashTagOptionsPage() 
	{	
		String retJson = Connection.connect(
			"http://localhost:8888/rest/hashTagOptionsPageAndTrends"
			, "",
			"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Ok"))
			{
				ArrayList<Map> trends = (ArrayList)object.get("trends");
				return Response.ok(new Viewable("/jsp/hashTagOptions.jsp", trends)).build();
			}
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;		
	}
	@POST
	@Path("/getHashtagTimeline")
	public Response getHashtagTimeline(@Context HttpServletRequest req, @FormParam("HashtagName") String HashtagName) 
	{	
		String currentEmail = (String) req.getSession().getAttribute("currentEmail");
		String urlParameters = "HashtagName=" + HashtagName  + "&currentEmail=" + currentEmail;
		String retJson = Connection.connect(
			"http://localhost:8888/rest/getHashtagTimeline"
			, urlParameters,
			"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Ok"))
			{
				req.getSession(true).setAttribute("hashtagName", HashtagName);
				ArrayList<Map> HashTagPosts = (ArrayList)object.get("HashTagPosts");
				return Response.ok(new Viewable("/jsp/hashtagTimeline.jsp", HashTagPosts)).build();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;				
	}	
}