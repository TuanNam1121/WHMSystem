<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Warehouse Staff Import Request List">
        <meta name="keywords"
              content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Import Request List - WHM System</title>

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
                                <h4>IMPORT REQUEST LIST</h4>
                                <h6>View import requests assigned to you</h6>
                            </div>
                        </div>

                    <c:if test="${not empty message}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>${message}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% session.removeAttribute("message"); %>
                    </c:if>
                    <c:if test="${not empty sessionScope.error}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong>${sessionScope.error}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% session.removeAttribute("error"); %>
                    </c:if>

                    <div class="card">
                        <div class="card-body">
                            <form action="importRequestList" method="get">
                                <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                                    <div class="card-body pb-0">
                                        <div class="row">
                                            <div class="col-lg-3 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="code" placeholder="Enter Request Code"
                                                           value="${param.code}">
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="status">
                                                        <option value="">Choose Status</option>
                                                        <option value="Approved" ${param.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                                        <option value="Processing" ${param.status == 'Processing' ? 'selected' : ''}>Processing</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="date" class="datetimepicker cal-icon"
                                                           placeholder="Choose Date" value="${param.date}">
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-filters ms-auto"
                                                            style="border: none; padding: 0;">
                                                        <img src="assets/img/icons/search-whites.svg" alt="img">
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        <div class="table-responsive">
                            <table class="table" id="import-request-table">
                                <thead>
                                    <tr>
                                        <th>Request Code</th>
                                        <th>Supplier</th>
                                        <th>Note</th>
                                        <th>Status</th>
                                        <th>Created At</th>
                                        <th>Total Item</th>
                                        <th>Total Price</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${importRequests}" var="ir">
                                        <tr>
                                            <td class="text-bolds"><a href="ImportRequestDetail?id=${ir.purchaseRequestId}">
                                                    PR-${ir.purchaseRequestId}</a>
                                            </td>
                                            <td>${ir.supplier}</td>
                                            <td>${not empty ir.note ? ir.note : 'No note provided'}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when
                                                        test="${ir.status == 'APPROVED'}">
                                                        <span class="badges bg-lightyellow">${ir.status}</span>
                                                    </c:when>
                                                    <c:when
                                                        test="${ir.status == 'PROCESSING'}">
                                                        <span class="badges bg-lightgreen">${ir.status}</span>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:formatDate value='${ir.createdAt}'
                                                                pattern='dd MMM yyyy'/>
                                            </td>
                                            <td>${ir.totalItem}</td>
                                            <td><fmt:formatNumber value="${ir.totalPrice}" pattern="#,###"/></td>
                                            <td>
                                                <a class="btn btn-sm btn-outline-primary"
                                                   href="ImportProduct?prId=${ir.purchaseRequestId}">
                                                    <i class="fas fa-eye me-1"></i>Import
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <jsp:include page="/WEB-INF/common/pagination.jsp"/>
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
