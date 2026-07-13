<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <title>Import / Export Detail Report By Day - WHM System</title>
        <meta name="description"
              content="Detailed daily import and export inventory report with charts and statistics">

        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <style>
            /* ===== Summary Cards ===== */
            .day-summary-card {
                border: 1px solid #e8ebed;
                border-radius: 10px;
                padding: 20px 18px;
                background: #ffffff;
                display: flex;
                align-items: center;
                gap: 14px;
                height: 100%;
                transition: box-shadow 0.25s ease, transform 0.2s ease;
            }

            .day-summary-card:hover {
                box-shadow: 0 6px 24px rgba(0, 0, 0, 0.09);
                transform: translateY(-2px);
            }

            .day-summary-icon {
                width: 52px;
                height: 52px;
                border-radius: 8px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 22px;
                flex-shrink: 0;
            }

            .day-summary-icon.icon-green {
                background: #ddf8ec;
                color: #28c76f;
            }

            .day-summary-icon.icon-red {
                background: #ffe5e5;
                color: #ea5455;
            }

            .day-summary-icon.icon-blue {
                background: #ddf0ff;
                color: #00cfe8;
            }

            .day-summary-icon.icon-orange {
                background: #fff4cc;
                color: #ff9f43;
            }

            .day-summary-card p {
                margin-bottom: 3px;
                color: #637381;
                font-size: 13px;
            }

            .day-summary-card h4 {
                margin-bottom: 0;
                font-size: 22px;
                font-weight: 700;
            }

            /* ===== Filter / Search Section ===== */
            .filter-card {
                border: 1px solid #e8ebed;
                border-radius: 10px;
                padding: 18px 20px 10px;
                background: #fff;
                margin-bottom: 20px;
            }

            .filter-card label {
                font-weight: 600;
                font-size: 13px;
                color: #333;
                margin-bottom: 5px;
            }

            /* ===== Chart Section ===== */
            .chart-card {
                border: 1px solid #e8ebed;
                border-radius: 10px;
                padding: 20px;
                background: #fff;
                height: 100%;
            }

            .chart-card-title {
                font-weight: 700;
                font-size: 14px;
                color: #333;
                margin-bottom: 16px;
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .chart-card-title i {
                color: #ff9f43;
            }

            /* ===== Top-5 Ranking Table ===== */
            .ranking-table thead th {
                background-color: #ff9f43;
                color: #fff;
                font-weight: 600;
                font-size: 12px;
                text-align: center;
                vertical-align: middle;
                white-space: nowrap;
                border: 1px solid #e8e8e8;
                padding: 8px 10px;
            }

            .ranking-table tbody td {
                font-size: 12px;
                text-align: center;
                vertical-align: middle;
                border: 1px solid #eee;
                padding: 7px 10px;
            }

            .ranking-table tbody td.td-name {
                text-align: left;
            }

            .ranking-table tbody tr:hover {
                background-color: #fff5ec;
            }

            .rank-badge {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 24px;
                height: 24px;
                border-radius: 50%;
                font-weight: 700;
                font-size: 12px;
                color: #fff;
            }

            .rank-1 {
                background: #f39c12;
            }

            .rank-2 {
                background: #95a5a6;
            }

            .rank-3 {
                background: #cd7f32;
            }

            .rank-4 {
                background: #7367f0;
            }

            .rank-5 {
                background: #00cfe8;
            }

            /* ===== Main Report Table ===== */
            .report-summary-table thead th {
                text-align: center;
                vertical-align: middle;
                background-color: #ff9f43;
                color: #fff;
                font-weight: 600;
                font-size: 13px;
                white-space: nowrap;
                border: 1px solid #e8e8e8;
            }

            .report-summary-table tbody td {
                text-align: center;
                vertical-align: middle;
                font-size: 13px;
                border: 1px solid #e8e8e8;
            }

            .report-summary-table tbody td.td-name {
                text-align: left;
            }

            .report-summary-table tbody tr:hover {
                background-color: #fff5ec;
            }

            .badge-import {
                background-color: #e6f9ee;
                color: #28a745;
                padding: 4px 12px;
                border-radius: 20px;
                font-size: 12px;
                font-weight: 600;
            }

            .badge-export {
                background-color: #fce4e4;
                color: #e74c3c;
                padding: 4px 12px;
                border-radius: 20px;
                font-size: 12px;
                font-weight: 600;
            }

            /* ===== Section headings ===== */
            .section-heading {
                font-size: 15px;
                font-weight: 700;
                color: #333;
                margin-bottom: 14px;
                padding-bottom: 8px;
                border-bottom: 2px solid #ff9f43;
                display: flex;
                align-items: center;
                gap: 8px;
            }

            /* ===== Export / Print buttons ===== */
            .btn-export {
                background-color: #28a745;
                color: #fff;
                border: none;
                padding: 8px 20px;
                border-radius: 5px;
                font-size: 13px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .btn-export:hover {
                background-color: #218838;
                color: #fff;
            }

            .btn-print {
                background-color: #17a2b8;
                color: #fff;
                border: none;
                padding: 8px 20px;
                border-radius: 5px;
                font-size: 13px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .btn-print:hover {
                background-color: #138496;
                color: #fff;
            }

            /* ===== No data placeholder ===== */
            .no-data-placeholder {
                text-align: center;
                padding: 40px 20px;
                color: #aaa;
            }

            .no-data-placeholder i {
                font-size: 40px;
                margin-bottom: 10px;
                display: block;
                color: #ddd;
            }

            /* ===== Sort select styling ===== */
            .sort-select-wrapper {
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .sort-select-wrapper label {
                white-space: nowrap;
                margin-bottom: 0 !important;
            }

            /* Hide native calendar icon to avoid overlapping with template icon */
            input[type="date"]::-webkit-calendar-picker-indicator {
                opacity: 0;
                width: 100%;
                height: 100%;
                position: absolute;
                top: 0;
                left: 0;
                cursor: pointer;
            }
            .input-groupicon {
                position: relative;
            }
        </style>
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
                        <!-- Page Header -->
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Detailed Daily Report</h4>
                                <h6>View detailed inventory stock-in and stock-out transactions by day</h6>
                            </div>
                            <div class="page-btn d-flex gap-2">
                                <a href="${pageContext.request.contextPath}/ImportExportByDay?date=${param.date}&keyword=${param.keyword}&sortBy=${param.sortBy}&sortDir=${param.sortDir}"
                               class="btn btn-export" id="btn-export-excel">
                                <i class="fas fa-file-excel me-1"></i> Export Excel
                            </a>
                            <button onclick="window.print()" class="btn btn-print" id="btn-print-page">
                                <i class="fas fa-print me-1"></i> Print
                            </button>
                        </div>
                    </div>
                    <form action="${pageContext.request.contextPath}/ImportExportByDayReport"
                          method="get" id="filter-form">

                        <!-- Filter Row -->
                        <div class="filter-card">
                            <div class="row align-items-end g-3">

                                <!-- Date picker -->
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group mb-0">
                                        <label for="input-date">
                                            Date
                                        </label>
                                        <div class="input-groupicon">
                                            <input type="date" id="input-date" name="date"
                                                   value="${param.date}" placeholder="DD-MM-YYYY"
                                                   class="form-control" style="position:relative; z-index:1; background:transparent;">
                                            <div class="addonset">
                                                <img src="assets/img/icons/calendars.svg"
                                                     alt="calendar">
                                            </div> 
                                        </div>
                                    </div>
                                </div>

                                <!-- Keyword Search -->
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group mb-0">
                                        <label for="input-keyword">
                                            <i class="fas fa-search me-1" style="color:#ff9f43;"></i>
                                            Search
                                        </label>
                                        <input type="text" id="input-keyword" name="keyword"
                                               value="${param.keyword}" placeholder="Product Name or SKU..."
                                               class="form-control">
                                    </div>
                                </div>

                                <!-- Sort By -->
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group mb-0">
                                        <label for="input-sortby">
                                            <i class="fas fa-sort-amount-down me-1"
                                               style="color:#ff9f43;"></i> Order By
                                        </label>
                                        <select id="input-sortby" name="sortBy" class="select">
                                            <option value="" ${empty param.sortBy ? 'selected' : '' }>--
                                                Default --</option>
                                            <option value="sku" ${'sku'==param.sortBy ? 'selected' : '' }>SKU
                                            </option>
                                            <option value="name" ${'name'==param.sortBy ? 'selected' : '' }>
                                                Product Name</option>
                                            <option value="importQuantity" ${'importQuantity'==param.sortBy ? 'selected'
                                                                             : '' }>Import Quantity</option>
                                            <option value="exportQuantity" ${'exportQuantity'==param.sortBy ? 'selected'
                                                                             : '' }>Export Quantity</option>
                                        </select>
                                    </div>
                                </div>

                                <!-- Sort Direction -->
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group mb-0">
                                        <label for="input-sortdir">
                                            <i class="fas fa-exchange-alt me-1"
                                               style="color:#ff9f43;"></i> Sort By
                                        </label>
                                        <select id="input-sortdir" name="sortDir" class="select">
                                            <option value="asc" ${'asc'==param.sortDir ? 'selected' : '' }>Ascending</option>
                                            <option value="desc" ${'desc'==param.sortDir || empty param.sortDir ? 'selected' : '' }>
                                                Descending</option>
                                        </select>
                                    </div>
                                </div>

                                <!-- Submit -->
                                <div class="col-lg-1 col-sm-6 col-12">
                                    <div class="form-group mb-0">
                                        <label>&nbsp;</label>
                                        <button type="submit" class="btn btn-filters ms-auto"
                                                id="btn-search" style="border: none; padding: 0;">
                                            <img src="assets/img/icons/search-whites.svg" alt="img">
                                        </button>
                                    </div>
                                </div>

                                <!-- Reset -->
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group mb-0">
                                        <label>&nbsp;</label>
                                        <a href="${pageContext.request.contextPath}/ImportExportByDayReport"
                                           class="btn btn-secondary w-100" id="btn-reset"
                                           style="font-size:13px; padding:8px 12px;">
                                            <i class="fas fa-redo me-1"></i> Làm mới
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </div>


                        <!-- ===== SUMMARY CARDS ===== -->
                        <div class="row mb-3">
                            <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                <div class="day-summary-card">
                                    <div class="day-summary-icon icon-green">
                                        <i class="fas fa-arrow-circle-down"></i>
                                    </div>
                                    <div>
                                        <p>Total Import Quantity</p>
                                        <h4 id="total-import-qty">
                                            ${totalImportQty}
                                        </h4>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                <div class="day-summary-card">
                                    <div class="day-summary-icon icon-red">
                                        <i class="fas fa-arrow-circle-up"></i>
                                    </div>
                                    <div>
                                        <p>Total Export Quantity</p>
                                        <h4 id="total-export-qty">
                                            ${totalExportQty}
                                        </h4>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                <div class="day-summary-card">
                                    <div class="day-summary-icon icon-blue">
                                        <i class="fas fa-coins"></i>
                                    </div>
                                    <div>
                                        <p>Total Import Value</p>
                                        <h4 id="total-import-value">
                                            <fmt:formatNumber value="${totalImportValue}" pattern="#,###"/>
                                            <small style="font-size:13px; font-weight:500; color:#637381;">đ</small>
                                        </h4>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-3 col-sm-6 col-12 mb-3">
                                <div class="day-summary-card">
                                    <div class="day-summary-icon icon-orange">
                                        <i class="fas fa-hand-holding-usd"></i>
                                    </div>
                                    <div>
                                        <p>Total Export Value</p>
                                        <h4 id="total-export-value">
                                             <fmt:formatNumber value="${totalExportValue}" pattern="#,###"/>
                                            <small style="font-size:13px; font-weight:500; color:#637381;">đ</small>
                                        </h4>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- ===== CHARTS + RANKING ===== -->
                        <div class="row mb-3">
                            <!-- Pie Chart: Số lượng giao dịch -->
                            <div class="col-lg-5 col-sm-12 mb-3">
                                <div class="chart-card">
                                    <div class="chart-card-title">
                                        <i class="fas fa-chart-pie"></i>
                                        Daily Transaction Quantity Distribution
                                    </div>
                                    <div
                                        style="position: relative; height: 260px; display:flex; align-items:center; justify-content:center;">
                                        <canvas id="transactionPieChart"></canvas>
                                    </div>
                                    <div class="d-flex justify-content-center gap-3 mt-3" style="font-size:12px;">
                                        <span>
                                            <span style="display:inline-block;width:12px;height:12px;border-radius:50%;background:#28c76f;margin-right:4px;"></span> Import
                                            <span>
                                                <span style="display:inline-block;width:12px;height:12px;border-radius:50%;background:#ea5455;margin-right:4px;"></span> Export
                                            </span>
                                            <span>
                                                <span style="display:inline-block;width:12px;height:12px;border-radius:50%;background:#ff9f43;margin-right:4px;"></span> Adjust
                                            </span>
                                    </div>
                                </div>
                            </div>



                            <!-- ===== FILTER + DETAIL TABLE ===== -->
                            <div class="card">
                                <div class="card-body">
                                    <!-- Detail Table -->
                                    <div class="section-heading mt-2">
                                        <i class="fas fa-table" style="color:#ff9f43;"></i>
                                        Inventory Stock Movement Details
                                        <c:if test="${not empty param.date}">
                                            <span style="font-size:13px; color:#ff9f43; margin-left:8px;">
                                                — Date ${param.date}
                                            </span>
                                        </c:if>
                                    </div>

                                    <div class="table-responsive" id="import-export-day-table" tabindex="-1">
                                        <table class="table report-summary-table">
                                            <thead>
                                                <tr>
                                                    <th>TT</th>
                                                    <th>SKU</th>
                                                    <th>Product Name</th>
                                                    <th>Unit</th>
                                                    <th>Date</th>
                                                    <th>
                                                        <span style="color:#d4edda;">
                                                            <i class="fas fa-arrow-down me-1"></i>
                                                        </span>
                                                        Import Quantity
                                                    </th>
                                                    <th>
                                                        <span style="color:#f8d7da;">
                                                            <i class="fas fa-arrow-up me-1"></i>
                                                        </span>
                                                        Export Quantity
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty list}">
                                                        <c:forEach items="${list}" var="item" varStatus="v">
                                                            <tr>
                                                                <td>${(page - 1) * pageSize + v.index + 1}</td>
                                                                <td>
                                                                    <span
                                                                        style="font-family: monospace; font-size:12px; background:#f3f4f6; padding:2px 6px; border-radius:4px;">
                                                                        ${item.sku}
                                                                    </span>
                                                                </td>
                                                                <td class="td-name"><a href="${pageContext.request.contextPath}/ConvertDetailReport?productId=${item.productId}&fromDate=${param.date}&toDate=${param.date}"
                                                                                       class="text-dark fw-bold"
                                                                                       style="text-decoration: none;"
                                                                                       title="View import/export transactions history">
                                                                        ${item.productName}</a>
                                                                </td>
                                                                <td>${item.unit}</td>
                                                                <td>
                                                                    <fmt:formatDate value="${item.date}"
                                                                                    pattern="dd/MM/yyyy" />
                                                                </td>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${item.totalImport > 0}">
                                                                            <strong style="color:#28a745;">
                                                                                +
                                                                                <fmt:formatNumber
                                                                                    value="${item.totalImport}"
                                                                                    pattern="#,##0" />
                                                                            </strong>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span style="color:#ccc;">—</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${item.totalExport > 0}">
                                                                            <strong style="color:#ea5455;">
                                                                                -
                                                                                <fmt:formatNumber
                                                                                    value="${item.totalExport}"
                                                                                    pattern="#,##0" />
                                                                            </strong>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span style="color:#ccc;">—</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="7">
                                                                <div class="no-data-placeholder">
                                                                    <i class="fas fa-inbox"></i>
                                                                    <p class="mb-0">Không có dữ liệu để hiển thị</p>
                                                                    <small>Hãy chọn ngày hoặc thay đổi bộ lọc để xem
                                                                        dữ liệu</small>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>

                                            <!-- Table Footer Totals -->
                                            <c:if test="${not empty list}">
                                                <tfoot>
                                                    <tr
                                                        style="background-color:#f8f9fa; font-weight:700; font-size:13px;">
                                                        <td colspan="5"
                                                            style="text-align:right; padding-right:15px;">
                                                            Total:
                                                        </td>
                                                        <td style="color:#28a745;">
                                                            +
                                                            <fmt:formatNumber
                                                                value="${totalImportQty != null ? totalImportQty : 0}"
                                                                pattern="#,##0" />
                                                        </td>
                                                        <td style="color:#ea5455;">
                                                            -
                                                            <fmt:formatNumber
                                                                value="${totalExportQty != null ? totalExportQty : 0}"
                                                                pattern="#,##0" />
                                                        </td>
                                                    </tr>
                                                </tfoot>
                                            </c:if>
                                        </table>
                                    </div>

                                    <!-- Pagination -->
                                    <jsp:include page="/WEB-INF/common/pagination.jsp" />

                                </div>
                            </div>
                    </form>

                </div>
            </div>
        </div>

        <!-- Scripts -->
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
        <script>
                                /* Load Chart.js from CDN; if unavailable the pie section degrades gracefully */
                                (function () {
                                    var s = document.createElement('script');
                                    s.src = 'https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js';
                                    s.onerror = function () {
                                        // CDN failed – mark Chart as unavailable so the init block can handle it
                                        window.__chartJsFailed = true;
                                        var canvas = document.getElementById('transactionPieChart');
                                        if (canvas) {
                                            canvas.parentNode.innerHTML =
                                                    '<div style="text-align:center;color:#aaa;padding:40px 0;">' +
                                                    '<i class="fas fa-exclamation-circle" style="font-size:36px;color:#f39c12;display:block;margin-bottom:8px;"></i>' +
                                                    'Không thể tải biểu đồ (kiểm tra kết nối mạng)' +
                                                    '</div>';
                                        }
                                    };
                                    document.head.appendChild(s);
                                })();
        </script>
        <script src="assets/js/script.js"></script>

        <!-- Pie Chart Init – runs AFTER Chart.js is loaded -->
        <script>
                                function initPieChart() {
                                    if (window.__chartJsFailed)
                                        return; // CDN failed, already handled

                                    // Data from server (fallback to 0 if not provided by servlet yet)
                                    var importQty = parseInt('${totalImportQty != null ? totalImportQty : 0}') || 0;
                                    var exportQty = parseInt('${totalExportQty != null ? totalExportQty : 0}') || 0;
                                    var adjustQty = parseInt('${totalAdjustQty != null ? totalAdjustQty : 0}') || 0;

                                    var total = importQty + exportQty + adjustQty;
                                    var canvas = document.getElementById('transactionPieChart');
                                    if (!canvas)
                                        return;

                                    if (total === 0) {
                                        canvas.parentNode.innerHTML =
                                                '<div style="text-align:center;color:#aaa;padding:40px 0;">' +
                                                '<i class="fas fa-chart-pie" style="font-size:40px;color:#eee;display:block;margin-bottom:10px;"></i>' +
                                                'Chưa có dữ liệu để hiển thị biểu đồ' +
                                                '</div>';
                                        return;
                                    }

                                    var ctx = canvas.getContext('2d');
                                    new Chart(ctx, {
                                        type: 'doughnut',
                                        data: {
                                            labels: ['Nhập kho', 'Xuất kho', 'Điều chỉnh'],
                                            datasets: [{
                                                    data: [importQty, exportQty, adjustQty],
                                                    backgroundColor: ['#28c76f', '#ea5455', '#ff9f43'],
                                                    borderColor: ['#fff', '#fff', '#fff'],
                                                    borderWidth: 3,
                                                    hoverOffset: 8
                                                }]
                                        },
                                        options: {
                                            responsive: true,
                                            maintainAspectRatio: false,
                                            cutout: '65%',
                                            plugins: {
                                                legend: {display: false},
                                                tooltip: {
                                                    callbacks: {
                                                        label: function (context) {
                                                            var label = context.label || '';
                                                            var value = context.parsed;
                                                            var pct = total > 0 ? ((value / total) * 100).toFixed(1) : 0;
                                                            return ' ' + label + ': ' + value.toLocaleString() + ' (' + pct + '%)';
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }

                                // Hook onto the dynamic <script> tag created above for Chart.js
                                (function waitForChartJs() {
                                    var scripts = document.head.querySelectorAll('script[src*="chart.js"]');
                                    if (scripts.length === 0) {
                                        // Script tag not yet appended; try again shortly
                                        setTimeout(waitForChartJs, 50);
                                        return;
                                    }
                                    var tag = scripts[scripts.length - 1];
                                    if (tag.readyState === 'loaded' || tag.readyState === 'complete') {
                                        initPieChart();
                                    } else {
                                        tag.onload = initPieChart;
                                        tag.onreadystatechange = function () {
                                            if (this.readyState === 'loaded' || this.readyState === 'complete')
                                                initPieChart();
                                        };
                                    }
                                })();
        </script>
    </body>

</html>
