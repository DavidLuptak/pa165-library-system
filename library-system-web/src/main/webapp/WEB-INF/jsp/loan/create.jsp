<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionUrl" value="${pageContext.request.contextPath}/loan/create"/>
<c:set var="backUrl" value="${pageContext.request.contextPath}/loan/index"/>

<my:pagetemplate title="New Loan">
    <jsp:attribute name="body">
        <h3>New Loan</h3>
        <form:form method="post" action="${actionUrl}"
                   modelAttribute="loan" cssClass="form-horizontal form-loan">
            <div class="form-group">
                <form:label path="userId" cssClass="col-sm-1 control-label">User</form:label>
                <div class="col-sm-5">
                    <form:input path="userId" cssClass="form-control"/>
                    <form:errors path="userId" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="bookCopyIds" cssClass="col-sm-1 control-label">Book</form:label>
                <div class="col-sm-5">
                    <form:select path="bookCopyIds" cssClass="form-control" multiple="true">
                        <c:forEach items="${bookCopies}" var="bookCopy">
                            <form:option value="${bookCopy.id}">${bookCopy.book.name}</form:option>
                        </c:forEach>
                    </form:select>
                    <p class="help-block"><form:errors path="bookCopyIds" cssClass="error"/></p>
                </div>
            </div>
            <div class="form-group">
                <form:label path="loanDate" cssClass="col-sm-1 control-label">LoanDate</form:label>
                <div class="col-sm-5">
                    <form:input path="loanDate" cssClass="form-control"/>
                    <form:errors path="loanDate" cssClass="help-block"/>
                </div>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>