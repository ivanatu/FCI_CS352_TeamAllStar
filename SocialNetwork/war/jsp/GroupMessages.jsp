<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>SocialNet</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

         <h1><a href="#">SOCIALNETWORK</a></h1>
 
            <h2><span>Send Group Message </span></h2>
			
			<form action="/social/createMessageGroup" method="post">
						
				Create new chat group <br> <input type = "submit" value="Create">
				
			</form>
			
			<br> OR <br><br>
			
			<form action="/social/groupMesg" method="post">			
				
				Enter chat name <br> <input type = "text" name="chatName"></input><br>
			
				Message :<br> <textarea rows="5" cols="21" name = "message"></textarea><br><br>
				
				<input type = "submit" value="Send">
				
			</form>
		
           <h2 class="star"> Menu</h2>           
             <a href="/social/timelinePage/"><%=request.getSession().getAttribute("currentName")%>'s Timeline</a>
              <a href="/social/newsFeed/">News Feed</a>      
              <a href="/social/showNotifications/">Notifications</a>
              <a href="/social/addFriend/">Add Friend</a>
              <a href="/social/join/">Join Group</a>
              <a href="/social/group/">Create Group</a>
              <a href="/social/friendList/">My Friends</a>
			  <a href="/social/sendMessage/">Send Message</a>
			  <a href="/social/sendGroupMessage/">Send Group Message</a>
			  <a href="/social/pageOptions/">Page options</a>
			  <a href="/social/getHashtagOptions/">Hashtag Options</a>
			  <a href="/social/accountSearch/">Account Search</a>
			  <a href="/social/logout/">LogOut</a>
</body>
</html>