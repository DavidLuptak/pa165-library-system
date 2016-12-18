<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="User detail">
    <jsp:attribute name="body">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            There is no user with this ID.
        </div>
        <a href="${pageContext.request.contextPath}/user" class="btn btn-default">
            <fmt:message key="back"/>
        </a>
    </jsp:attribute>
</my:pagetemplate>
