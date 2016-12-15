<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">

    <div class="jumbotron">
        <h1>Welcome to the Library System!</h1>
        <p class="lead">The right place to borrow wonderful books.</p>
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/book/index"
              role="button">Go to books</a></p>
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/loan/index"
              role="button">Go to loans</a></p>
    </div>

</jsp:attribute>
</my:pagetemplate>
