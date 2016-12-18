<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Books">
<jsp:attribute name="body">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/book/create">
        <span class="glyphicon glyphicon-plus"></span> New book</a>
    <table class="table table-striped table-hover table-books">
        <thead>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="book" items="${books}">
            <tr>
                <td><c:out value="${book.title}"/></td>
                <td><c:out value="${book.author}"/></td>
                <td><c:out value="${book.isbn}"/></td>
                <td>
                    <a class="btn btn-default" title="Detail" href="${pageContext.request.contextPath}/book/detail/${book.id}">
                        <span class="glyphicon glyphicon-search"></span>
                    </a>
                    <a class="btn btn-danger" title="Delete" href="${pageContext.request.contextPath}/book/delete/${book.id}">
                        <span class="glyphicon glyphicon-remove"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>
