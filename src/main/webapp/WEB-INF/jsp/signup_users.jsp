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
    <div id="signup">
        <h1>Créer un utilisateur</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                    ${error}
            </div>
        </c:if>
        <c:if test="${not empty success}">
            <script>
                alert("${success}");
            </script>
        </c:if>
        <form action="${pageContext.request.contextPath}/user-servlet" method="post">
            <input name="actionUser" type="hidden" value="signup">
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

            <div class="form-row">
               <!-- <div class="form-group" contenteditable="false"> // hidden="hidden">
                    <label for="labelRole">Profil Utilisateur</label>
                    <input type="text" id="labelRole" name="labelRole"
                           value="${labelRole != null ? labelRole : 1}" min="1">
                    <small>Par défaut: guest</small>
                </div> -->
                <div class="form-group">
                    <label for="labelRole">Profil utilisateur</label>
                    <select id="labelRole" name="labelRole">
                        <option value="GUEST" ${labelRole == 'GUEST' ? 'selected' : ''}>Guest</option>
                        <option value="USER"  ${labelRole == 'USER'  ? 'selected' : ''}>Utilisateur</option>
                        <option value="ADMIN" ${labelRole == 'ADMIN' ? 'selected' : ''}>Admin</option>
                        <!-- with BDD -->
                        <c:forEach var="role" items="${roles}">
                        <option value="${role.id}" ${role.id == labelRole ? 'selected' : ''}>
                        </c:forEach>
                    </select>
                    <!--<small>Par défaut : guest</small>-->
                </div>

                <div class="form-group" contenteditable="false" hidden="hidden">
                    <label for="idAdmin">ID Admin</label>
                    <input type="number" id="idAdmin" name="idAdmin"
                           value="${idAdmin}" min="1">
                    <small>Optionnel</small>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Créer l'utilisateur</button>

            <div class="form-footer">
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">Annuler</a>
            </div>
        </form>
    </div>
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