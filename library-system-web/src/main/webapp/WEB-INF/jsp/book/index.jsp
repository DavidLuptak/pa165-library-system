<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Books">
<jsp:attribute name="body">
    <c:if test="${loggedUser.isAdmin()}">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/book/create">
        <span class="glyphicon glyphicon-plus"></span> New book</a>
    </c:if>
   <my:bookTable books="${books}"/>
</jsp:attribute>
</my:pagetemplate>
