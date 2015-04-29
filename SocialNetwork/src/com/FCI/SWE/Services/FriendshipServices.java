package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import com.FCI.SWE.NotifCommand.AcceptFriendCommand;
import com.FCI.SWE.NotifCommand.NotifCommnad;
import com.FCI.SWE.NotifCommand.ReadGroupMessageCommand;
import com.FCI.SWE.NotifCommand.ReadMessageCommand;
import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.GroupEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
@Path("/")
@Produces(MediaType.TEXT_PLAIN)

public class FriendshipServices {
	/**
	 * Send Friend Request is used to send a request to anotehr user
	 * 
	 * @param email
	 *            provide the email of the user to send friend request to 
	 * @return Status json
	 */
	@POST
	@Path("/sendFriendReq")
	public String sendFriendReq(@FormParam("currentEmail") String currentEmail ,@FormParam("email") String friendEmail) 
	{
		FriendshipEntity friendship = new FriendshipEntity(currentEmail, friendEmail);
		JSONObject json = new JSONObject();

		if(UserEntity.getUserIDByEmail(friendEmail) == -1)
			json.put("Status", "Failed");
			
			
		if(friendship.sendFriendReq())
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		return json.toJSONString();
	}	

	
	/**
	 * A Service that gets the list of friends through calling
	 * an entity function
	 * @return ArrayList Of Friends Name
	 */
	@POST
	@Path("/friendList")
	public String friendList(@FormParam("currentEmail") String currentEmail) {
		JSONObject object = new JSONObject();
		FriendshipEntity fe = new FriendshipEntity();
		ArrayList <String> friendList = fe.getFriendsNameList(currentEmail);
		object.put("friendList", friendList);
		return object.toString();
	}	

}
