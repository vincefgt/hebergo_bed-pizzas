<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DEV01
  Date: 12/01/2026
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <a href="<c:url value="/index.jsp" />">Accueil</a>
    <title>Test rents DAO</title>
    <div id="findAll" style="border:1px solid black;margin-top:2vh">
        <p>find All: liste des locations</p>
        <ul>
            <c:forEach var="rent" items="${requestScope.rentsList}">
                    <li>
                        id Rents ${rent.idRents} /
                        id User ${rent.idUser} /
                        id Estate ${rent.idEstate} /
                        purchase date ${rent.purchaseDate} /
                        start rent date ${rent.startRent} /
                        end rent date ${rent.endRent} /
                        total price ${rent.totalPrice} /
                        payment number ${rent.paymentNumber}

                    </li>

            </c:forEach>
        </ul>
    </div>

    <div id="create" style="border:1px solid black;margin-top:2vh">
        <p>Enregistrer une nouvelle réservation (location): </p>
        <form method="post" action="<c:url value="/rentsServlet" />">
            <input name="action" type="hidden" value="create">
            <input name="id-user" type="number" placeholder="id user">
            <input name="id-estate" type="number" placeholder="id estate">
            <input name="purchase-date" type="date" placeholder="purchase date">
            <input name="start-rent" type="date" placeholder="start rent date">
            <input name="end-rent" type="date" placeholder="end rent date">
            <input name="total-price" type="text" placeholder="total price">
            <input name="payment-number" type="text" placeholder="payment number">

            <button type="submit">Enregistrer</button>
        </form>
    </div>

    <div id="modify" style="border:1px solid black;margin-top:2vh">
        <p>Modifier réservation (location): </p>
        <p>Rechercher la réservation par son identifiant: </p>
        <form method="post" action="<c:url value="/rentsServlet" />">
            <input name="action" type="hidden" value="findById">
            <input name="id-rents-to-find" type="number" placeholder="id rents">

            <button type="submit">Chercher</button>
        </form>
        <form method="post" action="<c:url value="/rentsServlet" />">
            <input name="action" type="hidden" value="modify">
            <input name="id-rents-m" type="number" placeholder="id rents" value="${rentsToFind.idRents}">
            <input name="id-user-m" type="number" placeholder="id user" value="${rentsToFind.idUser}">
            <input name="id-estate-m" type="number" placeholder="id estate" value="${rentsToFind.idEstate}">
            <input name="purchase-date-m" type="date" placeholder="purchase date" value="${rentsToFind.purchaseDate}">
            <input name="start-rent-m" type="date" placeholder="start rent date" value="${rentsToFind.startRent}">
            <input name="end-rent-m" type="date" placeholder="end rent date" value="${rentsToFind.endRent}">
            <input name="total-price-m" type="text" placeholder="total price" value="${rentsToFind.totalPrice}">
            <input name="payment-number-m" type="text" placeholder="payment number" value="${rentsToFind.paymentNumber}">

            <button type="submit">Modifier</button>
        </form>
    </div>

</head>
<body>

</body>
</html>
