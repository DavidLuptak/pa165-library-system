<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

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
                <td><javatime:format value="${loan.loanDate}" pattern="dd.MM.yyyy HH:mm"/></td>
                <td>
                    <a class="btn btn-default" title="Detail" href="${pageContext.request.contextPath}/loan/detail/${loan.id}">
                        <span class="glyphicon glyphicon-search"></span>
                    </a>
                    <c:if test="${loan.returnDate == null}">
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
