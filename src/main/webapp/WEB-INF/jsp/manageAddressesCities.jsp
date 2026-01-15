<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DEV01
  Date: 15/01/2026
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestio adresses et villes</title>
</head>
<body>
  <a href="<c:url value="/index.jsp" />">Accueil</a>
  <h1>Gestio adresses et villes</h1>
  <p>findAll : liste des adresses et des villes</p>
  <div id="findAll" style="border:1px solid black;margin-top:2vh">
    <ul>
      <c:forEach var="address" items="${addressesList}">
        <li>
          id address: ${address.idAddress} /
          number street: ${address.numberStreet} /
          id city: ${address.idCity}
      </li>
      </c:forEach>
    </ul>
    <ul>
      <c:forEach var="city" items="${citiesList}">
        <li>
          id city: ${city.idCity} /
          label: ${city.labelCity} /
          zip code: ${city.zipCode}
        </li>
      </c:forEach>
    </ul>
  </div>

  <div id="create" style="border:1px solid black;margin-top:2vh">
    <form method="post" action="<c:url value="/AddressesCitiesServlet" />">
      <input name="action" type="hidden" value="create">
      <input name="number-street" type="text" placeholder="numero et rue">
      <input name="id-city" type="number" placeholder="id city">
      <input name="label-city" type="text" placeholder="label ville">
      <input name="zip-code" type="text" placeholder="code postal">

      <button type="submit">Enregistrer</button>
    </form>
  </div>

</body>
</html>
