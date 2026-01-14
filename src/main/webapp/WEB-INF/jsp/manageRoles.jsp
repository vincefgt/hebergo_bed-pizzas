<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DEV01
  Date: 14/01/2026
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <a href="<c:url value="/index.jsp" />">Accueil</a>
    <title>Gestion roles DAO</title>

    <div id="findAll" style="border:1px solid black;margin-top:2vh">
        <p>find All: liste des roles</p>
        <ul>
            <c:forEach var="role" items="${requestScope.rolesList}">
                <li>id role: ${role.idRole} / label role: ${role.labelRole}</li>
            </c:forEach>

        </ul>
    </div>
</head>
<body>

</body>
</html>
