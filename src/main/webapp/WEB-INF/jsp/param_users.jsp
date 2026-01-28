<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Compte - Hebergo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/navBar.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/asset/css/navBar.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/asset/css/asideMeteoApi.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/asset/css/footer.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/asset/css/styleList.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/asset/css/styleAdmin.css" rel="stylesheet" />
    <script src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
          integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin=""/>
</head>
<body data-context-path="${pageContext.request.contextPath}">
<c:if test="${empty sessionScope.user}">
    <c:redirect url="/index.jsp"/>
</c:if>
<c:if test="${not empty successMessage}">
    alert('${successMessage}');
</c:if>
<c:if test="${not empty errorMessage}">
    alert('${errorMessage}');
</c:if>
<c:import url="/public/navBar.jsp" />

<main>
    <div class="main-container">
        <!-- Header Profile -->
        <div class="profile-header">
            <h1>Bienvenue, ${sessionScope.user.firstname} ${sessionScope.user.lastname}</h1>
            <p>Gérez votre profil et vos biens immobiliers</p>
            <p>Rôle: host</p>
        </div>
        <!-- Tabs -->
        <div class="tabs-container">
            <button class="tab-btn active" data-tab="profile">Mon Profil</button>
            <button class="tab-btn" data-tab="estates">Mes Logements</button>
            <button class="tab-btn" data-tab="admin">Administration</button>
        </div>
        <!-- Tab Content: Profile -->
        <div id="profile-tab" class="tab-content active">
            <div class="profile-section">
                <h2>Informations personnelles</h2>
                <form action="${pageContext.request.contextPath}/user-servlet?actionUser=update" method="post">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="firstname">Prénom<span class="required">*</span></label>
                            <input type="text" id="firstname" name="firstname"
                                   value="${sessionScope.user.firstname}" required>
                        </div>
                        <div class="form-group">
                            <label for="lastname">Nom<span class="required">*</span></label>
                            <input type="text" id="lastname" name="lastname"
                                   value="${sessionScope.user.lastname}" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email<span class="required">*</span></label>
                            <input type="email" id="email" name="email"
                                   value="${sessionScope.user.email}" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Téléphone<span class="required">*</span></label>
                            <input type="tel" id="phone" name="phone"
                                   value="${sessionScope.user.phone}" required>
                        </div>
                        <div class="form-group full-width">
                            <label for="currentPassword">Mot de passe actuel</label>
                            <input type="password" id="currentPassword" name="currentPassword" required minlength="8">
                        </div>
                        <div class="form-group full-width">
                            <label for="newPassword">Nouveau mot de passe (vide = inchangé)</label>
                            <input type="password" id="newPassword" name="newPassword" placeholder="" minlength="8">
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Confirmer nouveau mot de passe</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" minlength="8">
                        </div>
                        <div class="form-group full-width">
                            <a href="${pageContext.request.contextPath}/index.jsp">
                                <button type="button" class="btn btn-secondary">Supprimer compte</button>
                            </a>
                        </div>
                    </div>
                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/index.jsp">
                            <button type="button" class="btn btn-secondary">Annuler</button>
                        </a>
                        <button type="submit" class="btn btn-primary">Enregistrer les modifications</button>
                    </div>
                </form>
            </div>
        </div>
        <!-- Tab Content: Estates -->
        <div id="estates-tab" class="tab-content">
            <div class="profile-section">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px;">
                    <h2 style="margin: 0;">Mes logements</h2>
                    <a href="${pageContext.request.contextPath}/EstateServlet?action=add" class="btn btn-primary"
                       style="text-decoration: none;">+ Ajouter un logement</a>
                </div>

                <c:choose>
                    <c:when test="${not empty estatesList}">
                        <div class="estates-grid">
                            <c:forEach var="estate" items="${estatesList}">
                                    <div class="estate-card">
                                        <a href="detailsServlet?idEstate=${estate.idEstate}" style="text-decoration: none;">
                                        <div class="estate-image">
                                            <c:choose>
                                                <c:when test="${not empty estate.photoEstate}">
                                                    <img src="${estate.photoEstate}" alt="${estate.nameEstate}">
                                                </c:when>
                                                <c:otherwise>
                                                    Pas d'image disponible
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="estate-content">
                                            <div class="estate-header">
                                                <div>
                                                    <h3 class="estate-title">${estate.nameEstate}</h3>
                                                    <div class="estate-price" >${estate.dailyPrice}€ <span>/ nuit</span></div>
                                                </div>
                                                <span class="estate-status ${estate.valid ? 'valid' : 'pending'}">
                                                        ${estate.valid ? 'Actif' : 'Non actif'}
                                                </span>
                                            </div>
                                            <div class="estate-footer">
                                                <div class="estate-actions">
                                                    <button class="icon-btn" onclick="editEstate(${estate.idEstate})" title="Modifier">✏️</button>
                                                    <button class="icon-btn delete" onclick="deleteEstate(${estate.idEstate})" title="Supprimer">🗑️</button>
                                                </div>
                                            </div>
                                        </div>
                                        </a>
                                    </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                            </svg>
                            <h3>Aucun logement pour le moment</h3>
                            <p>Commencez par ajouter votre premier bien immobilier</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!-- Tab Content: Admin -->
        <div id="admin-tab" class="tab-content">
            <div class="profile-section">
                <div style="display: flex; flex-direction:column; justify-content: space-between; align-items: center; margin-bottom: 30px;">
                    <h2 style="margin: 0;">Admin</h2>
                    <!-- Estate Management Section -->
                    <div class="admin-section">
                        <h3>Logements</h3>
                        <!-- searchBar -->
                        <div class="search-container">
                            <label for="searchEstateId">Recherche ID Logement</label>
                            <div class="search-input-group" style="display: flex; align-items: center; justify-content: center; gap: 2rem;">
                                <input type="text"
                                       id="searchEstateId"
                                       class="form-control"
                                       placeholder="Entrez l'ID du logement"
                                       autocomplete="off"
                                       value="${param.idEstate}">
                                <button class="btn btn-primary" onclick="searchEstate()">
                                    <i class="bi bi-search"></i> Rechercher
                                </button>
                                <button class="btn btn-secondary" onclick="resetSearch()">
                                    <i class="bi bi-x-circle"></i> Réinitialiser
                                </button>
                            </div>
                        </div>
                        <!-- Affichage des résultats de recherche -->
                        <div id="searchResults" style="margin-top: 20px;">
                            <!-- Message de succès -->
                            <c:if test="${not empty searchSuccess and searchSuccess}">
                                <div class="alert alert-success" role="alert">Logement trouvé avec succès !</div>
                            </c:if>
                            <!-- Message d'erreur -->
                            <c:if test="${not empty searchError}">
                                <div class="alert alert-warning" role="alert">${searchError}</div>
                            </c:if>
                            <!-- Tableau des résultats -->
                            <c:choose>
                                <c:when test="${not empty estateFound}">
                                    <table class="results-table table table-striped">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Photo</th>
                                            <th>Nom</th>
                                            <th>Prix/jour</th>
                                            <th>Statut</th>
                                            <th>Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr onclick="window.location.href='${pageContext.request.contextPath}/detailsServlet?idEstate=${estateFound.idEstate}'"
                                            style="cursor: pointer;">
                                            <td>${estateFound.idEstate}</td>
                                            <td class="estate-image-admin">
                                                <c:choose>
                                                    <c:when test="${not empty estateFound.photoEstate}">
                                                        <img src="${pageContext.request.contextPath}/${estateFound.photoEstate}"
                                                             alt="${estateFound.nameEstate}"
                                                             style="max-width: 80px; height: auto;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span>Pas d'image</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="estate-title">${estateFound.nameEstate}</td>
                                            <td class="estate-price">${estateFound.dailyPrice} €</td>
                                            <td><span class="estate-status ${estateFound.valid ? 'valid' : 'pending'}">
                                                    ${estateFound.valid ? 'Actif' : 'Non actif'}
                                            </span></td>
                                            <td>
                                                <button class="btn-action btn-delete"
                                                        onclick="event.stopPropagation(); deleteEstateAdmin(${estateFound.idEstate}, event)">Supprimer
                                                </button>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="empty-results" id="estateEmptyState">
                                        <i class="bi bi-search" style="font-size: 48px; color: #6c757d;"></i>
                                        <p>Utilisez le formulaire ci-dessus pour rechercher un logement.</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <hr style="margin: 30px 0;"> <!-- Séparateur -->
                        <!-- listTable Estate-->
                        <div class="table-responsive" style="overflow: auto; max-height: 400px; margin: auto;">
                            <table class="table table-striped table-hover" id="estateTable">
                                <thead style="position: sticky; top: 0;">
                                <tr>
                                    <th>ID</th>
                                    <th>Photo</th>
                                    <th>Name</th>
                                    <th>Daily Price</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="estate" items="${list}">
                                    <tr onclick="window.location.href='${pageContext.request.contextPath}/detailsServlet?idEstate=${estate.idEstate}'"
                                        style="cursor: pointer;">
                                    <%-- <tr  onclick="populateForm('${estate.idEstate}','${estate.nameEstate}','${estate.dailyPrice}',
                                                '${estate.description}','${estate.idAdmin}','${estate.idUser}',
                                                '${estate.idAddress}','${estate.photoEstate}')" style="overflow: hidden; cursor: pointer;">--%>
                                        <td>${estate.idEstate}</td>
                                        <td class="estate-image-admin" ><img src="${pageContext.request.contextPath}/${estate.photoEstate}" alt=${estate.photoEstate}/></td>
                                        <td class="estate-title">${estate.nameEstate}</td>
                                        <td class="estate-price">${estate.dailyPrice} €</td>
                                        <td> <span class="estate-status ${estate.valid ? 'valid' : 'pending'}">
                                                ${estate.valid ? 'Actif' : 'Non actif'}
                                        </span></td>
                                        <td>
                                            <%--<a href="${pageContext.request.contextPath}/EstateServlet?action=delete&id=${estate.idEstate}"
                                               onclick="return confirm('Are you sure you want to delete this?')">Delete</a>--%>
                                                <button class="btn-action btn-delete" onclick="event.stopPropagation(); deleteEstateAdmin(${estate.idEstate}, event)">Supprimer</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- User Management Section -->
                    <div class="admin-section">
                        <h3>Recherche ID Guest / Host</h3>
                        <div class="search-container">
                        <label for="searchUserId">Recherche ID User</label>
                        <div class="search-input-group" style="display: flex; align-items: center; justify-content: center; gap: 2rem;">
                            <input type="text"
                                   id="searchUserId"
                                   class="form-control"
                                   placeholder="Entrez l'ID du User"
                                   autocomplete="off"
                                   value="${param.idUser}">
                            <button class="btn btn-primary" onclick="searchUser()">
                                <i class="bi bi-search"></i> Rechercher
                            </button>
                            <button class="btn btn-secondary" onclick="resetSearch()">
                                <i class="bi bi-x-circle"></i> Réinitialiser
                            </button>
                        </div>
                    </div>
                    <!-- Affichage des résultats de recherche -->
                    <div id="searchResults" style="margin-top: 20px;">
                        <!-- Message de succès -->
                        <c:if test="${not empty userSearchSuccess and userSearchSuccess}">
                            <div class="alert alert-success" role="alert">User trouvé avec succès !</div>
                        </c:if>
                        <!-- Message d'erreur -->
                        <c:if test="${not empty userSearchError}">
                            <div class="alert alert-warning" role="alert">${userSearchError}</div>
                        </c:if>
                        <!-- Tableau des résultats -->
                        <c:choose>
                            <c:when test="${not empty userFound}">
                                <table class="results-table table table-striped">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Firstname</th>
                                        <th>Lastname</th>
                                        <th>Email</th>
                                        <th>Statut</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>${userFound.idUser}</td>
                                        <td>${userFound.firstname}</td>
                                        <td>${userFound.lastname}</td>
                                        <td>${userFound.email}</td>
                                        <td><span class="estate-status ${userFound.isDeleted ? 'pending' : 'valid'}">
                                                ${userFound.isDeleted ? 'Non actif' : 'Actif'}</span>
                                        </td>
                                        <td>
                                            <button class="btn-action btn-delete"
                                                    onclick="event.stopPropagation(); deleteUserAdmin(${userFound.idUser}, event)">Supprimer
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <div class="empty-results" id="userEmptyState">
                                    <i class="bi bi-search" style="font-size: 48px; color: #6c757d;"></i>
                                    <p>Utilisez le formulaire ci-dessus pour rechercher un user.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <hr style="margin: 30px 0;"> <!-- Séparateur -->
                        <div class="table-responsive" style="overflow: auto; max-height: 400px;">
                            <table class="table table-striped table-hover" id="usersTable">
                                <thead style="position: sticky; top: 0;">
                                <tr>
                                    <th>ID</th>
                                    <th>Firstname</th>
                                    <th>Lastname</th>
                                    <th>Email</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${listUsers}">
                                     <tr  onclick="populateForm('${user.idUser}','${user.idAdmin}','${user.idRole}',
                                             '${user.idAddress}','${user.firstname}','${user.lastname}',
                                             '${user.phone}','${user.email}','${user.isDeleted}')"
                                              style="overflow: hidden; cursor: pointer;">
                                         <td>${user.idUser}</td>
                                         <td style="font-weight: bold">${user.firstname}</td>
                                         <td style="font-weight: bold">${user.lastname}</td>
                                         <td style="font-size: 12px">${user.email}</td>
                                         <td><span class="estate-status ${user.isDeleted ? 'pending' : 'valid'}">
                                                 ${user.isDeleted ? 'Non actif' : 'Actif'}</span>
                                         </td>
                                         <td><button class="btn-action btn-delete" onclick="event.stopPropagation(); deleteUserAdmin(${user.idUser},event)">Supprimer</button>
                                         </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <!-- Footer Actions -->
                    <div class="admin-footer" style="display: none;">
                        <button class="btn-footer btn-return" onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'">
                            Retour</button>
                        <button class="btn-footer btn-add" onclick="addNewItem()">Ajouter</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/public/footer.jsp" />

<!-- function in AdminJS -->
<script>
    // Define contextPath globally before loading admin.js
    window.contextPath = '${pageContext.request.contextPath}';
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
        integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
<script src="${pageContext.request.contextPath}/asset/js/map.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/weather.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/App.js"></script>
<script src="${pageContext.request.contextPath}/asset/js/adminJS.js"></script>
<script type="module" src="asset/js/UserJS.js"></script>
</body>
</html>