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
                        <td></td>
                        <td>
                            <a class="btn btn-warning" href="${pageContext.request.contextPath}/book/edit/${book.id}">Edit</a>
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/book/index">Back</a>
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