package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.GroupEntity;


@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class GroupServices {

	/**
	 * Create a group is used to create a group and allows user to 
	 * be a memeber in the group
	 * @param userID 
	 *            the user created the group
	 * @param name 
	 *            the name of the gorup 
	 * @param desc 
	 *            the description of the group
	 * @param privacy 
	 *            the privacy of the group
	 * @return Status json
	 */
	@POST
	@Path("/CreateGroupService")
	public String createGroup(@FormParam("currentEmail") String currentEmail,
			@FormParam("name") String name,
			@FormParam("desc") String desc,
			@FormParam("privacy") String privacy) {
		
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setDescription(desc);
		groupEntity.setName(name);
		groupEntity.setOwnerEmail(currentEmail);
		groupEntity.setPrivacy(privacy);
		JSONObject json = new JSONObject();
		if(groupEntity.saveGroup())
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
//		System.out.println(json.toJSONString());
		return json.toJSONString();
	}
	/**
	 * Join Group allows user to join groups and be memebers  
	 * @param user_id
	 *            the Id of the logged in user 
	 * @param gpID
	 * 			  The group Id to a memeber in
	 * @return Status json
	 */	
	@POST
	@Path("/JoinGroupService")
	public String joinGroup(@FormParam("currentEmail") String currentEmail,
			@FormParam("gpID") String gpID) {
		
		GroupEntity groupEntity = new GroupEntity();
		JSONObject json = new JSONObject();
		if(groupEntity.joinGroup(gpID, currentEmail))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
//		System.out.println(json.toJSONString());
		return json.toJSONString();
	}	

}