<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="${category.name} [Category]">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-sm-6">
                <table class="table table-detail">
                    <tbody>
                    <tr>
                        <th scope="row" class="col-sm-2">Name</th>
                        <td><c:out value="${category.name}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2">Number of books</th>
                        <td><c:out value="${category.books.size()}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2">Books</th>
                        <c:forEach var="book" items="${category.books}">
                            <td><c:out value="${book.title}"/></td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <a class="btn btn-warning"
                               href="${pageContext.request.contextPath}/category/edit/${category.id}">Edit</a>
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/category/index">Back</a>
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

    </jsp:attribute>
</my:pagetemplate>
