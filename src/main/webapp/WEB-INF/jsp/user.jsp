<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un utilisateur</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            padding: 40px;
            max-width: 600px;
            width: 100%;
        }

        h1 {
            color: #333;
            margin-bottom: 30px;
            text-align: center;
        }

        .alert {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: 500;
        }

        .required {
            color: #e74c3c;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="number"],
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }

        input:focus,
        select:focus {
            outline: none;
            border-color: #667eea;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            width: 100%;
            margin-top: 10px;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }

        .btn-secondary:hover {
            background: #5a6268;
        }

        .form-footer {
            margin-top: 20px;
            text-align: center;
        }

        small {
            color: #777;
            font-size: 12px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Créer un utilisateur</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-error">
                ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/user/create" method="post">
        <div class="form-row">
            <div class="form-group">
                <label for="firstname">Prénom <span class="required">*</span></label>
                <input type="text" id="firstname" name="firstname"
                       value="${firstname}" required maxlength="50">
            </div>

            <div class="form-group">
                <label for="lastname">Nom <span class="required">*</span></label>
                <input type="text" id="lastname" name="lastname"
                       value="${lastname}" required maxlength="50">
            </div>
        </div>

        <div class="form-group">
            <label for="email">Email <span class="required">*</span></label>
            <input type="email" id="email" name="email"
                   value="${email}" required maxlength="50">
            <small>Format: exemple@domaine.com</small>
        </div>

        <div class="form-group">
            <label for="phone">Téléphone <span class="required">*</span></label>
            <input type="text" id="phone" name="phone"
                   value="${phone}" required pattern="\d{10}" maxlength="10">
            <small>10 chiffres sans espaces</small>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="password">Mot de passe <span class="required">*</span></label>
                <input type="password" id="password" name="password"
                       required minlength="8">
                <small>Minimum 8 caractères</small>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirmer <span class="required">*</span></label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       required minlength="8">
            </div>
        </div>

        <div class="form-group">
            <label for="idAddress">ID Adresse <span class="required">*</span></label>
            <input type="number" id="idAddress" name="idAddress"
                   value="${idAddress}" required min="1">
            <small>L'adresse doit exister dans la table Addresses</small>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="idRole">ID Rôle</label>
                <input type="number" id="idRole" name="idRole"
                       value="${idRole != null ? idRole : 1}" min="1">
                <small>Par défaut: 1</small>
            </div>

            <div class="form-group">
                <label for="idAdmin">ID Admin</label>
                <input type="number" id="idAdmin" name="idAdmin"
                       value="${idAdmin}" min="1">
                <small>Optionnel</small>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Créer l'utilisateur</button>

        <div class="form-footer">
            <a href="${pageContext.request.contextPath}/user/list" class="btn btn-secondary">
                Annuler
            </a>
        </div>
    </form>
</div>

<script>
    // Validation en temps réel du téléphone
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
</script>
</body>
</html>