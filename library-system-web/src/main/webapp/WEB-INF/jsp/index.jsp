<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <div class="jumbotron content-center">
        <c:if test="${not empty param.redirected}">
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                You have to login first.
            </div>
        </c:if>

        <h1>Welcome to the Library System!</h1>
        <p class="lead">The right place to borrow wonderful books. The library actually contains <c:out
                value="${books}"/> books.</p>

        <br/><br/>

        <c:choose>
            <c:when test="${not empty pageContext.request.remoteUser}">
                <p>
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/category/index"
                       role="button">Go to categories</a>
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/book/index"
                       role="button">Go to books</a>
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/loan/index"
                       role="button">Go to loans</a>
                </p>
            </c:when>
            <c:otherwise>
                    <h4 style="margin: auto">You have to login in order to continue to our library:</h4>
                    <br>
                    <div>
                        <%@include file="loginForm.jspf" %>
                    </div>
            </c:otherwise>
        </c:choose>
    </div>

</jsp:attribute>
</my:pagetemplate>
