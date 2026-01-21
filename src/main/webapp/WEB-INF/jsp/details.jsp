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
</head>
<body>
    <c:import url="../../public/navBar.jsp" />

    <h1 class="offset-1">${estate.nameEstate}</h1>
    <div class="col-6 offset-1">
        <img src="${pageContext.request.contextPath}/${estate.photoEstate}" class="col-10">
        <p>${estate.description}</p>
        <p>${user.lastname}</p>
        <form method="post"  action="<c:url value="/detailsServlet" />">
            <input type="date" name="start-rent" />
            <input type="date" name="end-rent" />
        </form>

    </div>


    <c:import url="../../public/footer.jsp" />
    <script src="${pageContext.request.contextPath}/asset/js/App.js"></script>
    <%-- Link bootstrap --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous">
    </script>
</body>
</html>
