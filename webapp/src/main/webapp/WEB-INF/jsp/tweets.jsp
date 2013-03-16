<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ page import="java.util.*" %>
<%@ page import="org.opennms.twissandra.api.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Public Tweets</title>
</head>
<body>

<sec:authorize access="isAnonymous()">
<a href="spring_security_login">Login</a>
<h1>The most recent tweets</h1>

</sec:authorize>
<sec:authorize access="isAuthenticated()">
<h1>The most recent tweets for ${username}!</h1>
</sec:authorize>


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