<%-- 
    Author     : Bedrich Said
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Login to the library">
    <jsp:attribute name="body">
        <h3>List of Book Copies</h3>
        <table class="table table-striped table-hover table-books">
            <thead>
            <tr>
                <th>ID</th>
                <th>Book State</th>
                <th>Available</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td><c:out value="${book.id}"/></td>
                    <td><c:out value="${book.bookState}"/></td>
                    <td>
                        <c:if test="${empty book.loans}">Yes</c:if>
                        <c:if test="${not empty book.loans}">No</c:if>
                    </td>
                    <td>
                        <a class="btn btn-default" href="${pageContext.request.contextPath}/book/detail/${book.id}">
                            <span class="glyphicon glyphicon-search" title="Detail"></span>
                        </a>
                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/book/delete/${book.id}">
                            <span class="glyphicon glyphicon-remove" title="Delete"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:pagetemplate>