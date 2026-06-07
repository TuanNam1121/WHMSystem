<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <title>Rom List</title>

            <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

            <link rel="stylesheet" href="assets/css/bootstrap.min.css">

            <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">

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

                <jsp:include page="/common/header.jsp"></jsp:include>
                <jsp:include page="/common/sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Storage List</h4>
                            </div>
                            <div class="page-btn">
                                <a href="AddStorage" class="btn btn-added"><img src="assets/img/icons/plus.svg"
                                        alt="img" class="me-2">Add New Storage</a>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <div class="table-top">
                                    <div class="search-set">
                                        <div class="search-path">
                                            <a class="btn btn-filter" id="filter_search">
                                                <img src="assets/img/icons/filter.svg" alt="img">
                                                <span><img src="assets/img/icons/closes.svg" alt="img"></span>
                                            </a>
                                        </div>
                                        <div class="search-input">
                                            <a class="btn btn-searchset"><img src="assets/img/icons/search-whites.svg"
                                                    alt="img"></a>
                                        </div>
                                    </div>

                                </div>

                                <form id="filterForm" action="StorageList" method="GET">
                                    <div class="card mb-0" id="filter_inputs">
                                        <div class="card-body pb-0">
                                            <div class="row">
                                                <div class="col-lg-12 col-sm-12">
                                                    <div class="row">
                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <select class="select" name="status">
                                                                    <option value="">Choose Status</option>
                                                                    <option value="active" <c:if
                                                                        test="${param.status == 'active'}">selected
                                                                        </c:if>>Active</option>
                                                                    <option value="inactive" <c:if
                                                                        test="${param.status == 'inactive'}">selected
                                                                        </c:if>>Inactive</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-1 col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <button type="submit" class="btn btn-filters ms-auto">
                                                                    <img src="assets/img/icons/search-whites.svg"
                                                                        alt="img">
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>


                                <div class="table-responsive">
                                    <table class="table datanew">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Size</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach var="rom" items="${roms}">
                                                <tr>
                                                    <td>${rom.id}</td>
                                                    <td>${rom.size}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${rom.active}">
                                                                <span>Active</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span>Inactive</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <form action="UpdateStorageStatus" method="post"
                                                            class="d-inline">
                                                            <input type="hidden" name="id" value="${rom.id}">
                                                            <input type="hidden" name="active"
                                                                value="${not rom.active}">
                                                            <input type="hidden" name="status" value="${param.status}">
                                                            <button type="submit"
                                                                class="btn btn-sm ${rom.active ? 'btn-outline-danger' : 'btn-outline-success'}">
                                                                ${rom.active ? 'Deactive' : 'Active'}
                                                            </button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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

            <script src="assets/js/moment.min.js"></script>
            <script src="assets/js/bootstrap-datetimepicker.min.js"></script>

            <script src="assets/plugins/select2/js/select2.min.js"></script>

            <script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
            <script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

            <script src="assets/js/script.js"></script>
        </body>

        </html>
