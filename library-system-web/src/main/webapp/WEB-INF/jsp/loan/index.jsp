<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<my:pagetemplate title="Loans">
<jsp:attribute name="body">
    <c:if test="${!loggedUser.isAdmin()}">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/loan/create">
        <span class="glyphicon glyphicon-plus"></span> <fmt:message key="loan.new"/>
    </a>
    </c:if>
 <div id="loans">
     <ul>
         <li><a href="#loaned"><fmt:message key="loan.loaned"/></a></li>
         <li><a href="#returned"><fmt:message key="loan.returned"/></a></li>
     </ul>
     <div id="loaned">
         <my:loanTable loans="${loaned}" user="${loggedUser}" showReturn="false"/>
     </div>
     <div id="returned">
         <my:loanTable loans="${returned}" user="${loggedUser}" showReturn="true"/>
     </div>
 </div>

</jsp:attribute>
    <jsp:attribute name="scripts">
    <script>
        $("#loans").tabs();
    </script>
</jsp:attribute>
</my:pagetemplate>
