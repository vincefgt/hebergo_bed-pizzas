<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    Created by IntelliJ IDEA.
    User: User
    Date: 08/01/2026
    Time: 17:02
    To change this template use File | Settings | File Templates.
--%>

<header>
    <nav class="navbar bg-body-tertiary">
        <div class="container-fluid">
            <a href="${pageContext.request.contextPath}/index.jsp">
            <img id="logo" src="${pageContext.request.contextPath}/asset/images/Bed_and_Pizza_logo.jpg" alt="Bed and Pizza logo">
            </a>

            <button data-mdb-button-init class="navbar-toggler first-button ms-auto" type="button" data-mdb-collapse-init
                    data-mdb-target="#navbarToggleExternalContent9" aria-controls="navbarToggleExternalContent9"
                    aria-expanded="false" aria-label="Toggle navigation">
                <div class="animated-icon1"><span></span><span></span><span></span></div>
            </button>
        </div>

        <!-- Filtre de recherche -->
        <div class="search-bar col-10 col-lg-6 offset-1">
            <div class="search-field" id="destinationField">
                <label>Destination</label>
                <input type="text" id="destinationInput" placeholder="Rechercher une destination">
            </div>

            <div class="search-field" id="dateField">
                <label>Dates</label>
                <input type="text" id="dateInput" placeholder="Quand ?" readonly>
                <div class="calendar-dropdown" id="calendarDropdown">
                    <div class="calendar-tabs">
                        <button class="calendar-tab active">Dates</button>
                    </div>
                    <div class="calendar-navigation">
                        <button class="calendar-nav-btn" id="prevMonth">&lt;</button>
                        <button class="calendar-nav-btn" id="nextMonth">&gt;</button>
                    </div>
                    <div class="calendars-container" id="calendarsContainer"></div>
                    <div class="calendar-footer">
                        <div class="calendar-actions">
                            <button class="clear-btn" id="clearDates">Effacer</button>
                            <button class="search-btn-calendar" id="confirmDates">Valider</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="search-field" id="guestsField">
                <label>Voyageurs</label>
                <input type="text" id="guestsInput" placeholder="Ajouter des voyageurs" readonly>
                <div class="guests-dropdown col-12" id="guestsDropdown">
                    <div class="guest-input-group">
                        <label for="guestsNumber">Nombre de voyageurs</label>
                        <input type="number" id="guestsNumber" min="1" max="20" value="1">
                    </div>
                    <button class="guest-confirm-btn" id="confirmGuests">Valider</button>
                </div>
            </div>
            <div class="search-field" id="searchField">
                <p>Valider<img class="ms-3" src="${pageContext.request.contextPath}/asset/images/loupe.png" alt="Recherche logo"></p>
            </div>
        </div>

        <!-- Lien Options supplémentaires et filtres prix -->
        <div class="options-and-price-wrapper">
            <div class="additional-options-container">
                <button class="additional-options-btn" id="toggleAdditionalOptions">
                    <span>Options supplémentaires</span>
                    <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                        <path d="M4 6L8 10L12 6" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                </button>
            </div>

            <!-- Section prix (cachée par défaut) -->
            <div class="price-filter-section" id="priceFilterSection">
                <div class="price-filter-container">
                    <div class="price-input-group">
                        <label for="minPrice">Prix minimum (&euro;)</label>
                        <input type="number" id="minPrice" min="0" placeholder="0">
                    </div>
                    <div class="price-separator">-</div>
                    <div class="price-input-group">
                        <label for="maxPrice">Prix maximum (&euro;)</label>
                        <input type="number" id="maxPrice" min="0" placeholder="1000">
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <!-- Modal hamburger -->
    <div class="hamburger-modal" id="hamburgerModal">
        <div class="hamburger-modal-content">
            <button class="modal-close-btn" id="closeModal"></button>
            <c:if test="${empty sessionScope.user}">
            <div class="modal-menu">
                <a href="user-servlet?actionUser=login" class="modal-menu-item">
                    <i class="bi bi-box-arrow-in-right"><span> Se connecter</span></i>
                </a>
                <a href="user-servlet?actionUser=signup" class="modal-menu-item" >
                    <i class="bi bi-person-plus"><span> Créer compte</span></i>
                </a>
            </div>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
            <div class="modal-menu">
                <a href="user-servlet?actionUser=paramUser" class="modal-menu-item" id="paramMenu" >
                    <i class="bi bi-person-gear"><span> Paramètre</span></i></a>
                <a href="user-servlet?actionUser=logout" class="modal-menu-item" id="logoutMenu" >
                    <i class="bi bi-box-arrow-left"><span> Déconnexion</span></i></a>
            </div>
            </c:if>
        </div>
    </div>
</header>