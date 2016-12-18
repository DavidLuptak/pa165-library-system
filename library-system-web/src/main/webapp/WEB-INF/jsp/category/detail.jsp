<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="${category.name}">
    <jsp:attribute name="body">

        <h2>${category.name}</h2>
        <a href="${pageContext.request.contextPath}/category/index" class="btn btn-default">
            <fmt:message key="back"/>
        </a>

        <c:if test="${loggedUser.isAdmin()}">
        <a class="btn btn-warning"
           href="${pageContext.request.contextPath}/category/edit/${category.id}">
            <fmt:message key="edit"/>
        </a>
        <a class="btn btn-danger"
           href="${pageContext.request.contextPath}/category/delete/${category.id}">
            <fmt:message key="delete"/>
        </a>
        </c:if>

        <my:bookTable books="${category.books}"/>

    </jsp:attribute>
</my:pagetemplate>
