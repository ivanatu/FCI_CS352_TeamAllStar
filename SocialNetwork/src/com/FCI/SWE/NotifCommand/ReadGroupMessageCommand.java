package com.FCI.SWE.NotifCommand;

import com.FCI.SWE.Services.NotificationServices;
import com.FCI.SWE.ServicesModels.GroupMessageEntity;


public class ReadGroupMessageCommand implements NotifCommnad {

	private GroupMessageEntity me ;
	public ReadGroupMessageCommand(String notID, String currentEmail) 
	{
		me = new GroupMessageEntity(Long.parseLong(notID) ,currentEmail); 
	}

	@Override
	public boolean excute() {
		
		if(me.readGroupMessage())
		{
			NotificationServices.response = me.getMesg();
			return true ;
		}
		return false ;
	}

}
