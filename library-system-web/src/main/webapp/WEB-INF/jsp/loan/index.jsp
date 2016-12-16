<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:pagetemplate title="Loans">
<jsp:attribute name="body">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/loan/create">
        <span class="glyphicon glyphicon-plus"></span> New loan</a>
    <table class="table table-striped table-hover table-books">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Book</th>
            <th>LoanDate</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="loan" items="${loans}">
            <tr>
                <td><c:out value="${loan.user.firstName}"/></td>
                <td><c:out value="${loan.user.lastName}"/></td>
                <td><c:out value="${loan.bookCopy.book.title}"/></td>
                <td><c:out value="${loan.loanDate}"/></td>
                <td>
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/loan/detail/${loan.id}">
                        <span class="glyphicon glyphicon-search" title="Detail"></span>
                    </a>
                    <c:if test="${loan.returnDate == null}">
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/loan/return/${loan.id}">
                        <span class="glyphicon glyphicon-ok-sign" title="Return"></span>
                    </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>
