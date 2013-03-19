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

    <c:set var="action">
      <c:choose>
        <c:when test="${added}">Added</c:when>
        <c:otherwise>Removed</c:otherwise>
      </c:choose>
    </c:set>

    <c:set var="actionDescr">
      <c:choose>
        <c:when test="${added}">added to</c:when>
        <c:otherwise>removed from</c:otherwise>
      </c:choose>
    </c:set>

    <div class="grid_9 alpha">
        <h2 class="grid_4 suffix_5">Friend Successfully ${action}</h2>
        <p>The user has been ${actionDescr} your friends list.</p>
    </div>
