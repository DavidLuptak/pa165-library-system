<%-- 
    Author     : Bedrich Said
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="Login to the library">
    <jsp:attribute name="body">
        <h3>Login to the library</h3>
        <div>
            <form:form method="POST" action="j_spring_security_check">

                <c:if test="${not empty param.error}">
                    <div class="alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        Invalid credentials.
                    </div>
                </c:if>

                <label for="inputEmail" class="sr-only">Email address</label>
                <input type="email" name="user" id="inputEmail" class="form-control" placeholder="Email address" required="true" autofocus="true">
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" name="pass" id="inputPassword" class="form-control" placeholder="Password" required="true">

                <button class="btn btn-primary" type="submit">Login</button>
                <a href="${backUrl}" class="btn btn-default">Back</a>
            </form:form>
        </div>
    </jsp:attribute>
</my:pagetemplate>