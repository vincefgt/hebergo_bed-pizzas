<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DEV01
  Date: 14/01/2026
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>estate name</th>
    <th>description</th>
    <th>price</th>
    <th>status</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="estate" items="${list}">
    <tr>
      <td>${estate.idEstate}</td>
      <td>${estate.nameEstate}</td>
      <td>${estate.description}</td>
      <td>${estate.dailyPrice} €</td>
      <td>${estate.valid ? "available" : "unavailable"}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<form action="estates" method="post" class="p-4 border rounded shadow-sm">
  <div class="mb-3">
    <label class="form-label">Estate Name</label>
    <input type="text" name="nameEstate" class="form-control" required>
  </div>
  <div class="mb-3">
    <label class="form-label">Daily Price</label>
    <input type="number" step="0.01" name="dailyPrice" class="form-control" required>
  </div>
  <div class="mb-3">
    <label class="form-label">Description</label>
    <textarea name="description" class="form-control"></textarea>
  </div>
  <label class="form-label">ID admin</label>
  <input type="number" name="idAdmin" required>
  <label class="form-label">ID user</label>
  <input type="number" name="idUser" required>
  <label class="form-label">ID address</label>
  <input type="number" name="idAddress" required>

  <button type="submit" class="btn btn-primary w-100">Save Estate</button>
</form>
</body>
</html>
