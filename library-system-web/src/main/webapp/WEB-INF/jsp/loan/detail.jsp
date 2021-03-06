<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<my:pagetemplate title="Loan detail">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-sm-12">
                <table class="table table-detail">
                    <tbody>
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="user.name"/></th>
                        <td><c:out value="${loan.user.firstName}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="user.surname"/></th>
                        <td><c:out value="${loan.user.lastName}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="loan.book"/></th>
                        <td><c:out value="${loan.bookCopy.book.title}"/></td>
                    </tr>
                    <tr>
                        <th scope="row" class="col-sm-4"><fmt:message key="loan.date_loan"/></th>
                        <td><javatime:format value="${loan.loanDate}" pattern="dd.MM.yyyy HH:mm"/></td>
                    </tr>
                    <c:if test="${loan.returnDate != null}">
                        <tr>
                            <th scope="row" class="col-sm-4"><fmt:message key="loan.date_return"/></th>
                            <td><javatime:format value="${loan.returnDate}" pattern="dd.MM.yyyy HH:mm"/></td>
                        </tr>
                    </c:if>
                    <c:if test="${loan.returnDate != null}">
                        <tr>
                            <th scope="row" class="col-sm-4"><fmt:message key="loan.book_state_return"/></th>
                            <td><c:out value="${loan.returnBookState}"/></td>
                        </tr>
                    </c:if>
                    <tr>
                        <td></td>
                        <td class="detail-buttons">
                            <button class="btn btn-default" onclick="history.go(-1)"><fmt:message key="back"/></button>
                        </td>
                    </tr>
                    </tbody>

                </table>
            </div>
        </div>

    </jsp:attribute>
</my:pagetemplate>
