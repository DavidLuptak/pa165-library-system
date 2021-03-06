<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ attribute name="loans" required="true" type="java.util.List"%>
<%@ attribute name="user" required="true" type="cz.muni.fi.pa165.library.dto.UserDTO"%>
<%@ attribute name="showReturn"%>


<table class="table table-striped table-hover table-books">
    <thead>
    <tr>
        <c:if test="${user.isAdmin()}">
            <th>First Name</th>
            <th>Last Name</th>
        </c:if>
        <th>Book</th>
        <th>Loaned</th>
        <c:if test="${showReturn}">
            <th>Returned</th>
        </c:if>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="loan" items="${loans}">
        <tr>
            <c:if test="${user.isAdmin()}">
                <td><c:out value="${loan.user.firstName}"/></td>
                <td><c:out value="${loan.user.lastName}"/></td>
            </c:if>
            <td><c:out value="${loan.bookCopy.book.title}"/></td>
            <td><javatime:format value="${loan.loanDate}" pattern="dd.MM.yyyy HH:mm"/></td>
            <c:if test="${showReturn}">
                <td><javatime:format value="${loan.returnDate}" pattern="dd.MM.yyyy HH:mm"/></td>
            </c:if>
            <td>
                <fmt:message key="detail" var="detailString"/>
                <a class="btn btn-default" title="${detailString}" href="${pageContext.request.contextPath}/loan/detail/${loan.id}">
                    <span class="glyphicon glyphicon-search"></span>
                </a>
                <c:if test="${loan.returnDate == null && user.isAdmin()}">
                    <fmt:message key="loan.return" var="returnString"/>
                    <a style="color: white" class="btn btn-danger" title="${returnString}" href="${pageContext.request.contextPath}/loan/return/${loan.id}">
                        <span class="glyphicon glyphicon-ok-sign"></span>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

