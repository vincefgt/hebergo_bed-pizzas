<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
    Created by IntelliJ IDEA.
    User: User
    Date: 08/01/2026
    Time: 17:02
    To change this template use File | Settings | File Templates.
--%>


<header>
    <section class="mb-3">
        <nav class="navbar bg-body-tertiary">
            <div class="container-fluid">
                <img id="logo" src="${pageContext.request.contextPath}/asset/images/Bed_and_Pizza_logo.jpg" alt="Bed and Pizza">

                <!-- Filtre de recherche -->
                <div class="search-bar">
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
                        <div class="guests-dropdown" id="guestsDropdown">
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

                <button data-mdb-button-init class="navbar-toggler first-button ms-auto" type="button" data-mdb-collapse-init
                        data-mdb-target="#navbarToggleExternalContent9" aria-controls="navbarToggleExternalContent9"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <div class="animated-icon1"><span></span><span></span><span></span></div>
                </button>
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
                <button class="modal-close-btn" id="closeModal">&times;</button>
                <div class="modal-menu">
                    <button class="modal-menu-item" id="loginBtn">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                            <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                        </svg>
                        <span>Se connecter</span>
                    </button>
                    <button class="modal-menu-item" id="settingsBtn">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                            <circle cx="12" cy="12" r="3" stroke="currentColor" stroke-width="2"/>
                            <path d="M12 1v6m0 6v6M23 12h-6m-6 0H1" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                        </svg>
                        <span>Paramètres</span>
                    </button>
                </div>
            </div>
        </div>
    </section>
</header>