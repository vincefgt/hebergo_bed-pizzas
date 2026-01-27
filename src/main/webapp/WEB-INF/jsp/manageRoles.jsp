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

    <div id="create" style="border:1px solid black;margin-top:2vh">
        <p>Enregistrer un nouveau role: </p>
        <form method="post" action="<c:url value="/rolesServlet" />">
            <input name="action" type="hidden" value="create">
            <input name="label-role" type="text" placeholder="label role">

            <button type="submit">Enregistrer</button>
        </form>
    </div>

    <div id="modify" style="border:1px solid black;margin-top:2vh">
        <p>Rechercher un role par son identifiant: </p>
        <form method="post" action="<c:url value="/rolesServlet" />">
            <input name="action" type="hidden" value="findById">
            <input name="id-role-to-find" type="number" placeholder="id role">

            <button type="submit">Chercher</button>
        </form>


        <p>Modifier role: </p>

        <form method="post" action="<c:url value="/rolesServlet" />">
            <input name="action" type="hidden" value="modify">
            <input name="id-role-m" type="number" placeholder="id role" value="${roleToFind.idRole}">
            <input name="label-role-m" type="text" placeholder="label role" value="${roleToFind.labelRole}">

            <button type="submit">Modifier</button>
        </form>

        <p>Supprimer role</p>
        <form method="post" action="<c:url value="/rolesServlet" />">
            <input name="action" type="hidden" value="delete">
            <input name="id-role-d" type="number" placeholder="id role" value="${roleToFind.idRole}"readonly>
            <input name="label-role-d" type="text" placeholder="label role" value="${roleToFind.labelRole}" readonly>

            <button type="submit">Supprimer</button>
        </form>
    </div>
</head>
<body>

</body>
</html>
