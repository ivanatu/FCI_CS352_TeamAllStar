package com.FCI.SWE.Services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.NotifCommand.AcceptFriendCommand;
import com.FCI.SWE.NotifCommand.NotifCommnad;
import com.FCI.SWE.NotifCommand.ReadGroupMessageCommand;
import com.FCI.SWE.NotifCommand.ReadMessageCommand;
import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.GroupMessageEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
import com.FCI.SWE.ServicesModels.NotificationEntity;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class NotificationServices 
{
	static public String response = ""; //the returned value to jsp page
	
	@POST
	@Path("/notificationReaction")
	public String notificationsReaction( @FormParam("currentEmail") String currentEmail, @FormParam("notID") String notID,
									@FormParam("notType") String notType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		JSONObject json = new JSONObject();
		NotifCommnad ncom = (NotifCommnad)Class.forName("com.FCI.SWE.NotifCommand." + notType).getConstructor(String.class, String.class).newInstance(notID, currentEmail);	
		if(ncom.excute())
		{
			json.put("Status", "OK");
			json.put("Response", response);
		}
		else
			json.put("Status", "Failed");
		return json.toJSONString();
	}	
	@POST
	@Path("/showNotifications")
	public String notifications(@FormParam("currentEmail") String currentEmail) 
	{
		JSONObject object = new JSONObject();
		NotificationEntity ne = new NotificationEntity() ;
		ArrayList <Map> groupMessages = ne.getNotifications(currentEmail);
		object.put("notifications", groupMessages);
		return object.toString();
	}
}
