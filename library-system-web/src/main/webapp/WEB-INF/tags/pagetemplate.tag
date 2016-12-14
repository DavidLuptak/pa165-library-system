<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${title}"/></title>
    <!-- bootstrap loaded from content delivery network -->
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" type="text/css">--%>
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"--%>
    <%--type="text/css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <jsp:invoke fragment="head"/>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/book/index">Library</a>
        </div>

        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/book/index">Books</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/category/index">Categories</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/loan/index">Loans</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a>
                </li>
            </ul>
        </div>

    </div>
</nav>

<div class="container" style="margin-top: 70px;">

    <c:if test="${not empty alert_info}">
        <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
    </c:if>
    <c:if test="${not empty alert_danger}">
        <div class="alert alert-danger" role="alert"><c:out value="${alert_danger}"/></div>
    </c:if>

    <jsp:invoke fragment="body"/>

</div>

<script>
    $(function () {
       var url = document.location.pathname;
        $('.navbar li a[href="' + url + '"]').parent().addClass("active");
    });
    console.log();
</script>
</body>
</html>