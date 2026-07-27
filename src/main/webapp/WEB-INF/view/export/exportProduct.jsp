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
    <title>Export Product - WHM System</title>

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
                    <h4>Export Product</h4>
                    <h6>Export products from Order.</h6>
                </div>
            </div>

            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                    <strong>${sessionScope.successMessage}</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="successMessage" scope="session"/>
            </c:if>

            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Processor</label>
                                <input type="text" class="form-control" value="${sessionScope.user.fullName}"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Customer</label>
                                <input type="text" class="form-control" value="${sessionScope.order.customer}"
                                       readonly="readonly">
                            </div>
                        </div>
                        <hr>
                        <div class="page-title">
                            <h6>Export Items</h6>
                            <br>
                        </div>

                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Product</th>
                                    <th>SKU</th>
                                    <th>Qty</th>
                                    <th>Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${sessionScope.pickingList}" var="p" varStatus="v">
                                    <tr>
                                        <td>${v.index + 1}</td>
                                        <td class="productimgname">
                                            <a href="javascript:void(0);" class="product-img">
                                                <img src="${p.imgUrl}" alt="product">
                                            </a>
                                            <p>${p.name}</p>
                                        </td>
                                        <td>${p.sku}</td>
                                        <td>${p.quantity}</td>
                                        <td>
                                            <fmt:formatNumber value="${p.price}" pattern="#,###"/>
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                        <hr>

                        <form action="exportProduct" method="post" id="scanBarcodeForm">
                            <input type="hidden" name="orderId" value="${sessionScope.order.id}">
                            <c:choose>
                                <c:when test="${not empty sessionScope.error}">
                                    <div id="scanError" class="alert alert-danger"
                                         role="alert">${sessionScope.error}</div>
                                </c:when>
                                <c:otherwise>
                                    <div id="scanError" class="alert alert-danger d-none"
                                         role="alert"></div>
                                </c:otherwise>
                            </c:choose>
                            <c:remove var="error" scope="session"/>
                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Scan Serial Number</label>
                                    <div class="input-groupicon">
                                        <input type="text" name="serial" id="serialInput"
                                               maxlength="100"
                                               placeholder="Scan serial number and wait..." autofocus required>
                                        <div class="addonset">
                                            <img src="assets/img/icons/scanners.svg" alt="img">
                                        </div>
                                    </div>
                                    <button type="submit" id="btnScanSubmit"
                                            style="position: absolute; left: -9999px;"></button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <form action="submitExport" method="post" id="submitExportForm">
                        <input type="hidden" name="orderId" value="${sessionScope.order.id}">
                        <input type="hidden" name="submitAction" id="submitActionInput" value="COMPLETE">
                        <div class="row">
                            <div class="table-responsive export-scan-table-scroll" id="scannedProductTable"
                                 tabindex="-1">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Product Name</th>
                                        <th>S/N</th>
                                        <th>Price</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody id="scannedProductBody">
                                    <c:forEach items="${sessionScope.scannedList}" var="s">
                                        <tr class="bor-b1">
                                            <td class="productimgname">
                                                <a class="product-img">
                                                    <img src="${s.imgUrl}" alt="product">
                                                </a>
                                                <a href="javascript:void(0);">${s.name}</a>
                                            </td>
                                            <td>
                                                <input type="hidden" name="tempIds" value="${s.tempId}">
                                                <input type="hidden" name="sn"
                                                       data-temp-id="${s.tempId}"
                                                       value="${s.serial}">
                                                    ${s.serial}
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${s.price}" pattern="#,###"/>
                                            </td>
                                            <td>
                                                <a href="removeItem?tempId=${s.tempId}&orderId=${sessionScope.order.id}"
                                                   class="delete-set">
                                                    <img src="assets/img/icons/delete.svg" alt="svg">
                                                </a>
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
                                            <h5 id="grandTotal">
                                                <fmt:formatNumber value="${requestScope.grandTotal}"
                                                                  pattern="#,###"/>
                                            </h5>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea class="form-control"
                                              readonly="readonly">${sessionScope.order.note}</textarea>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <button type="submit"
                                        class="btn btn-submit me-2" id="btnSubmitExport">
                                    Submit
                                </button>
                                <a href="cancelExport" class="btn btn-cancel">Back</a>
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
<script>
    const orderId = "${sessionScope.order.id}";
    const scanForm = document.getElementById("scanBarcodeForm");
    const serialInput = document.getElementById("serialInput");
    const scannedTable = document.getElementById("scannedProductTable");
    const scannedBody = document.getElementById("scannedProductBody");
    const scanError = document.getElementById("scanError");
    const grandTotal = document.getElementById("grandTotal");

    function setupDeleteButton(button) {
        button.addEventListener("click", function (event) {
            event.preventDefault();

            const row = button.closest("tr");
            const input = row.querySelector("input[name='sn']");
            const tempId = input.dataset.tempId;
            button.style.pointerEvents = "none";

            const params = new URLSearchParams();
            params.append("tempId", tempId);
            params.append("orderId", orderId);
            params.append("ajax", "true");

            fetch("removeItem?" + params.toString())
                .then(function (response) {
                    return response.json();
                })
                .then(function (data) {
                    if (!data.success) {
                        showScanError(data.message);
                        return;
                    }

                    row.remove();
                    grandTotal.textContent =
                        Number(data.grandTotal).toLocaleString("en-US");
                    scanError.classList.add("d-none");
                    serialInput.focus({preventScroll: true});
                })
                .catch(function () {
                    showScanError("Cannot remove product. Please try again.");
                })
                .finally(function () {
                    button.style.pointerEvents = "";
                });
        });
    }

    document.querySelectorAll(".delete-set").forEach(setupDeleteButton);

    function escapeHtml(value) {
        const div = document.createElement("div");
        if (value == null) {
            value = "";
        }
        div.textContent = value;
        return div.innerHTML;
    }

    function addProductToTable(item) {
        const row = document.createElement("tr");
        row.className = "bor-b1";
        row.innerHTML =
            '<td class="productimgname">' +
            '<a class="product-img"><img src="' + escapeHtml(item.imgUrl) + '" alt="product"></a>' +
            '<a href="javascript:void(0);">' + escapeHtml(item.name) + '</a>' +
            '</td>' +
            '<td>' +
            '<input type="hidden" name="tempIds" value="' + escapeHtml(item.tempId) + '">' +
            '<input type="hidden" name="sn" data-temp-id="' + escapeHtml(item.tempId) + '" ' +
            'value="' + escapeHtml(item.serial) + '">' +
            escapeHtml(item.serial) +
            '</td>' +
            '<td>' + item.qty + '</td>' +
            '<td>' + Number(item.price).toLocaleString("en-US") + '</td>' +
            '<td>' + item.stock + '</td>' +
            '<td>' +
            '<a href="removeItem?tempId=' + encodeURIComponent(item.tempId) +
            '&orderId=' + encodeURIComponent(orderId) + '" class="delete-set">' +
            '<img src="assets/img/icons/delete.svg" alt="svg">' +
            '</a>' +
            '</td>';

        scannedBody.prepend(row);
        setupDeleteButton(row.querySelector(".delete-set"));
    }

    function showScanError(message) {
        scanError.textContent = message;
        scanError.classList.remove("d-none");
        scanForm.scrollIntoView({behavior: "smooth", block: "center"});
    }

    if (!scanError.classList.contains("d-none")) {
        scanForm.scrollIntoView({behavior: "smooth", block: "center"});
        serialInput.focus({preventScroll: true});
    }

    scanForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const serial = serialInput.value.trim();
        if (serial === "") {
            serialInput.focus();
            return;
        }

        const formData = new URLSearchParams();
        formData.append("orderId", orderId);
        formData.append("serial", serial);
        formData.append("ajax", "true");
        serialInput.disabled = true;

        fetch("exportProduct", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: formData.toString()
        })
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                if (!data.success) {
                    showScanError(data.message);
                    serialInput.select();
                    return;
                }

                scanError.classList.add("d-none");
                addProductToTable(data.item);
                grandTotal.textContent = Number(data.grandTotal).toLocaleString("en-US");
                serialInput.value = "";

                scannedTable.scrollIntoView({behavior: "smooth", block: "center"});
                scannedTable.focus({preventScroll: true});

                setTimeout(function () {
                    serialInput.focus({preventScroll: true});
                }, 400);
            })
            .catch(function () {
                showScanError("Cannot add product. Please try again.");
            })
            .finally(function () {
                serialInput.disabled = false;
                serialInput.focus({preventScroll: true});
            });
    });
</script>
</body>
</html>
