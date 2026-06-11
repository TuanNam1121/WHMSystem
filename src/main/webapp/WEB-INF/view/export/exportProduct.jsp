<%--
  Created by IntelliJ IDEA.
  User: tung
  Date: 9/6/26
  Time: 13:13
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
    <title>Export Product</title>

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

    <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Export Product</h4>
                    <h6>Export products from Order.</h6>
                </div>
            </div>
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger alert-dismissible fade show mt-3" role="alert">
                    <strong><i class="fas fa-exclamation-triangle"></i> Error:</strong> ${sessionScope.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>

                <c:remove var="error" scope="session"/>
            </c:if>
            <div class="card">
                <div class="card-body">
                    <div class="row">
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
                        <form action="exportProduct" method="post">
                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Scan/Search Product (SKU)</label>
                                    <div class="input-groupicon">
                                        <input type="text" name="sku" placeholder="Scan barcode and wait..."
                                               autofocus required>
                                        <div class="addonset">
                                            <img src="assets/img/icons/scanners.svg" alt="img">
                                        </div>
                                    </div>

                                    <button type="submit" style="display:none;"></button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="table-responsive ">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>S/N</th>
                                    <th>QTY</th>
                                    <th>Price</th>
                                    <th>Stock</th>
                                    <th>Total Cost ($)</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${sessionScope.scannedList}" var="s">
                                    <tr class="bor-b1">
                                        <td class="productimgname">
                                            <a class="product-img">
                                                <img src="${s.imgUrl}" alt="product">
                                            </a>
                                            <a href="javascript:void(0);">${s.name}</a>
                                        </td>
                                        <td>
                                            <input type="text" name="sn" class="form-control">
                                        </td>
                                        <td>${s.qty}</td>
                                        <td>
                                            <fmt:formatNumber value="${s.price}" pattern="#,###"/>
                                        </td>
                                        <td>${s.stock}</td>
                                        <td>
                                            <fmt:formatNumber value="${s.totalCost}" pattern="#,###"/>
                                        </td>
                                        <td>
                                            <a href="removeItem?tempId=${s.tempId}" class="delete-set"><img
                                                    src="assets/img/icons/delete.svg" alt="svg"></a>
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
                                        <h5>$ <fmt:formatNumber value="${grandTotal}" pattern="#,##0.00"/></h5>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Status</label>
                                <select class="select">
                                    <option>Choose Status</option>
                                    <option>Completed</option>
                                    <option>Doing</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label>Description</label>
                                <textarea class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <a href="javascript:void(0);" class="btn btn-submit me-2">Submit</a>
                            <a href="cancelExport" class="btn btn-cancel">Cancel</a>
                        </div>
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

<script src="assets/js/moment.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.min.js"></script>

<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

<script src="assets/js/script.js"></script>
<script>
    $(document).ready(function () {
        const skuInput = $('input[name="scannedSku"]');

        // 1. Luôn ép con trỏ chuột nhảy vào ô quét mã sau mỗi lần tải trang
        skuInput.focus();

        // 2. Bắt sự kiện khi app quét (hoặc người dùng) gửi phím Enter (mã 13)
        skuInput.on('keypress', function (e) {
            if (e.which === 13) {
                e.preventDefault(); // Chặn hành vi mặc định
                $(this).closest('form').submit(); // Ép form gửi dữ liệu về Servlet
            }
        });

        // 3. Tuyệt chiêu: Bắt luôn sự kiện Paste (Dán)
        // Vì phần mềm Barcode to PC đang dùng lệnh Dán, đôi khi nó dán xong quên Enter.
        skuInput.on('paste', function () {
            setTimeout(function () {
                // Đợi 0.1 giây để chữ dán kịp hiện vào ô, rồi tự động nhấn Submit
                if (skuInput.val().trim() !== '') {
                    skuInput.closest('form').submit();
                }
            }, 100);
        });
    });
</script>
</body>
</html>
