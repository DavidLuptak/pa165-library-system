<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New book">
    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/book/create"
                   modelAttribute="book" cssClass="form-horizontal">
            <div class="form-group">
                <form:label path="isbn" cssClass="col-sm-1 control-label">Isbn</form:label>
                <div class="col-sm-4">
                    <form:input path="isbn" cssClass="form-control"/>
                    <form:errors path="isbn" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="author" cssClass="col-sm-1 control-label">Author</form:label>
                <div class="col-sm-4">
                    <form:input path="author" cssClass="form-control"/>
                    <form:errors path="author" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="title" cssClass="col-sm-1 control-label">Title</form:label>
                <div class="col-sm-4">
                    <form:input path="title" cssClass="form-control"/>
                    <form:errors path="title" cssClass="help-block"/>
                </div>
            </div>
            <div class="col-lg-offset-1">
                <a href="${pageContext.request.contextPath}/book/index" class="btn btn-default">Back</a>
                <button class="btn btn-primary" type="submit">Save</button>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>