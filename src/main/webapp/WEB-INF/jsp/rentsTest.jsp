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
    <p>find All</p>
    <ul>
        <c:forEach var="rent" items="${requestScope.rentsList}">
                <li>id User ${rent.idUser} /
                    id Estate ${rent.idEstate} /
                    purchase date ${rent.purchaseDate} /
                    start rent date ${rent.startRent} /
                    end rent date ${rent.endRent} /
                    total price ${rent.totalPrice} /
                    payment number ${rent.paymentNumber}

                </li>

        </c:forEach>
    </ul>
</head>
<body>

</body>
</html>
