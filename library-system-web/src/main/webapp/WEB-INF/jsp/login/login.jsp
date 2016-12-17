<%-- 
    Author     : Bedrich Said
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Login to the library">
    <jsp:attribute name="body">
        <h3>Log in to the library</h3>
        <c:if test="${param.error != null}">
            <div>Invalid credentials.</div>
        </c:if>
        <form:form action="${pageContext.request.contextPath}/login/validate" method="POST">
            <div class="form-group">
                <label for="username">E-mail:</label>
                <input name="username" type="text" />
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input name="password" type="password" />
            </div>
            <button type="submit">Login</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>