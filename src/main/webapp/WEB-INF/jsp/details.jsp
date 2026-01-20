<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DEV01
  Date: 19/01/2026
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Détails du biens</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/asset/css/navBar.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/asset/css/footer.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/asset/css/details.css" rel="stylesheet" />
</head>
<body>
    <c:import url="../../public/navBar.jsp" />

    <h1 class="text-center mb-5">${estate.nameEstate}</h1>

    <div class="col-8 offset-2">
        <img class="col-10 offset-1 mt-3" id="details-img" src="${estate.photoEstate}/photo.jpg">
        <div class="offset-2">
            <p>
            <h2><label>Description: </label></h2>
            ${estate.description}
            </p>
            <p>
            <h2><label>Votre Hôte: </label></h2>
            ${user.firstname} ${user.lastname}
            </p>
            <c:if test="${estate.isValid()}">

            <form method="post"  action="<c:url value="/detailsServlet" />">
                <div class="row">
                    <input type="hidden" value="${estate.idEstate}" name="id-estate">
                    <div class="col-3">
                        <h2 ><label>Début du séjour</label></h2>
                        <input type="date" name="start-rent" />
                    </div>
                    <div class="col-5">
                        <h2><label>Fin du séjour</label></h2>
                        <input type="date" name="end-rent" />
                    </div>
                    <div class="col-3 mt-4">
                        <button class="btn btn-primary" type="submit">Réserver</button>
                    </div>
                </div>
            </form>
        </div>

    </div>

        </c:if>


    </div>


    <c:import url="../../public/footer.jsp" />
    <script src="${pageContext.request.contextPath}/asset/js/App.js"></script>
    <%-- Link bootstrap --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous">
    </script>
</body>
</html>
