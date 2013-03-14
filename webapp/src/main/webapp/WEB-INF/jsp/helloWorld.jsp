<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ page import="java.util.*" %>
<%@ page import="org.opennms.twissandra.api.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World Page</title>
</head>
<body>
<h1>Sriptlet: <%= request.getAttribute("message") %></h1>
<h1>EL: ${message}</h1>


</body>
</html>