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


@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PageServices {
	
	@POST
	@Path("/createPage")
	public String createPage(@FormParam("currentEmail") String currentEmail, @FormParam("pageName") String pageName,
			@FormParam("pageType") String pageType, 
			@FormParam("pageCategory") String pageCategory) {
		
		JSONObject object = new JSONObject();		
		PageEntity page = new PageEntity(pageName, pageType, pageCategory, currentEmail);
		
		JSONObject json = new JSONObject();
		if(page.createPage())
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		
		return json.toJSONString();
	}
	@POST
	@Path("/pageList")
	public String pageList(@FormParam("currentUserEmail")String email) {
		JSONObject object = new JSONObject();
		PageEntity pe = new PageEntity();
		ArrayList <Map> pageList = pe.getPageNameList(email);		
		object.put("pageList", pageList);
		return object.toString();
	}	
	@POST
	@Path("/likePage")
	public String pageInfo(@FormParam("pageName") String pageName,
			@FormParam("currentUserEmail") String currentUserEmail) {
		
		JSONObject object = new JSONObject();
		
		PageEntity page = new PageEntity();
		
		JSONObject json = new JSONObject();
		if(page.likePage(currentUserEmail, pageName))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		
		return json.toJSONString();
	}	
}