<%-- 
    Author     : Bedrich Said
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="Login to the library">
    <jsp:attribute name="body">
        <h3>Successfully logged in.</h3>
        <p>Currently logged user is:</p>
        <table>
            <tr><td>First Name:</td><td><c:out value="${authenticatedUser.firstName}"/></td></tr>
            <tr><td>Last Name:</td><td><c:out value="${authenticatedUser.lastName}"/></td></tr>
            <tr><td>E-mail:</td><td><c:out value="${authenticatedUser.email}"/></td></tr>
            <tr><td>User Role:</td><td><c:out value="${authenticatedUser.userRole}"/></td></tr>
            <tr><td>Address:</td><td><c:out value="${authenticatedUser.address}"/></td></tr>
            <tr><td>Loans:</td><td>${fn:length(authenticatedUser.loans)}</td></tr>
        </table>
        <p><c:out value="${authenticatedUser}"/></p>
    </jsp:attribute>
</my:pagetemplate>