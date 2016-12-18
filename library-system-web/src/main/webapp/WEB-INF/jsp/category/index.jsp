<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate>
    <jsp:attribute name="title"><fmt:message key="category.title"/></jsp:attribute>
    <jsp:attribute name="body">
    <c:if test="${loggedUser.isAdmin()}">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/category/create">
        <span class="glyphicon glyphicon-plus"></span> <fmt:message key="category.create"/> </a>
    </a>
    </c:if>
    <table class="table table-striped table-hover table-books">
        <thead>
        <tr>
            <th><fmt:message key="category.name"/></th>
            <th><fmt:message key="category.books_number"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td><c:out value="${category.name}"/></td>
                <td><c:out value="${category.books.size()}"/></td>
                <td class="table-buttons">
                    <a class="btn btn-default"
                       href="${pageContext.request.contextPath}/category/detail/${category.id}">
                        <span class="sr-only"><fmt:message key="detail"/></span> <!-- it's not visible on hover -->
                        <span class="glyphicon glyphicon-search"></span>
                    </a>
                    <c:if test="${loggedUser.isAdmin()}">
                    <a class="btn btn-danger" title="Delete"
                       href="${pageContext.request.contextPath}/category/delete/${category.id}">
                        <span class="glyphicon glyphicon-remove"></span>
                    </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>
