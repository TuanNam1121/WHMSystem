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
            <title>Inventory Transaction</title>

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

                <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
                <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Inventory Transaction List</h4>
                            </div>
                        </div>


                        <div class="card">
                            <div class="card-body">
                                <form id="filterForm" action="InventoryTransaction" method="GET">
                                    <div class="mb-0">
                                        <div class="pb-0">
                                            <div class="row">
                                                <div class="col-lg-12 col-sm-12">
                                                    <div class="row">
                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <select class="select" name="type">
                                                                    <option value="">Choose Type</option>
                                                                    <option value="AUDIT" <c:if
                                                                        test="${param.type == 'AUDIT'}">selected
                                                                        </c:if>>
                                                                        AUDIT
                                                                    </option>
                                                                    <option value="IMPORT" <c:if
                                                                        test="${param.type == 'IMPORT'}">selected
                                                                        </c:if>>
                                                                        IMPORT
                                                                    </option>
                                                                    <option value="EXPORT" <c:if
                                                                        test="${param.type == 'EXPORT'}">selected
                                                                        </c:if>>
                                                                        EXPORT
                                                                    </option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <input type="text" class="form-control" name="searchId"
                                                                    value="${param.searchId}"
                                                                    placeholder="Search ID or Serial"
                                                                    title="Search ID or Serial">
                                                            </div>
                                                        </div>
                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group d-flex align-items-center">
                                                                <label class="mb-0 me-2"
                                                                    style="white-space: nowrap;">From</label>
                                                                <input type="date" class="form-control" name="startDate"
                                                                    value="${param.startDate}" title="Start Date">
                                                                <label class="mb-0 mx-2"
                                                                    style="white-space: nowrap;">To</label>
                                                                <input type="date" class="form-control" name="endDate"
                                                                    value="${param.endDate}" title="End Date">
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

                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Type</th>
                                                    <th>Who Processed</th>
                                                    <th>Time Completed</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>

                                            <tbody>
                                                <c:forEach var="item" items="${transactions}">
                                                    <tr>
                                                        <td>${item.code}</td>
                                                        <td>${item.type}</td>
                                                        <td>${not empty item.processor ? item.processor : 'N/A'}</td>
                                                        <td>${item.date}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${item.type == 'AUDIT'}">
                                                                    <a class="me-3"
                                                                        href="InventoryAuditDetail?id=${item.id}">
                                                                        <img src="assets/img/icons/eye.svg" alt="img">
                                                                    </a>
                                                                </c:when>

                                                                <c:when test="${item.type == 'IMPORT'}">
                                                                    <a class="me-3"
                                                                        href="ImportHistoryDetail?receiptId=${item.id}">
                                                                        <img src="assets/img/icons/eye.svg" alt="img">
                                                                    </a>
                                                                </c:when>

                                                                <c:otherwise>
                                                                    <a class="me-3"
                                                                        href="exportDetail?orderId=${item.id}&from=inventoryTransaction">
                                                                        <img src="assets/img/icons/eye.svg" alt="img">
                                                                    </a>
                                                                </c:otherwise>

                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <jsp:include page="/WEB-INF/common/pagination.jsp" />

                                </form>

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
