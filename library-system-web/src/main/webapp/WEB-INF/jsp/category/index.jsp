<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:pagetemplate title="Categories">
<jsp:attribute name="body">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/category/create">
        <span class="glyphicon glyphicon-plus"></span> New Category</a>
    </a>
    <table class="table table-striped table-hover table-books">
        <thead>
        <tr>
            <th>Name</th>
            <th>Number of books in category</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td><c:out value="${category.name}"/></td>
                <td><c:out value="${category.books.size()}"/></td>
                <td>
                    <a class="btn btn-default" title="Detail" href="${pageContext.request.contextPath}/category/detail/${category.id}">
                        <span class="glyphicon glyphicon-search"></span>
                    </a>
                    <form method="post" action="${pageContext.request.contextPath}/category/delete/${category.id}">
                        <button type="submit" class="btn btn-danger" title="Delete">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>
