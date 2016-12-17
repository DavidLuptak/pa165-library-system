<%-- 
    Author     : Bedrich Said
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Login to the library">
    <jsp:attribute name="body">
        <h3>Successfully logged in.</h3>
        <p>Currently logged user is: <c:out value="${authenticatedUser}"/></p>
    </jsp:attribute>
</my:pagetemplate>