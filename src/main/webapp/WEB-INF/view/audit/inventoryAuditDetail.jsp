<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>Inventory Audit Details</title>

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
    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Inventory Audit Details</h4>
                    <h6>Review details for audit request #${audit.id}</h6>
                </div>
                <div class="page-btn">
                    <a href="InventoryAuditList" class="btn btn-added">
                        <img src="assets/img/icons/return1.svg" alt="img" class="me-2">Back to List
                    </a>
                </div>
            </div>


            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label class="font-weight-bold" style="font-size: 14px; color: #555;">Auditor</label>
                                <div class="form-control-static"
                                     style="font-size: 16px; font-weight: 600; padding: 8px 0;">
                                    ${audit.userFullName}
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label class="font-weight-bold" style="font-size: 14px; color: #555;">Status</label>
                                <div style="padding: 8px 0;">
                                    <span class="badges bg-lightgrey" style="font-size: 13px; padding: 6px 12px;">
                                        ${audit.status}
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label class="font-weight-bold" style="font-size: 14px; color: #555;">Created At</label>
                                <div class="form-control-static" style="font-size: 16px; padding: 8px 0;">
                                    ${audit.createdAt}
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label class="font-weight-bold" style="font-size: 14px; color: #555;">Last
                                    Updated</label>
                                <div class="form-control-static" style="font-size: 16px; padding: 8px 0;">
                                    ${audit.updatedAt}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="card mt-4">
                <div class="card-body">
                    <div class="card-title">
                        <h5>Audited Products</h5>
                    </div>
                    <div class="table-responsive">
                        <table class="table datanew">
                            <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>SKU</th>
                                <th>Category</th>
                                <th class="text-end">System Qty</th>
                                <th class="text-end">Physical Qty</th>
                                <th class="text-end">Discrepancy</th>
                                <th>Reason</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${audit.inventoryAuditItems}">
                                <tr>
                                    <td><strong>${item.productName}</strong></td>
                                    <td><span class="text-secondary font-weight-bold">${item.productSku}</span></td>
                                    <td>${item.categoryName}</td>
                                    <td class="text-end font-weight-bold">${item.systemQuantity}</td>
                                    <td class="text-end font-weight-bold">${item.physicalQuantity}</td>
                                    <td class="text-end font-weight-bold">
                                        <c:set var="disc" value="${item.physicalQuantity - item.systemQuantity}"/>
                                        <c:choose>
                                            <c:when test="${disc > 0}">
                                                <span class="text-success">+${disc}</span>
                                            </c:when>
                                            <c:when test="${disc < 0}">
                                                <span class="text-danger">${disc}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-secondary">0</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.reason}">
                                                ${item.reason}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted font-italic">No reason provided</span>
                                            </c:otherwise>
                                        </c:choose>
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
<script src="assets/plugins/select2/js/select2.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="assets/js/script.js"></script>
</body>
</html>
