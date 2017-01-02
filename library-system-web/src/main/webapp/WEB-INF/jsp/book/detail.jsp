<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate>
    <jsp:attribute name="title"><fmt:message key="book.detail"/></jsp:attribute>
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-sm-l2">
                <table class="table table-detail">
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="book.book_title"/></th>
                        <td><c:out value="${book.title}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="book.author"/></th>
                        <td><c:out value="${book.author}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="book.isbn"/></th>
                        <td><c:out value="${book.isbn}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="book.copies_number"/></th>
                        <td><c:out value="${book.bookCopies.size()}"/></td>
                    </tr>
                    <c:if test="${not empty book.categoryNames}">
                         <tr>
                             <th scope="row" class="col-sm-4"><fmt:message key="category.title"/></th>
                             <td>
                                 <ul class="detail-list">
                                <c:forEach var="categoryName" items="${book.categoryNames}">
                                    <li><c:out value="${categoryName}"/></li>
                                </c:forEach>
                                 </ul>
                             </td>

                         </tr>
                    </c:if>
                    <tr>
                        <td></td>
                        <td class="detail-buttons">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/book/index"><fmt:message key="back"/></a>
                            <c:if test="${loggedUser.isAdmin()}">
                                <a class="btn btn-default"
                                   href="${pageContext.request.contextPath}/book/addCopy/${book.id}"><fmt:message key="book.add_copy"/></a>
                            </c:if>
                            <c:if test="${loggedUser.isAdmin()}">
                                <a class="btn btn-warning"
                                   href="${pageContext.request.contextPath}/book/edit/${book.id}"><fmt:message key="edit"/></a>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </div>

        </div>

        <c:if test="${not empty book.bookCopies}">
             <h3><fmt:message key="book.copies_list"/></h3>
        <table class="table table-striped table-hover table-books">
            <thead>
            <tr>
                <th><fmt:message key="book.copy_state"/></th>
                <th><fmt:message key="book.available"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="copy" items="${copies}">
                <tr>
                    <td><c:out value="${copy.key.bookState}"/></td>
                    <td><c:out value="${copy.value}"/></td>
                            <c:if test="${loggedUser.isAdmin() && copy.value == 'Yes'}">
                <td class="table-buttons">
                    <a class="btn btn-default" title="Edit"
                       href="${pageContext.request.contextPath}/book/${book.id}/editCopy/${copy.key.id}">
                        <span class="sr-only"><fmt:message key="edit"/></span> <!-- it's not visible on hover -->
                        <span class="glyphicon glyphicon-edit"></span>
                    </a>
                    <a class="btn btn-danger" title="Delete"
                                           href="${pageContext.request.contextPath}/book/${book.id}/deleteCopy/${copy.key.id}">
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </a>
                    <td>
                            </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
    </jsp:attribute>
</my:pagetemplate>
