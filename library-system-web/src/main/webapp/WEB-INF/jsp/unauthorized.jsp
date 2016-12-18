<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:pagetemplate title="User detail">
    <jsp:attribute name="body">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Only administrator can perform this operation.
        </div>
    </jsp:attribute>
</my:pagetemplate>