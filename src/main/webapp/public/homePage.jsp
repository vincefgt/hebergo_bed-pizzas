<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08/01/2026
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="offset-1">
    <div class="row">
        <c:forEach var="estate" items="${estatesList}">
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4">
                <div id="card-detail" class="card shadow h-100 w-100">
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