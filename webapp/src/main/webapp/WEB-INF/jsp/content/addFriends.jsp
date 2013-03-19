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

<!--  This takes several model attributes

initially there are none which means this is the first time viewing

There is a form to query for other users which is then a GET parameter 'q' with the name to query

Once the query is performed in the controller:
 'q' is passed thru with query name
 'searched' is set to true
 'isFound' is set to true if we found the user
 'isFriend' is set to true if the found user is already our friend


 -->

    <h2 class="grid_4 suffix_5">Add Friends</h2>
    <div class="grid_9 alpha">
        <form method="GET" class="grid_6">
            <input type="text" name="q" value="${q}" />
            <input type="submit" value="Search" />
        </form>
        <div class="grid_6">
            <c:choose>
             <c:when test="${searched}">
               <c:choose>
                 <c:when test="${isFound}">
                    <p>Hooray, your friend ${q} is on the site!</p>
                    <sec:authorize access="isAuthenticated() and q != principal.name">
                       <c:url var="modifyFriendUrl" value="/modifyFriend">
                         <c:param name="next" value="${request.path}" />
                         <c:param name="q" value="${q}" />
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
                         <input type="hidden" name="action" value="${action}" />
                         <input type="hidden" name="friend" value="${q}" />
                         <input type="submit" value="${buttonLabel}" />
                       </form>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                      <c:url var="loginUrl" value="/login">
                        <c:param name="next" value="${request.path}" />
                        <c:param name="q" value="${q}" />
                      </c:url>
                        <a href="${loginUrl}">
                            Login to add ${q} as a friend
                        </a>
                    </sec:authorize>
                  </c:when>
                  <c:otherwise>
                    <p>There was nobody with username ${q}</p>
                  </c:otherwise>
                </c:choose>
              </c:when>
              <c:otherwise>
                <p>Enter a username above to see if they are on the site!</p>
              </c:otherwise>
            </c:choose>
        </div>
    </div>
