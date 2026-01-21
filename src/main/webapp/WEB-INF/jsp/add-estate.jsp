<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Estate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/asset/css/navBar.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/asset/css/footer.css" rel="stylesheet" />
</head>
<body class="bg-light">
<c:import url="../../public/navBar.jsp" />

<div class="container mt-5 mb-5">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0">Ajouter un nouveau logement</h3>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/EstateServlet"
                  method="post" enctype="multipart/form-data">
                <!-- Hidden field for update (optional) -->
                <input type="hidden" name="idEstate" value="${estate.idEstate}"/>

                <div class="mb-3">
                    <label for="nameEstate" class="form-label">Nom du logement</label>
                    <input type="text" class="form-control" id="nameEstate" name="nameEstate"
                           value="${estate.nameEstate}" required>
                </div>

                <div class="mb-3">
                    <label for="dailyPrice" class="form-label">Prix journalier (€)</label>
                    <input type="number" step="0.01" class="form-control" id="dailyPrice"
                           name="dailyPrice" value="${estate.dailyPrice}" required>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="4"
                              required>${estate.description}</textarea>
                </div>

                <div class="mb-3">
                    <label for="photoFile" class="form-label">Photo du logement</label>
                    <input type="file" class="form-control" id="photoFile" name="photoFile" accept="image/*">
                </div>

                <div class="row mb-3">
                    <div class="col">
                        <label for="idAdmin" class="form-label">ID Admin</label>
                        <input type="number" class="form-control" id="idAdmin" name="idAdmin"
                               value="${estate.idAdmin}" required>
                    </div>
                        <div class="col">
                        <label for="idUser" class="form-label">ID User</label>
                        <input type="number" class="form-control" id="idUser" name="idUser"
                               value="${estate.idUser}" required>
                    </div>
                    <div class="col">
                        <label for="idAddress" class="form-label">ID Address</label>
                        <input type="number" class="form-control" id="idAddress" name="idAddress"
                               value="${estate.idAddress}" required>
                    </div>
                </div>

                <div class="d-flex justify-content-end">
                    <a href="${pageContext.request.contextPath}/param_users.jsp"
                       class="btn btn-secondary me-2">Annuler</a>
                    <button type="submit" class="btn btn-primary">
                        ${estate.idEstate != null ? "Modifier" : "Ajouter"}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<c:import url="../../public/footer.jsp" />

<script src="${pageContext.request.contextPath}/asset/js/App.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
</body>


</html>