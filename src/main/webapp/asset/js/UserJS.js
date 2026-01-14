/*JS*/

function togglePassword() {
    const passwordInput = document.getElementById('password');
    const toggleButton = document.querySelector('.toggle-password');

    if (passwordInput.type === 'password') {
    passwordInput.type = 'text';
    toggleButton.textContent = '🙈';
} else {
    passwordInput.type = 'password';
    toggleButton.textContent = '👁️';
}
}

    // Form validation
    document.getElementById('loginForm').addEventListener('submit', function(e) {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;

    if (username === '') {
    e.preventDefault();
    alert('Veuillez entrer votre nom d\'utilisateur');
    return false;
}

    if (password === '') {
    e.preventDefault();
    alert('Veuillez entrer votre mot de passe');
    return false;
}

    if (password.length < 6) {
    e.preventDefault();
    alert('Le mot de passe doit contenir au moins 6 caractères');
    return false;
}
});

    // Auto-hide alerts after 5 seconds
setTimeout(function() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
    alert.style.transition = 'opacity 0.5s ease-out';
    alert.style.opacity = '0';
    setTimeout(function() {
    alert.remove();
}, 500);
});
}, 5000);
