<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Book detail">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-sm-6">
                <table class="table table-detail">
                    <tbody>
                    <tr>
                        <th scope="row" class="col-sm-2">Title</th>
                        <td><c:out value="${book.title}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2">Author</th>
                        <td><c:out value="${book.author}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2">Isbn</th>
                        <td><c:out value="${book.isbn}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2">Copies</th>
                        <td><c:out value="${book.bookCopies.size()}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2">Categories</th>
                        <td>
                            <ul class="detail-list">
                                <c:forEach var="categoryName" items="${book.categoryNames}">
                                    <li><c:out value="${categoryName}"/></li>
                                </c:forEach>
                            </ul>
                        </td>

                    </tr>
                    <tr>
                        <td></td>
                        <td class="detail-buttons">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/book/index">Back</a>
                            <c:if test="${loggedUser.isAdmin()}">
                                <a class="btn btn-warning" href="${pageContext.request.contextPath}/book/edit/${book.id}">Edit</a>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>

                </table>
            </div>

        </div>

        <div class="row">
            <div class="col-sm-6 col-sm-offset-1">

            </div>
        </div>
                        
        <h3>List of copies</h3>
        <table class="table table-striped table-hover table-books">
            <thead>
            <tr>
                <th>ID</th>
                <th>Book State</th>
                <th>Available</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="copy" items="${copies}">
                <tr>
                    <td><c:out value="${copy.id}"/></td>
                    <td><c:out value="${copy.bookState}"/></td>
                    <td>
                        <c:if test="${empty copy.loans}">Yes</c:if>
                        <c:if test="${not empty copy.loans}">No</c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
