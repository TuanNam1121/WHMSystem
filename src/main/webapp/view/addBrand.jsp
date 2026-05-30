<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="permissions" scope="request" />
<c:set var="pageTitle" value="${act == 'new'  ? 'Add New Brand' : 'Update Brand'}" scope="request" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
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
            <div class="whirly-loader"> </div>
        </div>

        <jsp:include page="../common/header.jsp"></jsp:include>
        <jsp:include page="../common/sidebar.jsp"></jsp:include>

        

        <div class="main-wrapper">

            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>${act == 'new' ?'Brand ADD':'Brand Information'}</h4>
                            <h6>${act == 'new' ?'Create new Brand':'Update Brand'}</h6>
                        </div>
                    </div>
<c:if test="${not empty message}">
            <div class="alert-success">${message}</div>
        </c:if>
                    <form action="${pageContext.request.contextPath}/BrandDetail" method="post" enctype="multipart/form-data">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <input name="id" type="hidden" value="${brand.id}" readonly>
                                    <input name="act" type="hidden" value="${act}">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Brand Name</label>
                                            <input type="text" name="name" value="${brand.name}">
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <label>Description</label>
                                            <input class="form-control" type="text" name="description" value="${brand.description}">
                                        </div>
                                    </div>
                                    <c:if test="${act != 'new'}">
                                        <div class="col-lg-12">
                                            <div class="form-group">
                                                <label>created at</label>
                                                <input class="form-control" name="createdAt" value="${brand.createdAt}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="form-group">
                                                <label>updated at</label>
                                                <input class="form-control" value="${brand.updatedAt}" readonly>
                                            </div>
                                        </div>
                                            <div class="col-lg-12">
                                                <div class="form-group">
                                                    <label> Brand Image</label>
                                                    <div class="image-upload">
                                                        <input type="file" name="image" id="imageInput" accept="image/*">

                                                        <div class="image-uploads">
                                                            <img src="assets/img/icons/upload.svg" alt="img">
                                                            <h4 id="uploadText">Drag and drop a file to upload</h4>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                    </c:if>
                                    <div class="col-lg-12">
                                        <input class="btn btn-submit me-2" type="submit" value="${act == 'new' ?'ADD':'UPDATE'}">
                                        <a href="${pageContext.request.contextPath}/brandlist.html" class="btn btn-cancel">Cancel</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

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
    </body>
</html>