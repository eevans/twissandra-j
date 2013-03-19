<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.opennms.twissandra.api.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


        <sec:authorize access="isAuthenticated()">
          <c:if test="${principal.name != username}">
            <c:url var="modifyFriendUrl" value="/modify-friend">
            	<c:param name="next" value="${request.path}" />
            </c:url>
            <c:set var="action">
              <c:choose>
                <c:when test="${isFriend}">remove</c:when>
                <c:otherwise>add</c:otherwise>
              </c:choose>
            </c:set>
            <c:set var="buttonLabel">
              <c:choose>
                <c:when test="${isFriend}">Remove Friend</c:when>
                <c:otherwise>Add Friend</c:otherwise>
              </c:choose>
            </c:set>
            <form method="POST" action="${modifyFriendUrl}">
                <input type="hidden" name="friend" value="${username}" />
                <input type="hidden" name="action" value="${action}" />
                <input type="submit" value="${buttonLabel}" />
            </form>
          </c:if>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <c:url var="loginUrl" value="/login">
            	<c:param name="next" value="/${username}" />
            	<c:param name="q" value="${username}" />
            </c:url> 
           <a href="${loginUrl}">
               Login to add ${username} as a friend
           </a>
        </sec:authorize>

