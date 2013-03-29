<!--
 * Copyright 2013, Matt Brozowski and Eric Evans
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
-->
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
                    <sec:authorize access="isAuthenticated()">
                     <c:if test="${principal.name != q}">
                       <c:url var="modifyFriendUrl" value="/modify-friend">
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
                     </c:if>
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
            
            <sec:authorize access="isAuthenticated()">
			    <h2 class="grid_4 suffix_5">${principal.name}&rsquo;s Friends</h2>
                <ul id="timeline" class="grid_9 alpha">
                <!--  ${friends} -->
				 <c:forEach var="friend" items="${friends}">
				    <c:url var="userUrl" value="/${friend}" />
            		<li>
                		<a href="${userUrl}" class="username">${friend}</a>
            		</li>
				 </c:forEach>                	
              </ul>
            </sec:authorize>
        </div>
    </div>
