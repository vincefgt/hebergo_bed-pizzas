<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Estate Management</title>
  <style>
    .clickable-row { cursor: pointer; }
    .clickable-row:hover { background-color: #f5f5f5; }
  </style>
</head>
<body>

<h2>Estates List</h2>
<table border="1">
  <thead>
  <tr>
    <th>ID</th>
    <th>Image</th>
    <th>Name</th>
    <th>Price</th>
    <th>Status</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="estate" items="${list}">
    <tr class="clickable-row" onclick="populateForm('${estate.idEstate}', '${estate.nameEstate}', '${estate.dailyPrice}', '${estate.description}', '${estate.idAdmin}', '${estate.idUser}', '${estate.idAddress}')">
      <td>${estate.idEstate}</td>
      <td><img src="${pageContext.request.contextPath}/${estate.photoEstate}" width="50"></td>
      <td>${estate.nameEstate}</td>
      <td>${estate.dailyPrice} €</td>
      <td>${estate.valid ? "Available" : "Unavailable"}</td>
      <td>
        <a href="estates?action=delete&id=${estate.idEstate}"
           onclick="return confirm('Are you sure you want to delete this?')">Delete</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<hr>

<h2>Manage Estate</h2>
<form id="estateForm" action="estates" method="post" enctype="multipart/form-data">
  <input type="hidden" name="idEstate" id="formId">

  <label>Estate Name:</label>
  <input type="text" name="nameEstate" id="formName" required><br>

  <label>Daily Price:</label>
  <input type="number" step="0.01" name="dailyPrice" id="formPrice" required><br>

  <label>Description:</label>
  <textarea name="description" id="formDesc"></textarea><br>

  <label>ID Admin:</label>
  <input type="number" name="idAdmin" id="formAdmin" required><br>

  <label>ID User:</label>
  <input type="number" name="idUser" id="formUser" required><br>

  <label>ID Address:</label>
  <input type="number" name="idAddress" id="formAddress" required><br>

  <label>Photo:</label>
  <input type="file" name="photoFile" accept="image/*"><br>

  <button type="submit" id="submitBtn">Save Estate</button>
  <button type="button" onclick="resetForm()">Clear/New</button>
</form>

<script>
  // Fills the form fields when a table row is clicked
  function populateForm(id, name, price, desc, admin, user, address) {
    document.getElementById('formId').value = id;
    document.getElementById('formName').value = name;
    document.getElementById('formPrice').value = price;
    document.getElementById('formDesc').value = desc;
    document.getElementById('formAdmin').value = admin;
    document.getElementById('formUser').value = user;
    document.getElementById('formAddress').value = address;
    document.getElementById('submitBtn').innerText = "Update Estate";
  }

  // Resets the form for a new entry
  function resetForm() {
    document.getElementById('estateForm').reset();
    document.getElementById('formId').value = "";
    document.getElementById('submitBtn').innerText = "Save Estate";
  }
</script>

</body>
</html>