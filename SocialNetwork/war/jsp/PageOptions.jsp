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

            <h2><span>Page Options </span></h2>
          
			<br>
			
			<form action="/social/PageCreationPage" method="post">
			     <input type = "submit" value="Create a page">
			</form>
			
			<br> <br>
			
			<form action="/social/getPageTimeline" method="post">
				search for a page :<br> <input type="text" name = "pageName"></input><br><br>
			     <input type = "submit" value="Search">
			</form>
			
			<br> <br>
			
			<h2><span>Your Pages</span></h2>
          
			<br>
				<c:forEach items = "${it}" var = "cur"  >
				    <a href = "/social/getPageTimelineAsAdmin/<c:out value="${cur.name}"/>"><c:out value="${cur.name}"/></a><br>
				     Page Likers: <c:out value="${cur.likes}"/><br>
				     --------------------------------------------<br>
				</c:forEach>
				
     
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