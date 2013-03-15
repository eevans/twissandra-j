<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ page import="java.util.*" %>
<%@ page import="org.opennms.twissandra.api.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Public Tweets</title>
</head>
<body>
<h1>The most recent tweets for ${username}!</h1>

<dl>
<%
List<Tweet> tweets = (List<Tweet>)request.getAttribute("tweets");

for(Tweet tweet : tweets) {
%>
	<dt><%= tweet.getPostedBy() %></dt><dd><%= tweet.getBody() %> <i><%= tweet.getPostedAt() %></i></dd> 
<%
}
%>
</dl>

</body>
</html>