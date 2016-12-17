<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:pagetemplate title="Users">
<jsp:attribute name="body">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/user/create">
        <span class="glyphicon glyphicon-plus"></span> New user</a>
    <table class="table table-striped table-hover table-books">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Address</th>
            <th>Role</th>
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
                    <a class="btn btn-default" title="List all loans" href="${pageContext.request.contextPath}/user/${user.id}/allLoans">
                        <span class="glyphicon glyphicon-list-alt" ></span>
                    </a>
                    <a class="btn btn-default" title="List returned loans" href="${pageContext.request.contextPath}/user/${user.id}/returnedLoans">
                        <span class="glyphicon glyphicon-list-alt" ></span>
                    </a>
                    <a class="btn btn-default" title="List not returned loans" href="${pageContext.request.contextPath}/user/${user.id}/notReturnedLoans">
                        <span class="glyphicon glyphicon-list-alt" ></span>
                    </a>
                    <a class="btn btn-default" title="New loan" href="${pageContext.request.contextPath}/user/${user.id}/createLoan">
                        <span class="glyphicon glyphicon-plus" ></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>
