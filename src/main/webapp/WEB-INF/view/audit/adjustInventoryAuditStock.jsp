<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>Adjust Inventory Audit Stock</title>

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
                    <h4>Adjust Stock </h4>
                </div>
            </div>

            <c:if test="${not empty message}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Error:</strong> ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"
                            aria-label="Close"></button>
                </div>
            </c:if>

            <form id="adjustStockForm" action="AdjustInventoryAuditStock" method="POST">
                <input type="hidden" name="id" value="${audit.id}"/>
                <div class="card mt-4">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>SKU</th>
                                    <th class="text-end">System Qty</th>
                                    <th class="text-end">Physical Qty</th>
                                    <th class="text-center">Difference</th>
                                    <th>Action</th>
                                    <th style="width: 30%;">Enter Serials (one per line)</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${discrepancyItems}">
                                    <c:set var="diff"
                                           value="${item.physicalQuantity - item.systemQuantity}"/>
                                    <tr>
                                        <td><strong>${item.productName}</strong></td>
                                        <td><span
                                                class="text-secondary font-weight-bold">${item.productSku}</span>
                                        </td>
                                        <td class="text-end">${item.systemQuantity}</td>
                                        <td class="text-end">${item.physicalQuantity}</td>
                                        <td class="text-center font-weight-bold">
                                            <c:choose>
                                                <c:when test="${diff > 0}"><span
                                                        class="text-success">+${diff}</span>
                                                </c:when>
                                                <c:when test="${diff < 0}"><span
                                                        class="text-danger">${diff}</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${diff > 0}"><span
                                                        class="badge bg-success">Need to ADD
                                                        ${diff}</span></c:when>
                                                <c:when test="${diff < 0}"><span
                                                        class="badge bg-danger">Need to DELETE
                                                        ${diff * -1}</span></c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                                                    <textarea name="serials_${item.id}"
                                                                              class="form-control" rows="3" required
                                                                              placeholder="Enter serials here..."></textarea>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="row mt-4">
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-submit me-2">
                                    Submit Serials
                                </button>
                                <a href="InventoryAuditList" class="btn btn-cancel">Cancel</a>
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