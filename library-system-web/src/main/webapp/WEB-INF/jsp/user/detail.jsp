<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<my:pagetemplate title="User detail">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-sm-6">
                <table class="table table-detail">
                    <tbody>
                    <tr>
                        <th scope="row" class="col-sm-4">First Name</th>
                        <td><c:out value="${user.firstName}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4">Last Name</th>
                        <td><c:out value="${user.lastName}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4">Email</th>
                        <td><c:out value="${user.email}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4">Address</th>
                        <td><c:out value="${user.address}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4">Role</th>
                        <td><c:out value="${user.userRole}"/></td>
                    </tr>
                    </tbody>

                </table>
            </div>
        </div>

    </jsp:attribute>
</my:pagetemplate>