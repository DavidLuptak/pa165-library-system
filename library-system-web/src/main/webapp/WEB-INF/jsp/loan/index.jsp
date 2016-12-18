<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<my:pagetemplate title="Loans">
<jsp:attribute name="body">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/loan/create">
        <span class="glyphicon glyphicon-plus"></span> New loan
    </a>

 <div id="loans">
     <ul>
         <li><a href="#loaned">Loaned</a></li>
         <li><a href="#returned">Returned</a></li>
     </ul>
     <div id="loaned">
         <my:loanTable loans="${loaned}" user="${user}" showReturn="false"/>
     </div>
     <div id="returned">
         <my:loanTable loans="${returned}" user="${user}" showReturn="true"/>
     </div>
 </div>

</jsp:attribute>
    <jsp:attribute name="scripts">
    <script>
        $("#loans").tabs();
    </script>
</jsp:attribute>
</my:pagetemplate>

