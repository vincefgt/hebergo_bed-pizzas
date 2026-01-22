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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <h1 class="display-2 text-center mb-5">${estate.nameEstate}</h1>
            <div class="col-10 offset-1">
                <img class="col-12 mt-3 col-xl-8 offset-xl-2" id="details-img" src="${estate.photoEstate}/photo.jpg">
                <div class="container mb-5 mt-5">
                    <c:if test="${estate.isValid()}">
                        <form method="post"  action="<c:url value="/detailsServlet" />">
                            <input type="hidden" name="id-estate" value="${estate.idEstate}">
                            <input type="hidden" name="id-user" value="${sessionScope.user.idUser}">
                            <input type="hidden" id="total-price" name="total-price">
                            <div class="row">
                                <div>
                                    <p class="fs-5">Description:</p>
                                        <p><span class="">${estate.description}</span></p>
                                    <p class="fs-5">Votre Hôte :
                                        <span class="">${user.firstname} ${user.lastname}</span>
                                    </p>
                                    <p class="fs-5">Prix par jour :
                                        <span class="">${estate.dailyPrice} €</span>
                                    </p>
                                </div>

                                <!-- remonter input et bouton puis finir par récap et prix total ... -->

                                <div class="col-10">
                                    <p class="fs-6">Sélectionnez vos dates: </p>
                                    <input type="text" id="date-range" name="dates"  placeholder="Quand souhaitez-vous partir ?" readonly>


                                    <input type="hidden" id="start-date" name="start-rent">
                                    <input type="hidden" id="end-date" name="end-rent">
                                </div>

                                <div class="col-12">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.user}">
                                            <button type="submit" class="btn btn-dark mt-4 col-xl-3 offset-xl-5" id="book">Réserver</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="btn btn-secondary mt-4" id="nobook" disabled>Pour réserver connectez-vous</button>
                                        </c:otherwise>

                                    </c:choose>
                                </div>

                                <div class="col-6 mt-3">
                                    <p class="fs-4">Nombre de jour :
                                        <span class="fs-5" class="" id="range-date"></span>
                                    </p>
                                </div>

                                <div class="col-6 mt-3">
                                    <p class="fs-4">Prix total:
                                        <span class="fs-5" id="total-price-display"></span>
                                    </p>
                                </div>

                            </div>

                        </form>
                    </c:if>
                    <c:if test="${!available}">
                        <div class="alert alert-danger mt-5" role="alert">
                            Cette date est VRAIMENT indisponible!
                        </div>
                    </c:if>

                    <c:if test="${successRents != null}">
                        <div class="alert alert-success mt-5" role="alert">
                            ${successRents}
                        </div>
                    </c:if>
                </div>
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
                    const dailyPrice = <c:out value="${estate.dailyPrice}" />;
                    const totalPrice = dailyPrice * rangeDay;
                    document.getElementById("total-price").value = totalPrice;
                    document.getElementById("total-price-display").textContent = totalPrice.toString() + " €";
                }
            }
        });
    </script>
</body>
</html>
