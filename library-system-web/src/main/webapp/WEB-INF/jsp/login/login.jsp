<%-- 
    Author     : Bedrich Said
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<my:pagetemplate title="Log in to the library">
    <jsp:attribute name="body">
        <h3>Log in to the library</h3>
    </jsp:attribute>
    <jsp:attribute name="content">
        <c:if test="${param.error != null}">
            <div>Invalid credentials.</div>
        </c:if>
        <form method="POST">
            <div>
                <label for="username">E-mail:</label>
                <input name="username" type="text" />

                <label for="password">Password:</label>
                <input name="password" type="password" />
            </div>
            <button type="submit">Log in</button>
        </form>
    </jsp:attribute>
</my:pagetemplate>