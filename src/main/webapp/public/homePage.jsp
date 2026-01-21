<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/01/2026
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel" data-bs-interval="8000">
    <!-- Indicateurs de slides -->
    <div class="carousel-indicators">
        <c:forEach var="estate" items="${estatesList}" varStatus="status">
            <button type="button"
                    data-bs-target="#carouselExampleAutoplaying"
                    data-bs-slide-to="${status.index}"
                    class="${status.first ? 'active' : ''}"
                    aria-current="${status.first ? 'true' : 'false'}"></button>
        </c:forEach>
    </div>
    <div class="carousel-inner">
        <c:forEach var="estate" items="${estatesList}" varStatus="status">
            <a href="detailsServlet?idEstate=${estate.idEstate}">
                <div class="carousel-item ${status.first ? 'active' : ''}">
                    <img src="${estate.photoEstate}/photo.jpg" class="d-block offset-1 w-50" alt="Photo ${status.index + 1}">
                    <div>
                        <h5 class="text-dark">${estate.nameEstate}</h5>
                        <p class="text-dark">${estate.description}</p>
                        <p class="text-dark"><strong>${estate.dailyPrice} €</strong></p>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<div class="offset-1">
    <div class="row">

        <c:forEach var="estate" items="${estatesList}">
            <div id="card-detail" class="card col-3 m-4 shadow p-3 mb-5">
                <a href="detailsServlet?idEstate=${estate.idEstate}">
                    <img id="card-img" class="col-12 mt-2 rounded" src="${estate.photoEstate}/photo.jpg" alt="Photo 1">
                    <div class="card-body">
                        <h5 class="card-title text-dark">${estate.nameEstate}</h5>
                        <p class="text-dark">${estate.description}</p>
                    </div>
                    <div class="card-footer">
                        <p class="text-dark"><strong>${estate.dailyPrice} €</strong></p>
                    </div>
                </a>
            </div>
        </c:forEach>

    </div>
</div>
