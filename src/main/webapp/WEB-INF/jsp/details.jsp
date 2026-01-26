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
    <link href="${pageContext.request.contextPath}/asset/css/footer.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

</head>
<body>
    <!-- header -->
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

                                <!-- Champ de paiement Stripe (caché par défaut) -->
                                <div class="col-10 mt-3" id="payment-section" style="display:none;">
                                    <p class="fs-6">Informations de paiement :</p>
                                    <div id="card-element" style="padding: 12px; border: 1px solid #ced4da; border-radius: 4px; background-color: white;">
                                        <!-- Stripe injectera le champ de carte ici -->
                                    </div>
                                    <div id="card-errors" role="alert" class="text-danger mt-2" style="font-size: 0.875rem;"></div>
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

    <%-- script bootstrap --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous">
    </script>

    <!-- script stripe -->
    <script src="https://js.stripe.com/v3/"></script>

    <!-- script flatpickr gestion calendrier -->
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

        let totalPrice;
        let startDate, endDate;

        // 2. Initialisation Stripe
        const stripe = Stripe('${applicationScope.stripePublicKey}');
        const elements = stripe.elements();
        const cardElement = elements.create('card', {
            style: {
                base: {
                    fontSize: '16px',
                    color: '#32325d',
                    fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", sans-serif',
                    '::placeholder': { color: '#aab7c4' }
                },
                invalid: { color: '#fa755a', iconColor: '#fa755a' }
            }
        });

        let cardMounted = false;

        // 3. Configuration de Flatpickr
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
                    startDate = instance.formatDate(selectedDates[0], "Y-m-d");
                    endDate = instance.formatDate(selectedDates[1], "Y-m-d");

                    document.getElementById('start-date').value = startDate;
                    document.getElementById('end-date').value = endDate;

                    let startDateObj = new Date(startDate);
                    let endDateObj = new Date(endDate);
                    let rangeDateMs = endDateObj - startDateObj;
                    const msParJour = 1000 * 60 * 60 * 24;
                    const rangeDay = (rangeDateMs / msParJour) + 1;
                    const displayRangeDate = document.getElementById("range-date");
                    displayRangeDate.textContent = rangeDay.toString();
                    const dailyPrice = <c:out value="${estate.dailyPrice}" />;
                    totalPrice = dailyPrice * rangeDay;
                    document.getElementById("total-price").value = totalPrice;
                    document.getElementById("total-price-display").textContent = totalPrice.toString() + " €";

                    // Afficher le champ de carte
                    const paymentSection = document.getElementById('payment-section');
                    paymentSection.style.display = 'block';

                    // Monter le Card Element (une seule fois)
                    if (!cardMounted) {
                        cardElement.mount('#card-element');
                        cardMounted = true;

                        // Gérer les erreurs de validation en temps réel
                        cardElement.on('change', function(event) {
                            const displayError = document.getElementById('card-errors');
                            if (event.error) {
                                displayError.textContent = event.error.message;
                            } else {
                                displayError.textContent = '';
                            }
                        });
                    }
                }
            }
        });

        // 4. Fonction de paiement complète
        async function processPayment() {
            // Vérifier que totalPrice est défini
            if (typeof totalPrice === 'undefined' || totalPrice === null || isNaN(totalPrice)) {
                alert('Veuillez sélectionner des dates de réservation avant de continuer.');
                return;
            }

            const bookBtn = document.getElementById('book');
            const originalBtnText = bookBtn.textContent;
            bookBtn.disabled = true;
            bookBtn.textContent = 'Traitement en cours...';

            try {
                // ÉTAPE 1 : Créer le PaymentIntent
                console.log('Étape 1 : Création PaymentIntent...');
                const amountInCents = Math.round(totalPrice * 100);

                const formData1 = new URLSearchParams();
                formData1.append("action", "create-payment-intent");
                formData1.append("amount", String(amountInCents));

                const res1 = await fetch('<c:url value="/detailsServlet" />', {
                    method: "POST",
                    body: formData1,
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    credentials: 'same-origin'
                });

                const text1 = await res1.text();
                console.log('Réponse création PaymentIntent:', text1);

                let data1;
                try {
                    data1 = JSON.parse(text1);
                } catch(e) {
                    console.error("Réponse non-JSON reçue:", text1);
                    alert('Erreur serveur: réponse invalide');
                    return;
                }

                if (data1.error) {
                    console.error('Erreur:', data1.error);
                    alert('Erreur: ' + data1.error);
                    return;
                }

                const clientSecret = data1.clientSecret;
                console.log('Client Secret reçu:', clientSecret);

                // ÉTAPE 2 : Confirmer le paiement avec Stripe
                console.log('Étape 2 : Confirmation paiement Stripe...');
                bookBtn.textContent = 'Paiement en cours...';

                const result = await stripe.confirmCardPayment(clientSecret, {
                    payment_method: {
                        card: cardElement,
                        billing_details: {
                            name: '<c:out value="${sessionScope.user.firstname} ${sessionScope.user.lastname}" />'
                        }
                    }
                });

                if (result.error) {
                    // Erreur de paiement
                    const errorElement = document.getElementById('card-errors');
                    errorElement.textContent = result.error.message;
                    alert('Erreur de paiement: ' + result.error.message);
                    console.error('Erreur paiement:', result.error);
                    return;
                }

                if (result.paymentIntent && result.paymentIntent.status === 'succeeded') {
                    console.log('Paiement réussi! PaymentIntent ID:', result.paymentIntent.id);

                    // ÉTAPE 3 : Enregistrer la réservation en BDD
                    console.log('Étape 3 : Enregistrement en BDD...');
                    bookBtn.textContent = 'Enregistrement...';

                    const formData2 = new URLSearchParams();
                    formData2.append("action", "save-booking");
                    formData2.append("id-estate", document.querySelector('input[name="id-estate"]').value);
                    formData2.append("id-user", document.querySelector('input[name="id-user"]').value);
                    formData2.append("start-rent", startDate);
                    formData2.append("end-rent", endDate);
                    formData2.append("total-price", totalPrice);
                    formData2.append("payment-intent-id", result.paymentIntent.id);

                    const res2 = await fetch('<c:url value="/detailsServlet" />', {
                        method: "POST",
                        body: formData2,
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'X-Requested-With': 'XMLHttpRequest'
                        },
                        credentials: 'same-origin'
                    });

                    const text2 = await res2.text();
                    console.log('Réponse enregistrement:', text2);

                    let data2;
                    try {
                        data2 = JSON.parse(text2);
                    } catch(e) {
                        console.error("Réponse non-JSON reçue:", text2);
                        alert('Erreur: la réservation a peut-être été enregistrée mais la réponse est invalide');
                        return;
                    }

                    if (data2.success) {
                        console.log('Réservation enregistrée avec succès!');
                        alert('Paiement réussi! Votre réservation a été enregistrée.');
                        // Rediriger vers la page de détails avec message de succès
                        window.location.href = 'detailsServlet?idEstate=' + data2.idEstate + '&successRents=Votre+réservation+a+été+confirmée';
                    } else if (data2.error) {
                        console.error('Erreur enregistrement:', data2.error);
                        alert('Le paiement a réussi mais l\'enregistrement a échoué: ' + data2.error + '. Contactez le support.');
                    }
                }

            } catch (error) {
                console.error('Erreur:', error);
                alert('Une erreur est survenue: ' + error.message);
            } finally {
                // Réactiver le bouton
                bookBtn.disabled = false;
                bookBtn.textContent = originalBtnText;
            }
        }

        // 5. Attacher l'événement au bouton
        const bookBtn = document.getElementById('book');
        if (bookBtn) {
            bookBtn.addEventListener('click', (e) => {
                e.preventDefault();
                processPayment();
            });
        }
    </script>
</body>
</html>
