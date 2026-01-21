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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

</head>
<body>
    <!-- header -->
    <c:import url="../../public/navBar.jsp" />

    <main>
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
                        <input type="hidden" name="id-estate" value="${estate.idEstate}">
                        <input type="hidden" name="id-user" value="${sessionScope.user.idUser}">

                        <div class="row">
                            <div class="col-6">
                                <h2><label for="date-range">Sélectionnez vos dates :</label></h2>
                                <input type="text" id="date-range" name="dates"  placeholder="Quand souhaitez-vous partir ?" readonly>

                                <input type="hidden" id="start-date" name="start-rent">
                                <input type="hidden" id="end-date" name="end-rent">
                            </div>

                            <div class="col-6">
                                <span id="range-date"></span>
                                <button type="submit" class="btn btn-dark mt-4" id="book">Réserver</button>
                            </div>

                        </div>

                    </form>

                </c:if>
                <c:if test="${!available}">
                    <div class="alert alert-danger" role="alert">
                        Cette date est VRAIMENT indisponible c....ard !
                    </div>
                </c:if>
            </div>
        </div>
    </main>

    <!-- footer -->
    <c:import url="../../public/footer.jsp" />

    <!-- script burger -->
    <script src="${pageContext.request.contextPath}/asset/js/App.js"></script>

    <%-- Link bootstrap --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous">
    </script>

    <!-- flatpickr gestion calendrier -->
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script>
        // 1. Récupération des dates bloquées depuis Java (via JSTL)
        const bookedDates = [
            <c:forEach items="${rentsList}" var="r" varStatus="status">
            {
                from: "${r.startRent.toString()}", // LocalDate.toString() -> "2026-02-15"
                to: "${r.endRent.toString()}"
            }${!status.last ? ',' : ''}
            </c:forEach>
        ];


        // 2. Configuration de Flatpickr
        flatpickr("#date-range", {
            mode: "range",       // Sélection de plage de dates
            minDate: "today",    // Impossible de réserver dans le passé
            dateFormat: "Y-m-d", // Format de stockage
            altInput: true,      // Affiche un format plus joli à l'utilisateur
            altFormat: "d F Y",  // Ex: 25 Janvier 2026
            disable: bookedDates, // Verrouille tes périodes JDBC

            onChange: function(selectedDates, dateStr, instance) {
                // Si on a bien sélectionné une plage (début et fin)
                if (selectedDates.length === 2) {
                    // On remplit nos champs cachés pour la Servlet
                    document.getElementById('start-date').value = instance.formatDate(selectedDates[0], "Y-m-d");
                    document.getElementById('end-date').value = instance.formatDate(selectedDates[1], "Y-m-d");
                    let startDate = new Date(instance.formatDate(selectedDates[0], "Y-m-d"));
                    let endDate = new Date(instance.formatDate(selectedDates[1], "Y-m-d"));
                    let rangeDateMs = endDate - startDate;
                    console.log(rangeDateMs);
                    const msParJour = 1000 * 60 * 60 * 24;
                    const rangeDay = (rangeDateMs / msParJour) + 1;
                    console.log(rangeDay);
                    const displayRangeDate = document.getElementById("range-date");
                    displayRangeDate.textContent = rangeDay.toString();
                }
            }
        });
    </script>
</body>
</html>
