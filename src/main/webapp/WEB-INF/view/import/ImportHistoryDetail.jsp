<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Import History Detail">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Import History Detail - WHM System</title>

        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

        <link rel="stylesheet" href="assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="assets/css/animate.css">

        <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="assets/css/style.css">

        <style>
            .info-card {
                box-shadow: none;
                border: 1px solid #e9ecef;
                border-radius: 8px;
                background-color: #fafbfe;
            }
            .info-card .card-body {
                padding: 15px;
            }
            .info-label {
                color: #888;
                font-size: 13px;
                margin-bottom: 5px;
                display: block;
            }
            .info-value {
                margin: 0;
                font-weight: 700;
                color: #333;
                font-size: 16px;
            }
            .status-badge {
                color: #1a9f53;
                background-color: #e5f8ed;
                padding: 6px 15px;
                border-radius: 4px;
                font-weight: 700;
                font-size: 13px;
                letter-spacing: 0.5px;
            }
            .table-container {
                border: 1px solid #e9ecef;
                border-radius: 8px;
                overflow: hidden;
                margin-top: 20px;
            }
            .table-container table {
                margin-bottom: 0;
            }
            .table-container thead th {
                background-color: #fafbfe;
                color: #6c757d;
                font-weight: 600;
                border-bottom: 1px solid #e9ecef;
                padding: 15px;
            }
            .table-container tbody td {
                padding: 15px;
                vertical-align: middle;
                border-bottom: 1px solid #f5f5f5;
            }
            .table-container tbody tr:last-child td {
                border-bottom: none;
            }
        </style>
    </head>
    <body>

        <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header d-flex justify-content-between align-items-center border-bottom pb-3 mb-4">
                        <div class="page-title mb-0">
                            <h4 class="mb-0" style="font-size: 22px; font-weight: 700; color: #333;">Import History Detail</h4>
                        </div>
                        <div class="page-btn">
                        <c:choose>
                            <c:when test="${param.from == 'inventorySummaryDetail'}">
                                <a href="${pageContext.request.contextPath}/inventorySummaryDetail?productId=${param.productId}&fromDate=${param.fromDate}&toDate=${param.toDate}" class="btn btn-cancel" id="btn-back-to-list">
                                    <i class="fas fa-arrow-left me-2"></i>Back to Transaction History
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="ImportHistory" class="btn btn-cancel" id="btn-back-to-list">
                                    <i class="fas fa-arrow-left me-2"></i>Back to List
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <c:if test="${not empty message}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <strong>${message}</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% session.removeAttribute("message"); %>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <strong>${error}</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% session.removeAttribute("error"); %>
                </c:if>

                <div class="row mb-4">
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                        <div class="card info-card h-100">
                            <div class="card-body">
                                <span class="info-label">Receipt</span>
                                <h5 class="info-value">GR-${detail.receiptId}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                        <div class="card info-card h-100">
                            <div class="card-body">
                                <span class="info-label">Purchase Request</span>
                                <h5 class="info-value">PR-${detail.purchaseRequestId}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                        <div class="card info-card h-100">
                            <div class="card-body">
                                <span class="info-label">Supplier</span>
                                <h5 class="info-value">${detail.supplier}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                        <div class="card info-card h-100">
                            <div class="card-body">
                                <span class="info-label">Processed By</span>
                                <h5 class="info-value">${detail.importBy}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                        <div class="card info-card h-100">
                            <div class="card-body">
                                <span class="info-label">Received At</span>
                                <h5 class="info-value">${detail.completedAt}</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <form action="ImportHistoryDetail" method="get">
                    <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                        <div class="card-body pb-0">
                            <div class="row">
                                <div class="col-lg-12 col-sm-12">
                                    <div class="row">
                                        <input type="hidden" name="receiptId" value="${detail.receiptId}">
                                        <div class="col-lg col-sm-6 col-12">
                                            <div class="form-group">
                                                <input type="text" name="keyword" placeholder="Search Product Name or Serial" value="${param.keyword}">
                                            </div>
                                        </div>

                                        <div class="col-lg col-sm-6 col-12">
                                            <div class="form-group">
                                                <select class="select" name="sortBy">
                                                    <option value="">Sort</option>                                                        
                                                    <option value="productName" ${param.sortBy == 'productName' ? 'selected' : ''}>Product Name</option>
                                                    <option value="Serial/IMEI" ${param.sortBy == 'Serial/IMEI' ? 'selected' : ''}>Serial/IMEI</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-lg-1 col-sm-6 col-12">
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
                        </div>
                    </div>

                    <div class="table-container bg-white">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Serial / IMEI</th>
                                        <th>Unit</th>
                                        <th>Imported Price</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${list}" var="i">
                                        <tr>
                                            <td>${i.productName}</td>
                                            <td>${i.serial}</td>
                                            <td>${i.unit}</td>
                                            <td><fmt:formatNumber value="${i.importedPrice}" pattern="#,###"/> VND</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <jsp:include page="/WEB-INF/common/pagination.jsp"/>
                </form>

            </div>

        </div>
    </div>


    <script src="assets/js/jquery-3.6.0.min.js"></script>
    <script src="assets/js/feather.min.js"></script>
    <script src="assets/js/jquery.slimscroll.min.js"></script>
    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/plugins/select2/js/select2.min.js"></script>
    <script src="assets/js/moment.min.js"></script>
    <script src="assets/js/bootstrap-datetimepicker.min.js"></script>
    <script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
    <script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
    <script src="assets/js/script.js"></script>

</body>
</html>
