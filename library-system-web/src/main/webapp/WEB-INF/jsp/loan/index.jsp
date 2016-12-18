<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<my:pagetemplate title="Loans">
<jsp:attribute name="body">
    <c:if test="${!loggedUser.isAdmin()}">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/loan/create">
        <span class="glyphicon glyphicon-plus"></span> New loan</a>
    </c:if>
    <table class="table table-striped table-hover table-books">
        <thead>
        <tr>
            <c:if test="${user.isAdmin()}">
            <th>First Name</th>
            <th>Last Name</th>
            </c:if>
            <th>Book</th>
            <th>LoanDate</th>
            <th>ReturnDate</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="loan" items="${loans}">
            <tr>
                <c:if test="${loggedUser.isAdmin()}">
                <td><c:out value="${loan.user.firstName}"/></td>
                <td><c:out value="${loan.user.lastName}"/></td>
                </c:if>
                <td><c:out value="${loan.bookCopy.book.title}"/></td>
                <td><javatime:format value="${loan.loanDate}" pattern="dd.MM.yyyy HH:mm"/></td>
                <td><javatime:format value="${loan.returnDate}" pattern="dd.MM.yyyy HH:mm"/></td>
                <td>
                    <a class="btn btn-default" title="Detail" href="${pageContext.request.contextPath}/loan/detail/${loan.id}">
                        <span class="glyphicon glyphicon-search"></span>
                    </a>
                    <c:if test="${loan.returnDate == null && loggedUser.isAdmin()}">
                    <a class="btn btn-danger" title="Return" href="${pageContext.request.contextPath}/loan/return/${loan.id}">
                        <span class="glyphicon glyphicon-ok-sign"></span>
                    </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>
