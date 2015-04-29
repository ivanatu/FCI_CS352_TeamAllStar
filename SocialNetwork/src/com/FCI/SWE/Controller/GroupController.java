package com.FCI.SWE.Controller;

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
public class GroupController {
	/**
	 * Action function to render create group page of application, this page contains a
	 * form with create a group button and some text fields for inputs 
	 * 
	 * @return create group page
	*/
	@GET
	@Path("/group")
	public Response group(@Context HttpServletRequest req) {

		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		return Response.ok(new Viewable("/jsp/GroupViews/createGroup")).build();
	}
	
	/**
	 * Action function to render join group page of application, this page contains a
	 * form with a button to join a  group and a textfield to take the id of the group as
	 * input
	 *
	 * 
	 * @return join group page
	*/	
	@GET
	@Path("/join")
	public Response join(@Context HttpServletRequest req) {

		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		return Response.ok(new Viewable("/jsp/GroupViews/joinGroup")).build();
	}
	/**
	 * a function the calls the create group service
	 *
	 * @param userID 
	 *            the user created the group to be sent to the service
	 * @param name 
	 *            the name of the group to be sent to the service 
	 * @param desc 
	 *            the description of the group to be sent to the service
	 * @param privacy 
	 *            the privacy of the group to be sent to the service
	 * 			
	 * @return status of the creation of the group
	 */	
	@POST
	@Path("/CreateGroup")
	public Response createGroup(@Context HttpServletRequest req ,@FormParam("name") String name,
			@FormParam("desc") String desc, @FormParam("privacy") String privacy) {
		
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		
		String serviceUrl = "http://localhost:8888/rest/CreateGroupService";
		String urlParameters = "currentEmail=" + currentEmail
				+ "&name=" + name + "&desc=" + desc + "&privacy=" + privacy;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok("Group Created Successfully!").build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * a function the calls the join group service
	 * @param gpID
	 * 			the group ID to be passed to the service
	 * 		 
	 * @return status of joining a group 
	 */	
	@POST
	@Path("/JoinGroup")
	public Response joinGroup(@Context HttpServletRequest req,@FormParam("gpID") String gpID) 
	{
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		
		String serviceUrl = "http://localhost:8888/rest/JoinGroupService";
		//String serviceUrl = "http://localhost:8888/rest/JoinGroupService";
		String urlParameters = "currentEmail=" + currentEmail
				+ "&gpID=" + gpID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok("You've joined the group").build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}	
}