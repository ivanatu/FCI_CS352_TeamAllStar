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
public class MessagesController {
	@GET
	@Path("/sendMessage")
	public Response sendMesg(@Context HttpServletRequest req) 
	{		
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		return Response.ok(new Viewable("/jsp/Messages")).build();
	}	
	@POST
	@Path("/sendMesg")
	@Produces("text/html")
	public Response sendMessage(@Context HttpServletRequest req, @FormParam("message") String Message,
			@FormParam("recEmail") String RecEmail) {
		
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		//if there's no message typed do nothing
		if(Message.equals("")  || RecEmail.equals(""))
		{
			return null ;	
		}
		String urlParameters = "message=" + Message + "&recEmail=" + RecEmail +"&currentEmail=" + currentEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/sendMessage"
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
		return Response.ok("Message Sent Successfully!").build();
	}
	
	@GET
	@Path("/sendGroupMessage")
	public Response sendGroupMessage(@Context HttpServletRequest req)
	{
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}		
		return Response.ok(new Viewable("/jsp/GroupMessages")).build();
	}
	
	@POST
	@Path("/createMessageGroup")
	public Response createMessageGroup(@Context HttpServletRequest req)
	{
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}		
		return Response.ok(new Viewable("/jsp/createMessageGroup")).build();
	}
	
	@POST
	@Path("/groupMsgEmails")
	@Produces("text/html")
	public Response createGroupMessage(@Context HttpServletRequest req , @FormParam("chatName") String chatName,
			@FormParam("recEmails") String recEmails)
		{
			String currentEmail = req.getSession().getAttribute("currentEmail").toString();
			if(currentEmail.equals(""))
			{
				return Response.ok(new Viewable("/jsp/error")).build();
			}
		//if there's no message typed do nothing
			if(chatName.equals("")  || recEmails.equals(""))
			{
				return null ;	
			}
		String urlParameters = "chatName=" + chatName + "&recEmails=" + recEmails + "&currentEmail=" + currentEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/groupMesgEmails"
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
		return Response.ok(new Viewable("/jsp/GroupMessages")).build();
	}
	
	@POST
	@Path("/groupMesg")
	@Produces("text/html")
	public Response sendGroupMessage(@Context HttpServletRequest req, @FormParam("message") String message,
			@FormParam("chatName") String chatName) 
	{		
		String currentEmail = req.getSession().getAttribute("currentEmail").toString();
		if(currentEmail.equals(""))
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}		
		//if there's no message typed do nothing
		if(message.equals("") || chatName.equals(""))
		{
			return null ;	
		}		
		String urlParameters = "message=" + message + "&chatName=" + chatName + "&currentEmail=" + currentEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/groupMesg"
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
		return Response.ok("Message Sent Successfully!").build();
	}
}