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
        <button type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide-to="0" class="active" aria-current="true"></button>
        <button type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide-to="1"></button>
        <button type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide-to="2"></button>
    </div>

    <div class="carousel-inner">
        <!-- Slide 1 : 3 premières photos -->
        <div class="carousel-item active">
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb1.jpg" class="d-block" alt="Photo 1">
                <p><strong>Appartement . Rouen</strong></p>
                <p>13-15 mars . Hôte particulier</p>
                <p>85 € pour 2 nuits . <i class="bi bi-star-fill"></i> 4,79</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb2.jpg" class="d-block" alt="Photo 2">
                <p><strong>Appartement . Rouen</strong></p>
                <p>6-8 mars . Hôte particulier</p>
                <p>96 € pour 2 nuits . <i class="bi bi-star-fill"></i> 4,84</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb3.jpg" class="d-block" alt="Photo 3">
                <p><strong>Appartement . Rouen</strong></p>
                <p>15-17 mars . Hôte particulier</p>
                <p>130 € pour 2 nuits . <i class="bi bi-star-fill"></i> 4,94</p>
            </div>
        </div>

        <!-- Slide 2 : 3 photos suivantes -->
        <div class="carousel-item">
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb4.jpg" class="d-block" alt="Photo 4">
                <p><strong>Appartement . Rouen</strong></p>
                <p>12-15 mars . Hôte particulier</p>
                <p>120 € pour 2 nuits . <i class="bi bi-star-fill"></i> 4,69</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb5.jpg" class="d-block" alt="Photo 5">
                <p><strong>Appartement . Rouen</strong></p>
                <p>20-23 mars . Hôte particulier</p>
                <p>180 € pour 3 nuits . <i class="bi bi-star-fill"></i> 4,72</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb6.jpg" class="d-block" alt="Photo 6">
                <p><strong>Appartement . Rouen</strong></p>
                <p>25-27 mars . Hôte particulier</p>
                <p>140 € pour 2 nuits . <i class="bi bi-star-fill"></i> 4,85</p>
            </div>
        </div>

        <!-- Slide 3 : 3 autres photos -->
        <div class="carousel-item">
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb7.jpg" class="d-block" alt="Photo 7">
                <p><strong>Appartement . Rouen</strong></p>
                <p>02-08 mars . Hôte particulier</p>
                <p>450 € pour 6 nuits . <i class="bi bi-star-fill"></i> 4,88</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb8.jpg" class="d-block" alt="Photo 8">
                <p><strong>Appartement . Rouen</strong></p>
                <p>16-19 mars . Hôte particulier</p>
                <p>180 € pour 3 nuits . <i class="bi bi-star-fill"></i> 4,92</p>
            </div>
            <div>
                <img src="${pageContext.request.contextPath}/asset/images/airbnb9.jpg" class="d-block" alt="Photo 9">
                <p><strong>Appartement . Rouen</strong></p>
                <p>19-23 mars . Hôte particulier</p>
                <p>350 € pour 4 nuits . <i class="bi bi-star-fill"></i> 4,93</p>
            </div>
        </div>
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


