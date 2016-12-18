<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate>
    <jsp:attribute name="title"><fmt:message key="book.title"/> </jsp:attribute>
    <jsp:attribute name="body">
    <c:if test="${loggedUser.isAdmin()}">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/book/create">
        <span class="glyphicon glyphicon-plus"></span> <fmt:message key="book.create"/></a>
    </c:if>
   <my:bookTable books="${books}"/>
</jsp:attribute>
</my:pagetemplate>
