// Attendre que le DOM soit complètement chargé
document.addEventListener('DOMContentLoaded', function() {

    // Gestion du menu hamburger et modal
    const firstButton = document.querySelector('.first-button');
    const hamburgerModal = document.getElementById('hamburgerModal');
    const closeModal = document.getElementById('closeModal');

    if (firstButton) {
        firstButton.addEventListener('click', function () {
            document.querySelector('.animated-icon1').classList.toggle('open');
            // Ouvrir le modal au lieu du collapse
            if (hamburgerModal) {
                hamburgerModal.classList.toggle('active');
            }
        });
    }

    // Fermer le modal
    if (closeModal) {
        closeModal.addEventListener('click', function() {
            hamburgerModal.classList.remove('active');
            document.querySelector('.animated-icon1').classList.remove('open');
        });
    }

    // Fermer le modal en cliquant en dehors
    if (hamburgerModal) {
        hamburgerModal.addEventListener('click', function(e) {
            if (e.target === hamburgerModal) {
                hamburgerModal.classList.remove('active');
                document.querySelector('.animated-icon1').classList.remove('open');
            }
        });
    }

    // Actions des boutons du modal
    const loginBtn = document.getElementById('loginBtn');
    const settingsBtn = document.getElementById('settingsBtn');

    if (loginBtn) {
        loginBtn.addEventListener('click', function() {
            console.log('Connexion demandée');
            // Ajoutez ici votre logique de connexion
            alert('Redirection vers la page de connexion...');
        });
    }

    if (settingsBtn) {
        settingsBtn.addEventListener('click', function() {
            console.log('Paramètres demandés');
            // Ajoutez ici votre logique de paramètres
            alert('Ouverture des paramètres...');
        });
    }

    // Gestion des options supplémentaires (prix)
    const toggleAdditionalOptions = document.getElementById('toggleAdditionalOptions');
    const priceFilterSection = document.getElementById('priceFilterSection');

    if (toggleAdditionalOptions && priceFilterSection) {
        toggleAdditionalOptions.addEventListener('click', function() {
            priceFilterSection.classList.toggle('active');
            toggleAdditionalOptions.classList.toggle('active');
        });
    }

    // === FILTRE DE RECHERCHE ===
    // Gestion destination
    const destinationField = document.getElementById('destinationField');
    const destinationInput = document.getElementById('destinationInput');
    const destinationDropdown = document.getElementById('destinationDropdown');

    if (destinationField && destinationInput && destinationDropdown) {
        destinationField.addEventListener('click', (e) => {
            e.stopPropagation();
            closeAllDropdowns();
            destinationDropdown.classList.add('active');
        });

        destinationInput.addEventListener('input', (e) => {
            const searchTerm = e.target.value.toLowerCase();
            const cityItems = document.querySelectorAll('.city-item');

            cityItems.forEach(item => {
                const cityName = item.textContent.toLowerCase();
                if (cityName.includes(searchTerm)) {
                    item.classList.remove('hidden');
                } else {
                    item.classList.add('hidden');
                }
            });

            if (!destinationDropdown.classList.contains('active')) {
                destinationDropdown.classList.add('active');
            }
        });
    }

    function selectCity(city) {
        destinationInput.value = city;
        destinationDropdown.classList.remove('active');
    }

    // Gestion calendrier
    const dateField = document.getElementById('dateField');
    const dateInput = document.getElementById('dateInput');
    const calendarDropdown = document.getElementById('calendarDropdown');
    const calendarsContainer = document.getElementById('calendarsContainer');
    const prevMonthBtn = document.getElementById('prevMonth');
    const nextMonthBtn = document.getElementById('nextMonth');
    const confirmDatesBtn = document.getElementById('confirmDates');
    const clearDatesBtn = document.getElementById('clearDates');

    let startDate = null;
    let endDate = null;
    let currentDisplayMonth = new Date();

    if (dateField && dateInput && calendarDropdown) {
        dateField.addEventListener('click', (e) => {
            e.stopPropagation();
            closeAllDropdowns();
            currentDisplayMonth = new Date();
            generateCalendars();
            calendarDropdown.classList.add('active');
        });
    }

    if (prevMonthBtn) {
        prevMonthBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            currentDisplayMonth.setMonth(currentDisplayMonth.getMonth() - 1);
            generateCalendars();
        });
    }

    if (nextMonthBtn) {
        nextMonthBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            currentDisplayMonth.setMonth(currentDisplayMonth.getMonth() + 1);
            generateCalendars();
        });
    }

    function generateCalendars() {
        if (!calendarsContainer) return;

        const today = new Date();
        today.setHours(0, 0, 0, 0);

        const month1 = new Date(currentDisplayMonth.getFullYear(), currentDisplayMonth.getMonth(), 1);
        const month2 = new Date(currentDisplayMonth.getFullYear(), currentDisplayMonth.getMonth() + 1, 1);

        calendarsContainer.innerHTML = '';
        calendarsContainer.appendChild(createCalendar(month1, today));
        calendarsContainer.appendChild(createCalendar(month2, today));
    }

    function createCalendar(date, today) {
        const calendar = document.createElement('div');
        calendar.className = 'calendar';

        const header = document.createElement('div');
        header.className = 'calendar-header';
        const monthYear = date.toLocaleDateString('fr-FR', { month: 'long', year: 'numeric' });
        header.textContent = monthYear.charAt(0).toUpperCase() + monthYear.slice(1);
        calendar.appendChild(header);

        const grid = document.createElement('div');
        grid.className = 'calendar-grid';

        ['L', 'M', 'M', 'J', 'V', 'S', 'D'].forEach(day => {
            const dayHeader = document.createElement('div');
            dayHeader.className = 'calendar-day-header';
            dayHeader.textContent = day;
            grid.appendChild(dayHeader);
        });

        const firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        const startDay = firstDay.getDay() === 0 ? 6 : firstDay.getDay() - 1;

        for (let i = 0; i < startDay; i++) {
            const emptyDay = document.createElement('div');
            emptyDay.className = 'calendar-day empty';
            grid.appendChild(emptyDay);
        }

        for (let i = 1; i <= lastDay.getDate(); i++) {
            const dayElement = document.createElement('div');
            dayElement.className = 'calendar-day';
            dayElement.textContent = i;
            const currentDate = new Date(date.getFullYear(), date.getMonth(), i);
            currentDate.setHours(0, 0, 0, 0);

            if (currentDate < today) {
                dayElement.classList.add('past');
            } else {
                dayElement.onclick = (e) => {
                    e.stopPropagation();
                    selectDate(currentDate);
                };
            }

            if (startDate && currentDate.getTime() === startDate.getTime()) {
                dayElement.classList.add('start-range');
            }
            if (endDate && currentDate.getTime() === endDate.getTime()) {
                dayElement.classList.add('end-range');
            }
            if (startDate && endDate && currentDate > startDate && currentDate < endDate) {
                dayElement.classList.add('in-range');
            }

            grid.appendChild(dayElement);
        }

        const totalCells = startDay + lastDay.getDate();
        const remainingCells = 42 - totalCells;
        for (let i = 0; i < remainingCells; i++) {
            const emptyDay = document.createElement('div');
            emptyDay.className = 'calendar-day empty';
            grid.appendChild(emptyDay);
        }

        calendar.appendChild(grid);
        return calendar;
    }

    function selectDate(date) {
        if (!startDate || (startDate && endDate)) {
            startDate = date;
            endDate = null;
        } else if (date > startDate) {
            endDate = date;
        } else {
            endDate = startDate;
            startDate = date;
        }
        generateCalendars();
    }

    if (confirmDatesBtn) {
        confirmDatesBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            if (startDate && endDate) {
                const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
                dateInput.value = `${startDate.toLocaleDateString('fr-FR', options)} - ${endDate.toLocaleDateString('fr-FR', options)}`;
                calendarDropdown.classList.remove('active');
            } else if (startDate) {
                const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
                dateInput.value = startDate.toLocaleDateString('fr-FR', options);
                calendarDropdown.classList.remove('active');
            }
        });
    }

    if (clearDatesBtn) {
        clearDatesBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            startDate = null;
            endDate = null;
            dateInput.value = '';
            generateCalendars();
        });
    }

    // Gestion voyageurs
    const guestsField = document.getElementById('guestsField');
    const guestsInput = document.getElementById('guestsInput');
    const guestsDropdown = document.getElementById('guestsDropdown');
    const guestsNumber = document.getElementById('guestsNumber');
    const confirmGuestsBtn = document.getElementById('confirmGuests');

    if (guestsField && guestsDropdown) {
        guestsField.addEventListener('click', (e) => {
            e.stopPropagation();
            closeAllDropdowns();
            guestsDropdown.classList.add('active');
            setTimeout(() => {
                if (guestsNumber) guestsNumber.focus();
            }, 100);
        });
    }

    if (confirmGuestsBtn && guestsNumber && guestsInput) {
        confirmGuestsBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            const numGuests = parseInt(guestsNumber.value);
            if (numGuests > 0) {
                guestsInput.value = `${numGuests} voyageur${numGuests > 1 ? 's' : ''}`;
                guestsDropdown.classList.remove('active');
            }
        });

        guestsNumber.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                confirmGuestsBtn.click();
            }
        });

        guestsNumber.addEventListener('click', (e) => {
            e.stopPropagation();
        });
    }

    // Validation globale du filtre (bouton avec loupe)
    const searchField = document.getElementById('searchField');
    if (searchField) {
        searchField.addEventListener('click', function() {
            const destination = destinationInput ? destinationInput.value : '';
            const dates = dateInput ? dateInput.value : '';
            const guests = guestsInput ? guestsInput.value : '';
            const minPrice = document.getElementById('minPrice') ? document.getElementById('minPrice').value : '';
            const maxPrice = document.getElementById('maxPrice') ? document.getElementById('maxPrice').value : '';

            // Vérification basique
            if (!destination) {
                alert('Veuillez sélectionner une destination');
                return;
            }

            // Construire l'objet de recherche
            const searchParams = {
                destination: destination,
                dates: dates,
                guests: guests,
                minPrice: minPrice,
                maxPrice: maxPrice
            };

            console.log('Recherche lancée avec les paramètres:', searchParams);

            alert(`Recherche lancée:\nDestination: ${destination}\nDates: ${dates || 'Non spécifiées'}\nVoyageurs: ${guests || 'Non spécifié'}\nPrix: ${minPrice || '0'}€ - ${maxPrice || '∞'}€`);
        });
    }

    // Fermer les dropdowns
    function closeAllDropdowns() {
        if (destinationDropdown) destinationDropdown.classList.remove('active');
        if (calendarDropdown) calendarDropdown.classList.remove('active');
        if (guestsDropdown) guestsDropdown.classList.remove('active');
    }

    document.addEventListener('click', closeAllDropdowns);

    // Empêcher la fermeture des dropdowns quand on clique dedans
    if (calendarDropdown) {
        calendarDropdown.addEventListener('click', (e) => {
            e.stopPropagation();
        });
    }

    if (guestsDropdown) {
        guestsDropdown.addEventListener('click', (e) => {
            e.stopPropagation();
        });
    }

    if (destinationDropdown) {
        destinationDropdown.addEventListener('click', (e) => {
            e.stopPropagation();
        });
    }

});