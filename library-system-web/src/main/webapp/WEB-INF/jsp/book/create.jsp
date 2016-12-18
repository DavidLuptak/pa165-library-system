<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="actionUrl" value="${pageContext.request.contextPath}/book/create"/>
<c:set var="backUrl" value="${pageContext.request.contextPath}/book/index"/>
<c:set var="editCopies" value="true"/>

<my:pagetemplate>
    <jsp:attribute name="title"><fmt:message key="book.create"/></jsp:attribute>
    <jsp:attribute name="body">
        <h3><fmt:message key="book.create"/></h3>
        <%@include file="form.jspf" %>
    </jsp:attribute>
</my:pagetemplate>
