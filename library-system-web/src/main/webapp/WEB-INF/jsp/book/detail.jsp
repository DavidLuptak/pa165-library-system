<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Book detail">
    <jsp:attribute name="body">
        <a class="btn btn-default" href="${pageContext.request.contextPath}/book/edit/${book.id}">Edit</a>
        <a class="btn btn-default" href="${pageContext.request.contextPath}/book/index">Back to list</a>
        <table class="table">
            <tr>
                <td class="col-md-2"><b>Id</b></td>
                <td>${book.id}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Author</b></td>
                <td><c:out value="${book.author}"/></td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Title</b></td>
                <td><c:out value="${book.title}"/></td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Isbn</b></td>
                <td><c:out value="${book.isbn}"/></td>
            </tr>
            <tr>
                <th scope="row">Copies</th>
                <td><c:out value="${book.bookCopies.size()}"/></td>
            </tr>
        </table>
    </jsp:attribute>
</my:pagetemplate>