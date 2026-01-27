
document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.tab-btn');
    const tabs = document.querySelectorAll('.tab-content');

    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const tabName = button.dataset.tab;
            // Désactiver tout
            buttons.forEach(b => b.classList.remove('active'));
            tabs.forEach(t => t.classList.remove('active'));
            // Activer le bon onglet
            button.classList.add('active');
            document.getElementById(tabName + '-tab').classList.add('active');
        });
    });
});

document.querySelectorAll('.estate-actions').forEach(actions => {
    actions.addEventListener('click', (e) => {
        e.stopPropagation();
        e.preventDefault();
    });
});
// Ajouter la recherche avec la touche Entrée
document.addEventListener('DOMContentLoaded', function(e) {
    e.preventDefault();
    e.stopPropagation();
    const searchInput = document.getElementById('searchUserId');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchUser();}
        });
    }
});

function getContextPath() {
    return document.body.getAttribute('data-context-path') || '';
}

// Search Estate by ID
async function searchEstate() {
    const estateId = document.getElementById('searchEstateId').value.trim();

    if (!estateId) {
        alert('Veuillez entrer un ID de logement');
        return;
    }
    try {
        const response = await fetch(window.contextPath + '/AdminServlet?action=searchEstate&id=' + estateId);
        const data = await response.json();
        const tableBody = document.getElementById('estateTableBody');
        const table = document.getElementById('estateTable');
        const emptyState = document.getElementById('estateEmptyState');

        if (data && data.idEstate) {
            // Show table, hide empty state
            table.style.display = 'table';
            emptyState.style.display = 'none';

            // Populate table
            tableBody.innerHTML = `
                <tr>
                    <td>${data.idEstate}</td>
                    <td>${data.nameEstate}</td>
                    <td>${data.ownerName || 'N/A'}</td>
                    <td>
                        <span class="status-badge ${data.valid ? 'active' : 'inactive'}">
                            ${data.valid ? 'Actif' : 'Non actif'}
                        </span>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-action btn-activate ${data.valid ? 'active' : ''}" 
                                    onclick="toggleEstateStatus(${data.idEstate}, ${data.valid})">
                                ${data.valid ? 'Actif' : 'Activer'}
                            </button>
                            <button class="btn-action btn-modify" 
                                    onclick="modifyEstate(${data.idEstate})">
                                Modifier
                            </button>
                            <button class="btn-action btn-delete" 
                                    onclick="deleteEstateAdmin(${data.idEstate})">
                                Supprimer
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        } else {
            table.style.display = 'none';
            emptyState.style.display = 'block';
            emptyState.textContent = 'Aucun logement trouvé avec cet ID.';
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la recherche du logement');
    }
}

// Search User by ID
async function searchUser() {
    const userId = document.getElementById('searchUserId').value.trim();
    if (!userId) {
        alert('Veuillez entrer un ID utilisateur');
        return;}

    try {
        const response = await fetch(window.contextPath + '/user-servlet?actionUser=researchUser&idUser=' + userId);
        const data = await response.json();
        const tableBody = document.getElementById('userTableBody');
        const table = document.getElementById('userTable');
        const emptyState = document.getElementById('userEmptyState');

        if (response && response.idUser) {
            // Show table, hide empty state
            table.style.display = 'table';
            emptyState.style.display = 'none';

            // Populate table
            tableBody.innerHTML = `
                <tr>
                    <td>${data.idUser}</td>
                    <td>${data.role || 'Host'}</td>
                    <td>${data.firstname} ${data.lastname}</td>
                    <td>${data.email}</td>
                    <td>
                        <span class="status-badge ${data.active ? 'active' : 'inactive'}">
                            ${data.active ? 'Actif' : 'Inactif'}
                        </span>
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-action btn-activate ${data.active ? 'active' : ''}" 
                                    onclick="toggleUserStatus(${data.idUser}, ${data.active})">
                                ${data.active ? 'Actif' : 'Activer'}
                            </button>
                            <button class="btn-action btn-modify" 
                                    onclick="modifyUser(${data.idUser})">
                                Modifier
                            </button>
                            <button class="btn-action btn-delete" 
                                    onclick="deleteUserAdmin(${data.idUser})">
                                Supprimer
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        } else {
            table.style.display = 'none';
            emptyState.style.display = 'block';
            emptyState.textContent = 'Aucun utilisateur trouvé avec cet ID.';
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la recherche de l\'utilisateur');
    }
}

