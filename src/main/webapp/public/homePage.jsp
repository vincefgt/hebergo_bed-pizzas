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
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center">
                <div id="card-detail" class="card shadow h-100 w-100" style="max-width: 350px;">
                    <a href="detailsServlet?idEstate=${estate.idEstate}" class="text-decoration-none">
                        <div class="card-img-top">
                            <img id="card-img" class="p-2 mt-2 w-100" src="${estate.photoEstate}/photo.jpg" alt="Photo ${estate.nameEstate}">
                        </div>

                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title text-dark">${estate.nameEstate}</h5>
                            <p class="card-text text-dark flex-grow-1">${estate.description}</p>
                        </div>
                        <div class="card-footer bg-transparent border-top">
                            <p class="text-dark text-end mb-0"><strong>${estate.dailyPrice} €</strong></p>
                        </div>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>