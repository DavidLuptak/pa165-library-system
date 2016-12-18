<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <div class="jumbotron">
        <center><h1>Welcome to the Library System!</h1></center>
        <center><p class="lead">The right place to borrow wonderful books. Our library actually contains <c:out value="${books}"/> books.</p></center>
        
        <br/><br/>
        
        <c:choose>
            <c:when test="${not empty pageContext.request.remoteUser}">
                <center><p>
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/category/index"
                      role="button">Go to categories</a>
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/book/index"
                       role="button">Go to books</a>
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/loan/index"
                      role="button">Go to loans</a>
                </p></center>
            </c:when>
            <c:otherwise>
                <h4>You have to login in order to continue to our library:</h4>
                <center><div><%@include file="loginForm.jspf"%></div></center>
            </c:otherwise>
        </c:choose>
    </div>

</jsp:attribute>
</my:pagetemplate>
