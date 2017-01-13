<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:pagetemplate title="Users">
<jsp:attribute name="body">
    <table class="table table-striped table-hover table-books">
        <thead>
        <tr>
            <th><fmt:message key="user.name"/></th>
            <th><fmt:message key="user.surname"/></th>
            <th><fmt:message key="user.email"/></th>
            <th><fmt:message key="user.address"/></th>
            <th><fmt:message key="user.role"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.address}"/></td>
                <td><c:out value="${user.userRole}"/></td>
                <td>
                    <a class="btn btn-default" title="Loans" href="${pageContext.request.contextPath}/user/${user.id}/loans">
                        <span class="glyphicon glyphicon-list-alt" ></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>
