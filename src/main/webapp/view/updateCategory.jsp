<%@ page import="com.swp.whmsystem.model.Category" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Bootstrap Admin Template">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Dreams Pos admin template</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">

    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">

    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<div id="global-loader">
    <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">

    <jsp:include page="../common/sidebar.jsp"></jsp:include>
    <jsp:include page="../common/header.jsp"></jsp:include>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Edit Product Category</h4>
                    <h6>Edit a product Category</h6>
                </div>
            </div>

            <div class="card">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                            ${error}
                    </div>
                </c:if>

                <%
                    Category category = (Category) request.getAttribute("category");
                %>

                <form action="updateCategory" method="post">
                    <input type="hidden" name="categoryid" value="${category.categoryId}">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Category Name</label>
                                    <input type="text" value="${category.name}" name="categoryName">
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea class="form-control" name="description" placeholder="Enter category description...">${category.description}</textarea>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label>Status</label>
                                    <div class="d-flex align-items-center" style="gap: 8px;">
                                        <input type="hidden" name="isActive" id="isActiveInput" value="${category.isActive}">
                                        <button type="button" id="btnActive"
                                            onclick="setStatus(true)"
                                            class="btn <c:choose><c:when test="${category.isActive}">btn-success</c:when><c:otherwise>btn-outline-success</c:otherwise></c:choose>"
                                            style="min-width: 100px; font-weight: 600;">
                                            Active
                                        </button>
                                        <button type="button" id="btnInactive"
                                            onclick="setStatus(false)"
                                            class="btn <c:choose><c:when test="${not category.isActive}">btn-danger</c:when><c:otherwise>btn-outline-danger</c:otherwise></c:choose>"
                                            style="min-width: 100px; font-weight: 600;">
                                            Inactive
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <input type="submit" class="btn btn-submit me-2" value="Submit">
                                <!--<a href="javascript:void(0);" >Submit</a>-->
                                <a href="categoryList" class="btn btn-cancel">Cancel</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>


<script src="assets/js/jquery-3.6.0.min.js"></script>

<script src="assets/js/feather.min.js"></script>

<script src="assets/js/jquery.slimscroll.min.js"></script>

<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/dataTables.bootstrap4.min.js"></script>

<script src="assets/js/bootstrap.bundle.min.js"></script>

<script src="assets/plugins/select2/js/select2.min.js"></script>

<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

<script src="assets/js/script.js"></script>
<script>
    function setStatus(isActive) {
        document.getElementById('isActiveInput').value = isActive;
        var btnActive   = document.getElementById('btnActive');
        var btnInactive = document.getElementById('btnInactive');
        if (isActive) {
            btnActive.className   = 'btn btn-success';
            btnInactive.className = 'btn btn-outline-danger';
        } else {
            btnActive.className   = 'btn btn-outline-success';
            btnInactive.className = 'btn btn-danger';
        }
    }
</script>
</body>
</html>