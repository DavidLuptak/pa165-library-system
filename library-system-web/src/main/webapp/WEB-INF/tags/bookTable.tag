<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<%@ attribute name="books" required="true" type="java.util.List"%>
<table class="table table-striped table-hover table-books">
    <thead>
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>ISBN</th>
        <th></th>
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
                <c:if test="${loggedUser.isAdmin()}">
                    <a class="btn btn-danger" title="Delete" href="${pageContext.request.contextPath}/book/delete/${book.id}">
                        <span class="glyphicon glyphicon-remove"></span>
                    </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>