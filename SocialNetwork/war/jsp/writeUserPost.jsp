<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Write User Post</title>
</head>
<body>

<form action="/social/WritePost" method="post">
  Content : <textarea rows="5" cols="21" name = "postContent"></textarea> <br>
   Privacy : <input type="text" name="privacy" /> <br>
   Feeling : <input type="text" name="feeling" /> <br>
  <input type="submit" value="Post">
  </form>
  </body>
</html>