<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="${category.name}">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-sm-6">
                <table class="table table-detail">
                    <tbody>
                    <tr>
                        <th scope="row" class="col-sm-2"><fmt:message key="category.name"/></th>
                        <td><c:out value="${category.name}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2"><fmt:message key="category.books_number"/></th>
                        <td><c:out value="${category.books.size()}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-2"><fmt:message key="category.books"/></th>
                        <td>
                            <ul class="detail-list">
                                <c:forEach var="book" items="${category.books}">
                                    <li><c:out value="${book.title}"/></li>
                                </c:forEach>
                            </ul>
                        </td>

                    </tr>
                    <tr>
                        <td></td>
                        <td class="detail-buttons">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/category/index">
                                <fmt:message key="back"/>
                            </a>
                            <c:if test="${loggedUser.isAdmin()}">
                                <a class="btn btn-warning"
                                   href="${pageContext.request.contextPath}/category/edit/${category.id}">
                                    <fmt:message key="edit"/>
                                </a>
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

    </jsp:attribute>
</my:pagetemplate>
