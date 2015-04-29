/**
 * 
 */
/**
 * @author Baron
 *
 */
package com.FCI.SWE.NotifCommand;

import com.FCI.SWE.Services.NotificationServices;
import com.FCI.SWE.ServicesModels.FriendshipEntity;

public class AcceptFriendCommand implements NotifCommnad {

	private FriendshipEntity fe ;
	public AcceptFriendCommand(String netID, String currentEmail)
	{
		fe =  new FriendshipEntity(netID, currentEmail) ;
	}
	@Override
	public boolean excute() 
	{
		if(fe.accpetFriendReq())
		{
			NotificationServices.response = "Friend request confirmed";
			return true ;
		}
		return false ;
	}

}