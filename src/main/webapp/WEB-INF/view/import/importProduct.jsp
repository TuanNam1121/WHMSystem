<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
                <meta name="description" content="POS - Import Products">
                <meta name="keywords"
                    content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
                <meta name="author" content="Dreamguys - Bootstrap Admin Template">
                <meta name="robots" content="noindex, nofollow">
                <title>Import Products - Dreams Pos</title>

                <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
                <link rel="stylesheet" href="assets/css/bootstrap.min.css">
                <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
                <link rel="stylesheet" href="assets/css/animate.css">
                <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
                <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
                <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
                <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
                <link rel="stylesheet" href="assets/css/style.css">
                <link rel="stylesheet" href="assets/css/importProduct.css">
            </head>

            <body>
                <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
                <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">

                        <!-- Page Header -->
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Import Products</h4>
                                <h6>Fill in serial numbers and pricing for imported products</h6>
                            </div>
                            <div class="page-btn">
                                <a href="warehouse-import-request-list.html" class="btn btn-cancel"
                                    id="btn-back-to-list">
                                    <i class="fas fa-arrow-left me-2"></i>Back to List
                                </a>
                            </div>
                        </div>

                        <!-- Main Card -->
                        <form action="ImportProduct" method="POST" id="import-form">
                            <input type="hidden" name="goodReceiptId" value="${requestScope.goodReceiptId}">
                            <div class="card">
                                <div class="card-body">

                                    <!-- Info Row: Purchase Request, Approved At, Handled By, Created By -->
                                    <div class="row mb-3" id="import-info-row">
                                        <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                            <div class="import-info-box">
                                                <span class="info-box-label">Purchase Request</span>
                                                <div class="info-box-value" id="info-purchase-request">
                                                    PR-${sessionScope.prCode}</div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                            <div class="import-info-box">
                                                <span class="info-box-label">Approved At</span>
                                                <div class="info-box-value" id="info-approved-at">
                                                    <fmt:formatDate value="${sessionScope.approvedAt}"
                                                        pattern="dd/MM/yyyy HH:mm" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                            <div class="import-info-box">
                                                <span class="info-box-label">Handled By</span>
                                                <div class="info-box-value" id="info-handled-by">${sessionScope.handler
                                                    !=
                                                    null ? sessionScope.handler : 'Not assigned'}</div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                            <div class="import-info-box">
                                                <span class="info-box-label">Created By</span>
                                                <div class="info-box-value" id="info-created-by">${sessionScope.creator
                                                    !=
                                                    null ? sessionScope.creator : 'Not assigned'}</div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Supplier -->
                                    <div class="supplier-field">
                                        <label>Supplier</label>
                                        <input type="text" value="FPT Supplier HCM" id="import-supplier"
                                            class="form-control" name="supplierName"
                                            style="border: 1px solid #dee2e6; border-radius: 6px;">
                                    </div>

                                    <div class="supplier-field">
                                        <label>Invoice Number</label>
                                        <input type="text" value="AC123" id="import-supplier-invoice"
                                            class="form-control" name="invoiceNumber"
                                            style="border: 1px solid #dee2e6; border-radius: 6px;">
                                    </div>


                                    <!-- Summary Row: Total Items, Serials Filled, Total Payment -->
                                    <div class="row mb-4" id="import-summary-row">
                                        <div class="col-lg-4 col-md-4 col-sm-6 col-12 mb-3">
                                            <div class="summary-box">
                                                <span class="summary-label">Total Items</span>
                                                <div class="summary-value" id="summary-total-items">
                                                    ${sessionScope.totalItem}</div>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-6 col-12 mb-3">
                                            <div class="summary-box">
                                                <span class="summary-label">Serials Filled</span>
                                                <div class="summary-value" id="summary-serials-filled">0</div>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-md-4 col-sm-6 col-12 mb-3">
                                            <div class="summary-box">
                                                <span class="summary-label">Total Payment</span>
                                                <div class="summary-value" id="summary-total-payment">0 đ</div>
                                            </div>
                                        </div>
                                    </div>


                                    <!-- Product Groups -->
                                    <div id="product-groups-container">
                                        <c:forEach var="group" items="${sessionScope.list}" varStatus="status">
                                            <c:set var="groupIndex" value="${status.index + 1}" />
                                            <div class="product-group" id="product-group-${groupIndex}">
                                                <div class="product-group-header"
                                                    onclick="toggleProductGroup(${groupIndex})">
                                                    <div class="toggle-icon ${groupIndex == 1 ? 'open' : ''}"
                                                        id="toggle-icon-${groupIndex}">
                                                        <i class="fas ${groupIndex == 1 ? 'fa-minus' : 'fa-plus'}"></i>
                                                    </div>
                                                    <div>
                                                        <span class="product-name">${group[0].productName}</span>
                                                        <span class="serial-count"
                                                            id="serial-count-${groupIndex}">0/${group.size()} serials
                                                            filled</span>
                                                    </div>
                                                    <div class="product-meta">
                                                        <span class="product-qty">${group.size()}</span>
                                                        <span class="product-unit">${group[0].unit}</span>
                                                        <div class="product-price">
                                                             <input type="text" value="${group[0].importedPrice}"
                                                                 id="price-product-${groupIndex}"
                                                                 name="price"
                                                                 onchange="recalcTotal(); syncGroupPrices(${groupIndex})">
                                                         </div>
                                                        <span class="product-currency">VND</span>
                                                    </div>
                                                </div>
                                                <div class="product-group-body" id="product-body-${groupIndex}"
                                                    style="${groupIndex == 1 ? '' : 'display: none;'}">
                                                    <div class="serial-rows-container">
                                                        <c:forEach var="row" items="${group}" varStatus="rowStatus">
                                                            <div class="serial-row">
                                                                <input type="hidden" name="productId" value="${row.productId}">
                                                                <span class="serial-index">${rowStatus.index + 1}</span>
                                                                <div class="serial-input">
                                                                    <input type="text" value="${row.serial}"
                                                                        placeholder="Enter serial / IMEI"
                                                                        name="serial"
                                                                        class="serial-field" data-group="${groupIndex}">
                                                                </div>
                                                                <span class="serial-qty">1</span>
                                                                <span class="serial-unit">${row.unit}</span>
                                                                <div class="serial-price">
                                                                    <input type="text" value="${row.importedPrice}"
                                                                        name="itemPrice"
                                                                        class="item-price-field"
                                                                        data-group="${groupIndex}"
                                                                        readonly>
                                                                </div>
                                                                <span class="serial-currency">VND</span>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <!-- Action Buttons -->
                                    <div class="import-actions">
                                        <a href="warehouse-import-request-list.html" class="btn btn-cancel-import"
                                            id="btn-cancel-import">
                                            Cancel
                                        </a>
                                        <a href="javascript:void(0);" class="btn btn-save-draft" id="btn-save-draft">
                                            Save Draft
                                        </a>
                                        <button type="button" class="btn btn-save-import" id="btn-save-import">
                                            Save Import
                                        </button>
                                    </div>
                        </form>
                    </div>
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

                <script>
                    // ===== Toggle Product Group (Expand/Collapse) =====
                    function toggleProductGroup(groupId) {
                        var body = document.getElementById('product-body-' + groupId);
                        var icon = document.getElementById('toggle-icon-' + groupId);

                        if (body.style.display === 'none') {
                            body.style.display = 'block';
                            icon.classList.add('open');
                            icon.innerHTML = '<i class="fas fa-minus"></i>';
                        } else {
                            body.style.display = 'none';
                            icon.classList.remove('open');
                            icon.innerHTML = '<i class="fas fa-plus"></i>';
                        }
                    }

                    // ===== Update serial filled counts =====
                    function updateSerialCounts() {
                        var groups = document.querySelectorAll('.product-group');
                        var totalFilled = 0;

                        groups.forEach(function (group, index) {
                            var groupId = index + 1;
                            var serialFields = group.querySelectorAll('.serial-field');
                            var filled = 0;
                            serialFields.forEach(function (field) {
                                if (field.value.trim() !== '') {
                                    filled++;
                                }
                            });

                            var total = serialFields.length;
                            var countEl = document.getElementById('serial-count-' + groupId);
                            if (countEl) {
                                countEl.textContent = filled + '/' + total + ' serials filled';
                            }
                            totalFilled += filled;
                        });

                        document.getElementById('summary-serials-filled').textContent = totalFilled;
                    }

                    // ===== Recalculate Total Payment =====
                    function recalcTotal() {
                        var total = 0;
                        var groups = document.querySelectorAll('.product-group');
                        groups.forEach(function (group) {
                            var header = group.querySelector('.product-group-header');
                            var priceInput = header.querySelector('.product-price input');
                            var qtySpan = header.querySelector('.product-qty');

                            if (priceInput && qtySpan) {
                                var price = parseInt(priceInput.value.replace(/[^0-9]/g, '')) || 0;
                                var qty = parseInt(qtySpan.textContent.replace(/[^0-9]/g, '')) || 0;
                                total += price * qty;
                            }
                        });
                        var totalPaymentEl = document.getElementById('summary-total-payment');
                        if (totalPaymentEl) {
                            totalPaymentEl.textContent = total.toLocaleString('vi-VN') + ' đ';
                        }
                    }

                    // ===== Sync item prices inside a group when header price changes =====
                    function syncGroupPrices(groupId) {
                        var headerPriceInput = document.getElementById('price-product-' + groupId);
                        if (!headerPriceInput) return;
                        var newPrice = headerPriceInput.value;
                        var group = document.getElementById('product-group-' + groupId);
                        if (!group) return;
                        group.querySelectorAll('.item-price-field[data-group="' + groupId + '"]').forEach(function (input) {
                            input.value = newPrice;
                        });
                    }

                    $(document).ready(function () {
                        // Initial calculations on load
                        updateSerialCounts();
                        recalcTotal();

                        // Listen for serial field changes
                        $(document).on('input', '.serial-field', function () {
                            updateSerialCounts();
                        });

                        // Prevent clicks on inputs inside header from toggling
                        $('.product-group-header input').on('click', function (e) {
                            e.stopPropagation();
                        });

                        // ===== Save Draft =====
                        $('#btn-save-draft').on('click', function () {
                            Swal.fire({
                                title: 'Save as Draft?',
                                html: '<p>Your progress will be saved. You can continue filling in serials later.</p>',
                                icon: 'question',
                                showCancelButton: true,
                                confirmButtonColor: '#EA5455',
                                cancelButtonColor: '#6c757d',
                                confirmButtonText: '<i class="fas fa-save me-1"></i> Save Draft',
                                cancelButtonText: 'Cancel'
                            }).then(function (result) {
                                if (result.isConfirmed) {
                                    Swal.fire({
                                        icon: 'success',
                                        title: 'Draft Saved!',
                                        html: '<p>Your import draft has been saved successfully.</p>',
                                        confirmButtonColor: '#FF9F43'
                                    });
                                }
                            });
                        });

                        // ===== Save Import =====
                        $('#btn-save-import').on('click', function () {
                            // Check if all serials are filled
                            var allFilled = true;
                            var emptyCount = 0;
                            $('.serial-field').each(function () {
                                if ($(this).val().trim() === '') {
                                    allFilled = false;
                                    emptyCount++;
                                }
                            });

                            if (!allFilled) {
                                Swal.fire({
                                    title: 'Incomplete Serials',
                                    html: '<p>There are <strong>' + emptyCount + '</strong> serial fields that are still empty.</p><p>Do you want to continue saving the import?</p>',
                                    icon: 'warning',
                                    showCancelButton: true,
                                    confirmButtonColor: '#28C76F',
                                    cancelButtonColor: '#6c757d',
                                    confirmButtonText: '<i class="fas fa-check me-1"></i> Yes, Save Import',
                                    cancelButtonText: 'Cancel'
                                }).then(function (result) {
                                    if (result.isConfirmed) {
                                        completeImport();
                                    }
                                });
                            } else {
                                Swal.fire({
                                    title: 'Save Import?',
                                    html: '<div style="text-align:left; line-height:1.8;">' +
                                        '<p>You are about to <strong style="color:#28C76F;">save</strong> this import.</p>' +
                                        '<p><strong>Purchase Request:</strong> PR-${sessionScope.prCode}</p>' +
                                        '<p><strong>Supplier:</strong> ' + $('#import-supplier').val() + '</p>' +
                                        '<p><strong>Total Payment:</strong> ' + $('#summary-total-payment').text() + '</p>' +
                                        '<hr>' +
                                        '<p class="text-muted">Products will be added to inventory and stock will be updated.</p>' +
                                        '</div>',
                                    icon: 'question',
                                    showCancelButton: true,
                                    confirmButtonColor: '#28C76F',
                                    cancelButtonColor: '#6c757d',
                                    confirmButtonText: '<i class="fas fa-check-double me-1"></i> Yes, Save Import!',
                                    cancelButtonText: 'Cancel'
                                }).then(function (result) {
                                    if (result.isConfirmed) {
                                        completeImport();
                                    }
                                });
                            }
                        });

                        function completeImport() {
                            // Submit the form to trigger doPost
                            document.getElementById('import-form').submit();
                        }
                    });
                </script>
            </body>

            </html>