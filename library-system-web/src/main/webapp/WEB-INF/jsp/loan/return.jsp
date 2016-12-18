<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<c:set var="actionUrl" value="${pageContext.request.contextPath}/loan/return"/>
<c:set var="backUrl" value="${pageContext.request.contextPath}/loan/index"/>

<my:pagetemplate title="Return Loan">
    <jsp:attribute name="scripts">
        <script>
            $(function () {
                $(".datepicker").datetimepicker({ format: 'DD.MM.YYYY HH:mm'});
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="body">
        <h3>Return book</h3>
        <form:form method="post" action="${actionUrl}"
                   modelAttribute="loan" cssClass="form-horizontal form-loan">
            <div class="form-group">
                <form:label path="user.firstName" cssClass="col-sm-2 control-label">First Name</form:label>
                <div class="col-sm-5">
                    <form:input readonly="true" path="user.firstName" cssClass="form-control"/>
                    <form:errors path="user.firstName" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="user.lastName" cssClass="col-sm-2 control-label">Last Name</form:label>
                <div class="col-sm-5">
                    <form:input readonly="true" path="user.lastName" cssClass="form-control"/>
                    <form:errors path="user.lastName" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="bookCopy.book.title" cssClass="col-sm-2 control-label">Book</form:label>
                <div class="col-sm-5">
                    <form:input readonly="true" path="bookCopy.book.title" cssClass="form-control"/>
                    <form:errors path="bookCopy.book.title" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="loanDate" cssClass="col-sm-2 control-label">Loan Date</form:label>
                <div class="col-sm-5">
                    <form:input readonly="true" path="loanDate" cssClass="form-control"/>
                    <form:errors path="loanDate" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="loanDate" cssClass="col-sm-2 control-label">Loan Book State</form:label>
                <div class="col-sm-5">
                    <form:input readonly="true" path="bookCopy.bookState" cssClass="form-control"/>
                    <form:errors path="bookCopy.bookState" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="returnDate" cssClass="col-sm-2 control-label">Return Date</form:label>
                <div class="col-sm-5">
                    <form:input readonly="true" path="returnDate" cssClass="form-control datepicker"/>
                    <form:errors path="returnDate" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="returnBookState" cssClass="col-sm-2 control-label">Return Book State</form:label>
                <div class="col-sm-5">
                    <form:select path="returnBookState" cssClass="form-control" >
                        <c:forEach items="${bookStates}" var="bookState">
                            <form:option value="${bookState}">${bookState.toString()}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="returnBookState" cssClass="help-block"/>
                </div>
            </div>
            <form:hidden path="user.id"/>
            <form:hidden path="bookCopy.id"/>
            <form:hidden path="bookCopy.book.id"/>
            <form:hidden path="id"/>
            <div class="col-sm-offset-2 col-sm-5">
                <button class="btn btn-primary" type="submit">Return</button>
                <a href="${backUrl}" class="btn btn-default">Back</a>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>