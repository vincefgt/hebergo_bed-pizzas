<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Estate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title mb-0">Add New Estate</h3>
                </div>
                <div class="card-body">
                    <form action="estates" method="post">
                        <div class="mb-3">
                            <label class="form-label">Estate Name</label>
                            <input type="text" name="nameEstate" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="description" class="form-control" rows="3"></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Daily Price (€)</label>
                            <input type="number" step="0.01" name="dailyPrice" class="form-control" required>
                        </div>

                        <input type="hidden" name="idAdmin" value="1">
                        <input type="hidden" name="idUser" value="1">
                        <input type="hidden" name="idAddress" value="1">

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">Save Estate</button>
                            <a href="estates" class="btn btn-secondary">Back to List</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>