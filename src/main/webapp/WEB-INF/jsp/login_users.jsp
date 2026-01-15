<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un utilisateur</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/styleUser.css">
</head>
<body>
    <div class="container">
        <div id="login">
            <div>
                <div class="form-group">
                    <h1>Log In</h1>
                    <p>Bienvenue ! Connectez-vous à votre compte</p>
                </div>
                <div class="login-body">
                    <!-- Display error message if present -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-error">
                            <strong>Erreur :</strong> ${error}
                        </div>
                    </c:if>
                    <!-- Display success message if present -->
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                            <strong>Succès :</strong> ${success}
                        </div>
                    </c:if>
                    <!-- Display info message if present -->
                    <c:if test="${not empty info}">
                        <div class="alert alert-info">
                            <strong>Info :</strong> ${info}
                        </div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/user-servlet" method="post" id="loginForm">
                        <input name="actionUser" type="hidden" value="login">
                        <div class="form-group">
                            <label for="email">Email<span class="required">*</span></label>
                            <input
                                    type="text"
                                    id="email"
                                    name="email"
                                    placeholder="Entrez votre email"
                                    value="${param.email}"
                                    required
                                    autocomplete="email"
                            >
                        </div>

                        <div class="form-group">
                            <label for="password">Mot de passe<span class="required">*</span></label>
                            <div class="password-wrapper">
                                <input
                                        type="password"
                                        id="password"
                                        name="password"
                                        placeholder="Entrez votre mot de passe"
                                        required
                                        autocomplete="current-password"
                                >
                                <!--https://freefrontend.com/javascript-forms/-->
                                <button type="button" class="toggle-password" onclick="togglePassword()">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 256 256">
                                        <path d="M247.31,124.76c-.35-.79-8.82-19.58-27.65-38.41C194.57,61.26,162.88,48,128,48S61.43,61.26,36.34,86.35C17.51,105.18,9,124,8.69,124.76a8,8,0,0,0,0,6.5c.35.79,8.82,19.57,27.65,38.4C61.43,194.74,93.12,208,128,208s66.57-13.26,91.66-38.34c18.83-18.83,27.3-37.61,27.65-38.4A8,8,0,0,0,247.31,124.76ZM128,192c-30.78,0-57.67-11.19-79.93-33.25A133.47,133.47,0,0,1,25,128,133.33,133.33,0,0,1,48.07,97.25C70.33,75.19,97.22,64,128,64s57.67,11.19,79.93,33.25A133.46,133.46,0,0,1,231.05,128C223.84,141.46,192.43,192,128,192Zm0-112a48,48,0,1,0,48,48A48.05,48.05,0,0,0,128,80Zm0,80a32,32,0,1,1,32-32A32,32,0,0,1,128,160Z"></path>
                                    </svg>
                                </button>
                            </div>
                        </div>

                        <div class="remember-forgot">
                            <label class="remember-me">
                                <input type="checkbox" name="remember" id="remember">
                                <span>Se souvenir de moi</span>
                            </label>
                            <a href="${pageContext.request.contextPath}/forgot-password" class="forgot-password"> <!--TODO forgot password-->
                                Mot de passe oublié ?
                            </a>
                        </div>

                        <button type="submit" class="btn btn-primary">Se connecter</button>
                    </form>

                    <div class="signup-link">
                        Vous n'avez pas de compte ?
                        <a href="user-servlet?actionUser=signup">S'inscrire</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>
    /*
    // Validation en temps réel
    document.getElementById('phone').addEventListener('input', function(e) {
        this.value = this.value.replace(/\D/g, '').substring(0, 10);
    });

    // Validation de la confirmation du mot de passe
    document.querySelector('form').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Les mots de passe ne correspondent pas !');
            return false;
        }
    });
     */
</script>
</body>
</html>