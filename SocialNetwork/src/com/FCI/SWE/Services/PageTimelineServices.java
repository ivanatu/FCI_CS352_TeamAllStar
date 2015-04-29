package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.PageEntity;
import com.FCI.SWE.ServicesModels.PagePostEntity;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PageTimelineServices 
{
	@POST
	@Path("/getPageTimeline")
	public String getPageTimeline(@FormParam("currentEmail") String currentEmail, @FormParam("pageName") String pageName) 
	{	
		JSONObject object = new JSONObject();	
		PagePostEntity ppe = new PagePostEntity();
		ArrayList<Map> pageView = ppe.getTimeLine(pageName, currentEmail);
		
		object.put("Status", "Ok");
		object.put("pageView", pageView);
		return object.toString();
	}
	@POST
	@Path("/getPageTimelineAsAdmin")
	public String getPageTimelineAsAdmin(@FormParam("pageName") String pageName) 
	{		
		JSONObject object = new JSONObject();
				
		PagePostEntity ppe = new PagePostEntity();
		ArrayList<Map> myPage =  ppe.getPageTimelineAsAdmin(pageName);
		
		object.put("Status", "Ok");
		object.put("myPage", myPage);
		return object.toString();
	}		
}