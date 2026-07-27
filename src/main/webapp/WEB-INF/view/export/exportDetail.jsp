<%--
  Created by IntelliJ IDEA.
  User: tung
  Date: 12/6/26
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>Export Detail - WHM System</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css?v=export-scan-scroll">
</head>
<body>
<div id="global-loader">
    <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">

    <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Export Details</h4>
                    <h6>View details of export receipt.</h6>
                </div>
                <div class="page-btn">
                    <c:choose>
                        <c:when test="${param.from == 'inventorySummaryDetail'}">
                            <c:url var="backToTransactionHistoryUrl" value="/inventorySummaryDetail">
                                <c:param name="productId" value="${param.productId}"/>
                                <c:param name="fromDate" value="${param.fromDate}"/>
                                <c:param name="toDate" value="${param.toDate}"/>
                            </c:url>
                            <a href="${backToTransactionHistoryUrl}" class="btn btn-cancel">
                                <i class="fas fa-arrow-left me-2"></i>Back to Transaction History
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/exportHistory" class="btn btn-cancel">
                                <i class="fas fa-arrow-left me-2"></i>Back to List
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger alert-dismissible fade show mt-3" role="alert">
                    <strong><i class="fas fa-exclamation-triangle"></i> Error:</strong> ${sessionScope.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="error" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                    <strong>${sessionScope.successMessage}</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>

            <div class="card">
                <div class="card-body">
                    <fmt:formatDate value="${exportReceiptInfo.orderCreatedAt}" pattern="dd-MM-yyyy HH:mm:ss"
                                    var="orderCreatedAtText"/>
                    <c:set var="receiptCode" value="ER-${exportReceiptInfo.receiptId}"/>
                    <c:if test="${not empty exportReceiptInfo.receiptCode}">
                        <c:set var="receiptCode" value="${exportReceiptInfo.receiptCode}"/>
                    </c:if>
                    <c:set var="orderCode" value="SO-${exportReceiptInfo.orderId}"/>
                    <c:if test="${not empty exportReceiptInfo.orderCode}">
                        <c:set var="orderCode" value="${exportReceiptInfo.orderCode}"/>
                    </c:if>
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Export Receipt</label>
                                <input type="text" class="form-control"
                                       value="${receiptCode}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Sale Order</label>
                                <input type="text" class="form-control"
                                       value="${orderCode}" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Order Created At</label>
                                <input type="text" class="form-control"
                                       value="${orderCreatedAtText}"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Sale Created By</label>
                                <c:choose>
                                    <c:when test="${not empty exportReceiptInfo.saleCreatedBy}">
                                        <input type="text" class="form-control"
                                               value="${exportReceiptInfo.saleCreatedBy}"
                                               readonly="readonly">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control"
                                               value="-" readonly="readonly">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Sale Processed By</label>
                                <c:choose>
                                    <c:when test="${not empty exportReceiptInfo.saleProcessedBy}">
                                        <input type="text" class="form-control"
                                               value="${exportReceiptInfo.saleProcessedBy}"
                                               readonly="readonly">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" class="form-control"
                                               value="-" readonly="readonly">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Processor</label>
                                <input type="text" class="form-control" value="${sessionScope.user.fullName}"
                                       readonly="readonly">
                            </div>
                        </div>

                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Customer</label>
                                <input type="text" class="form-control" value="${sessionScope.order.customer}"
                                       readonly="readonly">
                            </div>
                        </div>
                    </div>


                    <form action="submitExport" method="post" id="submitExportForm">
                        <div class="row">
                            <div class="table-responsive export-scan-table-scroll">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Product Name</th>
                                        <th>SKU</th>
                                        <th>S/N</th>
                                        <th>Price</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${sessionScope.itemList}" var="s">
                                        <tr class="bor-b1">
                                            <td class="productimgname">
                                                <a class="product-img">
                                                    <img src="${s.imgUrl}" alt="product">
                                                </a>
                                                <a href="javascript:void(0);">${s.name}</a>
                                            </td>
                                            <td>${s.sku}</td>
                                            <td>${s.serial}</td>
                                            <td>
                                                <fmt:formatNumber value="${s.price}" pattern="#,###"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12 float-md-right">
                                <div class="total-order">
                                    <ul>
                                        <li class="total">
                                            <h4>Grand Total</h4>
                                            <h5><fmt:formatNumber value="${requestScope.grandTotal}"
                                                                  pattern="#,###"/></h5>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Status</label>
                                    <input type="text" class="form-control" value="${sessionScope.order.status}"
                                           readonly="readonly">
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea class="form-control"
                                              readonly="readonly">${sessionScope.order.note}</textarea>
                                </div>
                            </div>

                        </div>
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
<script src="assets/plugins/select2/js/select2.min.js"></script>
<script src="assets/js/moment.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="assets/js/script.js"></script>
</body>
</html>
