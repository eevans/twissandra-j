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

    <div class="grid_9 alpha">
        <div class="auth grid_4 prefix_1 alpha" id="signin">
            <h2 class="grid_4">Sign In</h2>
            <c:url var="loginUrl" value="/j_spring_security_check" />
            <form method="POST" action="${loginUrl}">
                <ul>
		     	    <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
		     	    <li>
		     	      <ul class="errorlist">
		     	      	<li>Login Failed:${SPRING_SECURITY_LAST_EXCEPTION.message}</li>
		     	      </ul>
		     	    </li>
		     	    </c:if>
                    <li><label for="id_username">Username:</label> <input id="id_username" type="text" name="j_username" maxlength="30" /></li>
					<li><label for="id_password">Password:</label> <input id="id_password" type="password" name="j_password" /></li>
                </ul>
                <input type="submit" value="Sign In" />
            </form>
        </div>

        <div class="auth grid_4 omega" id="signup">
            <h2 class="grid_4">Sign Up</h2>
            <c:url var="registrationUrl" value="/register" />
            <form method="POST" action="${registrationUrl}">
                <ul>
                	<c:if test="${not empty registration_error}"><li><ul class="errorlist"><li>${registration_error}</li></ul></li></c:if>
					<li><label for="id_username">Username:</label> <input id="id_username" type="text" name="j_username" maxlength="30" /></li>
					<li><label for="id_password">Password1:</label> <input id="id_password" type="password" name="j_password" /></li>
					<li><label for="id_password2">Password2:</label> <input id="id_password2" type="password" name="j_password2" /></li>
		        </ul>
                <input type="submit" value="Sign Up" />
            </form>
        </div>
    </div>
