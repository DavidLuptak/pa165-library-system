<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="${category.name}">
    <jsp:attribute name="body">

        <h2>${category.name}</h2>
        <a href="${pageContext.request.contextPath}/category/index" class="btn btn-default">Back</a>
        <my:bookTable books="${category.books}"/>

    </jsp:attribute>
</my:pagetemplate>
