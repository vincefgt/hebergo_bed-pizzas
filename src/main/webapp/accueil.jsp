<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
  <link href="${pageContext.request.contextPath}/asset/css/navBar.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/asset/css/carousel.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/asset/css/asideMeteoApi.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/asset/css/footer.css" rel="stylesheet" />
  <script
          src="https://code.jquery.com/jquery-3.7.1.js"
          integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
          crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
        integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY="
        crossorigin=""/>
  <title>Bed&Pizzas</title>
</head>
<body>
<%-- L'ajout de la navBar --%>
<c:import url="public/navBar.jsp" />

<main>

  <%-- Le aside de ma meteo API et carte de FRANCE --%>
  <c:import url="public/asideMeteoApi.jsp" />

  <section id="group-card">
    <%-- Le body de ma Home page --%>
    <jsp:include page="public/homePage.jsp" />
  </section>

</main>

<%-- L'ajout de mon footer --%>
<c:import url="public/footer.jsp" />


<%-- Link bootstrap --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous">
</script>
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
        integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo="
        crossorigin=""></script>
<%-- Link Javascript --%>
<script src="${pageContext.request.contextPath}/asset/js/map.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/weather.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/App.js"></script>

=======
integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous">
</script>
<title>JSP - Hello World</title>

<body>
<c:if test="${not empty success}">
  <script>
    alert("${success}");
  </script>
</c:if>

<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<a href="user-servlet">User</a>
<a href="<c:url value="/rentsServlet" />">rents</a>
<a href="<c:url value="/rolesServlet" />">roles</a>
<a href="<c:url value="/AddressesCitiesServlet" />">address</a>

<script type="module" src="asset/js/UserJS.js"></script>
>>>>>>> dao
</body>
</html>
