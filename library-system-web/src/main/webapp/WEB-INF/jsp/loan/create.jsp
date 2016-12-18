<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<c:set var="actionUrl" value="${pageContext.request.contextPath}/loan/create"/>
<c:set var="backUrl" value="${pageContext.request.contextPath}/loan/index"/>

<my:pagetemplate title="New Loan">
    <jsp:attribute name="scripts">
        <script>
            $(function () {
                $(".datepicker").datetimepicker({ format: 'DD.MM.YYYY HH:mm'});
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="body">
        <h3>New Loan</h3>
        <br>
        <form:form method="post" action="${actionUrl}"
                   modelAttribute="loan">
            <div class="form-group">
                <form:label path="bookIds" cssClass="control-label">Choose books:</form:label>
                <div style="width: 50%">
                    <form:select path="bookIds" cssClass="form-control" multiple="true" size="${books.size() > 15 ? 15 : books.size()}">
                        <c:forEach items="${books}" var="book">
                            <form:option value="${book.id}">${book.title}</form:option>
                        </c:forEach>
                    </form:select>
                    <p class="help-block"><form:errors path="bookIds" cssClass="error"/></p>
                </div>
            </div>
             <div class="col-sm-5">
                 <button class="btn btn-primary" type="submit">Save</button>
                 <a href="${backUrl}" class="btn btn-default">Back</a>
             </div>
            <form:hidden path="userId"/>
            <form:hidden path="loanDate"/>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>