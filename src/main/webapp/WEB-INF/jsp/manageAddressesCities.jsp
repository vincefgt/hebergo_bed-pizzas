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
    <title>Gestion adresses et villes</title>
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
    <p>Créer une nouvelle adresse et ville: </p>
    <form method="post" action="<c:url value="/AddressesCitiesServlet" />">
      <p>nouvelle adresse</p>
      <input name="action" type="hidden" value="create">
      <input name="number-street" type="text" placeholder="numero et rue">
      <input name="id-city" type="number" placeholder="id city"><br>
      <p>nouvelle ville</p>
      <input name="label-city" type="text" placeholder="label ville">
      <input name="zip-code" type="text" placeholder="code postal">

      <button type="submit">Enregistrer</button>
    </form>
  </div>

  <div id="modify" style="border:1px solid black;margin-top:2vh">
    <p>Rechecher une adresse et une ville par id</p>
    <form method="post" action="<c:url value="/AddressesCitiesServlet" />">
      <input name="action" type="hidden" value="findById">
      <p>rechercher une adresse</p>
      <input name="id-address-toFind" type="number" placeholder="id adresse">

      <p>rechercher une ville</p>
      <input name="id-city-toFind" type="number" placeholder="id city">
      <button type="submit">Recherche</button>
    </form>

    <p>Modifier une adresse et une ville</p>
    <form method="post" action="<c:url value="/AddressesCitiesServlet" />">
      <p>Modifier adresse</p>
      <input name="action" type="hidden" value="modify">
      <input name="id-address-m" type="number" placeholder="id address" value="${addressToFind.idAddress}"readonly>
      <input name="number-street-m" type="text" placeholder="numero street" value="${addressToFind.numberStreet}">
      <input name="id-city-m" type="number" placeholder="id city" value="${addressToFind.idCity}">
      <p>Modifier ville</p>
      <input name="id-city-toModify" type="number" placeholder="id city" value="${cityToFind.idCity}"readonly>
      <input name="label-city-m" type="text" placeholder="new city" value="${cityToFind.labelCity}">
      <input name="zip-code-m" type="number" placeholder="zip code" value="${cityToFind.zipCode}">

      <button type="submit">Modifier</button>
    </form>

  </div>

</body>
</html>
