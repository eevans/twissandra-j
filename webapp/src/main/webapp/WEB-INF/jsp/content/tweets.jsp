<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ page import="java.util.*" %>
<%@ page import="org.opennms.twissandra.api.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

    <h2 class="grid_4 suffix_5">Public Timeline</h2>
    <ul id="timeline" class="grid_9 alpha">
    <c:choose>
      <c:when test="${empty tweets}">
            <li>There are no tweets yet.  Make sure to post one!</li>
      </c:when>
      <c:otherwise>
        <c:forEach var="tweet" items="${tweets}">
            <c:url var="posterUrl" value="/${tweet.postedBy}/index.htm" />
            <li>
                <a href="${posterUrl}" class="username">${tweet.postedBy}</a>
                <span class="body">${fn:escapeXml(tweet.body)}</span>
            </li>
        </c:forEach>
      </c:otherwise>
    </c:choose>
    <c:if test="${next}">
        <li class="more"><a href="?start=${next}">More</a></li>
    </c:if>
    </ul>

