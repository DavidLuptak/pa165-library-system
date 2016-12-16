<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionUrl" value="${pageContext.request.contextPath}/category/create"/>
<c:set var="backUrl" value="${pageContext.request.contextPath}/category/index"/>

<my:pagetemplate title="New category">
    <jsp:attribute name="body">
        <h3>New category</h3>
        <%@include file="form.jspf"%>
    </jsp:attribute>
</my:pagetemplate>
