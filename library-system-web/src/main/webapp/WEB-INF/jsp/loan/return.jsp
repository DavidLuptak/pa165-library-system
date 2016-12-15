<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionUrl" value="${pageContext.request.contextPath}/loan/return"/>
<c:set var="backUrl" value="${pageContext.request.contextPath}/loan/return/${loan.id}"/>

<my:pagetemplate title="Edit loan">
    <jsp:attribute name="body">
        <h3>Return book</h3>
        <form:form method="post" action="${actionUrl}"
                   modelAttribute="loan" cssClass="form-horizontal form-loan">
            <div class="form-group">
                <form:label path="user" cssClass="col-sm-1 control-label">User</form:label>
                <div class="col-sm-5">
                    <form:input path="user" cssClass="form-control"/>
                    <form:errors path="user" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="bookCopy" cssClass="col-sm-1 control-label">Books</form:label>
                <div class="col-sm-5">
                    <form:input path="bookCopy" cssClass="form-control"/>
                    <form:errors path="bookCopy" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="loanDate" cssClass="col-sm-1 control-label">LoanDate</form:label>
                <div class="col-sm-5">
                    <form:input path="loanDate" cssClass="form-control"/>
                    <form:errors path="loanDate" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="returnDate" cssClass="col-sm-1 control-label">ReturnDate</form:label>
                <div class="col-sm-5">
                    <form:input path="returnDate" cssClass="form-control"/>
                    <form:errors path="returnDate" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="returnBookState" cssClass="col-sm-1 control-label">BookState</form:label>
                <div class="col-sm-5">
                    <form:input path="returnBookState" cssClass="form-control"/>
                    <form:errors path="returnBookState" cssClass="help-block"/>
                </div>
            </div>
            <form:hidden path="id"/>
            <div class="col-sm-offset-1 col-sm-5">
                <button class="btn btn-primary" type="submit">Return</button>
                <a href="${backUrl}" class="btn btn-default">Back</a>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>