// Toggle Estate Status
async function toggleEstateStatus(estateId, currentStatus) {
    try {
        const response = await fetch(window.contextPath + '/AdminServlet?action=toggleEstateStatus&id=' + estateId + '&status=' + !currentStatus, {
            method: 'POST'
        });

        if (response.ok) {
            alert('Statut du logement modifié avec succès');
            searchEstate(); // Refresh results
        } else {
            alert('Erreur lors de la modification du statut');
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la modification du statut');
    }
}

// Toggle User Status
async function toggleUserStatus(userId, currentStatus) {
    try {
        const response = await fetch(window.contextPath + '/AdminServlet?action=toggleUserStatus&id=' + userId + '&status=' + !currentStatus, {
            method: 'POST'
        });

        if (response.ok) {
            alert('Statut de l\'utilisateur modifié avec succès');
            searchUser(); // Refresh results
        } else {
            alert('Erreur lors de la modification du statut');
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la modification du statut');
    }
}

// Modify Estate
function modifyEstate(estateId) {
    window.location.href = window.contextPath + '/detailsServlet?idEstate=' + estateId;
}

// Modify User
function modifyUser(userId) {
    window.location.href = window.contextPath + '/user-servlet?actionUser=edit&id=' + userId;
}

// Delete Estate (Admin)
async function deleteEstateAdmin(estateId, event) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce logement id ' + estateId + ' ?')) {
        try {
            const response = await fetch(getContextPath()+ '/EstateServlet?action=delete&idEstate=' + estateId);
            if (response.ok) {
                const row = event.target.closest('tr');
                if (row) {
                    setTimeout(() => {
                        row.remove();
                        // Vérifier s'il reste des lignes dans le tableau
                        const tableBody = document.querySelector('#estateTable');
                        if (tableBody && tableBody.children.length === 0) {
                            document.getElementById('estateTable').style.display = 'none';
                            const emptyState = document.getElementById('estateEmptyState');
                            if (emptyState) {
                                emptyState.style.display = 'block';
                                emptyState.textContent = 'Aucun logement disponible.';
                            }
                        }
                    }, 300);
                    alert('Logement supprimé avec succès');
                }
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression du logement !');
        }
    }
}

// Delete User (Admin)
async function deleteUserAdmin(userId, event) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur id '+userId+'?')) {
        try {
            const response = await fetch(getContextPath()+ '/user-servlet?action=delete&idUser=' + userId);
            if (response.ok) {
                const rowUser = event.target.closest('tr');
                if (rowUser) {
                    rowUser.remove();
                    // Vérifier s'il reste des lignes dans le tableau
                    const tableBody = document.querySelector('#usersTable');
                    if (tableBody && tableBody.children.length === 0) {
                        document.getElementById('userTable').style.display = 'none';
                        const emptyState = document.getElementById('estateEmptyState');
                        if (emptyState) {
                            emptyState.style.display = 'block';
                            emptyState.textContent = 'Aucun logement disponible.';
                        }
                    }
                }
                alert('User supprimé avec succès');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression de l\'utilisateur');
        }
    }
}

// Add New Item
function addNewItem() {
    const activeTab = document.querySelector('.tab-btn.active').dataset.tab;

    // You can customize this based on what should be added
    if (confirm('Voulez-vous ajouter un nouveau logement ?')) {
        window.location.href = window.contextPath + '/EstateServlet?action=add';
    }
}

//userForm
function resetForm() {
    document.querySelector('form').reset();}

function editEstate(estateId) {
    window.location.href =  window.contextPath + "/detailsServlet?idEstate=" + estateId;
}
//hostList
async function deleteEstate(estateId) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce logement ?')) {
        // Show loading state
        const card = event.target.closest('.estate-card');
        if (card) {
            card.style.opacity = '0.5';
            card.style.pointerEvents = 'none';
        }
        // Use fetch API to delete without page reload
        await fetch(getContextPath() +'/EstateServlet?action=delete&idEstate=' + estateId, {
            method: 'GET',
            headers: {'X-Requested-With': 'XMLHttpRequest'}
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erreur lors de la suppression');
                }
                return response.text();
            })
            .then(data => {
                // Remove the card from DOM with animation
                if (card) {
                    card.style.transition = 'all 0.3s ease';
                    card.style.transform = 'scale(0.8)';
                    card.style.opacity = '0';

                    setTimeout(() => {
                        card.remove();

                        // Check if there are any estates left
                        const estatesGrid = document.querySelector('.estates-grid');
                        if (estatesGrid && estatesGrid.children.length === 0) {
                            // Show empty state
                            estatesGrid.parentElement.innerHTML = `
                            <div class="empty-state">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                          d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                                </svg>
                                <h3>Aucun logement pour le moment</h3>
                                <p>Commencez par ajouter votre premier bien immobilier</p>
                            </div>
                        `;
                        }
                        alert('Logement supprimé avec succès');
                    }, 300);
                }
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Erreur lors de la suppression du logement');

                // Restore card state on error
                if (card) {
                    card.style.opacity = '1';
                    card.style.pointerEvents = 'auto';
                }
            });
    }
}

