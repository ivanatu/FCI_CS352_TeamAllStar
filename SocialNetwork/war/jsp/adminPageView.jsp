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

            
            <h2><span><%= session.getAttribute("pageName(AdminView)") %> </span></h2>
            <div class="clr"></div>
			
			<form action="/social/createPagePost" method="post">				
			  		What have you been up to ?<br> <textarea rows="4" cols="40" name = "postContent"></textarea> <br>
			  			</select>&nbsp;					  	
		  			Privacy : <select name = "privacy">
			  				<option value = "public">Public</option>
			  				<option value = "private">Private</option>
			  			</select>&nbsp;	
			     <input type = "submit" value="post">
				<h3>______________________________________________________</h3>			
			</form>
				<c:forEach items = "${it}" var="iter" >
			    <h2><c:out value="${iter.postowner}"/></h2>
			    <h3><c:out value="${iter.postContent}"/><br>
			    	<c:out value="${iter.likes}"/><br>
			    	<c:out value="${iter.seen}"/><br>	
______________________________________________________				    				    
				</c:forEach></h3>					               

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
 </body>
</html>