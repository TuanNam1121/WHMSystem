<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title><c:out value="${reportTitle}"/> - WHM System</title>
    <link rel="shortcut icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/assets/img/favicon.jpg">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .report-summary-card {
            align-items: center;
            background: #fff;
            border: 1px solid #e8ebed;
            border-radius: 8px;
            display: flex;
            gap: 14px;
            height: 100%;
            padding: 20px;
        }

        .report-summary-icon {
            align-items: center;
            background: #fff4e8;
            border-radius: 8px;
            color: #ff9f43;
            display: flex;
            font-size: 23px;
            height: 52px;
            justify-content: center;
            width: 52px;
        }

        .report-summary-card p { color: #637381; margin: 0 0 4px; }
        .report-summary-card h4 { font-size: 24px; margin: 0; }
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

        .specialized-report-table thead th {
            background: #ff9f43;
            border: 1px solid #e8e8e8;
            color: #fff;
            font-size: 13px;
            font-weight: 600;
            text-align: center;
            vertical-align: middle;
            white-space: nowrap;
        }

        .specialized-report-table tbody td {
            border: 1px solid #e8e8e8;
            font-size: 13px;
            text-align: center;
            vertical-align: middle;
        }

        .specialized-report-table tbody td:nth-child(3) { text-align: left; }
        .specialized-report-table tbody tr:hover { background: #fff5ec; }
        .filter-error { color: #ea5455; font-size: 13px; margin-bottom: 15px; }
    </style>
</head>
<body>
<div id="global-loader"><div class="whirly-loader"></div></div>
<div class="main-wrapper">
    <jsp:include page="/WEB-INF/common/header.jsp"/>
    <jsp:include page="/WEB-INF/common/sidebar.jsp"/>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4><c:out value="${reportTitle}"/></h4>
                    <h6><c:out value="${reportSubtitle}"/></h6>
                </div>
                <c:url var="excelUrl" value="/${excelPath}">
                    <c:param name="keyword" value="${param.keyword}"/>
                    <c:param name="fromDate" value="${param.fromDate}"/>
                    <c:param name="toDate" value="${param.toDate}"/>
                </c:url>
                <div class="page-btn">
                    <a href="${excelUrl}" class="btn btn-export">
                        <i class="fas fa-file-excel me-1"></i> Export Excel
                    </a>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-lg-4 col-sm-6 col-12 mb-3">
                    <div class="report-summary-card">
                        <div class="report-summary-icon">
                            <c:choose>
                                <c:when test="${reportType == 'import'}"><i class="fas fa-arrow-down"></i></c:when>
                                <c:when test="${reportType == 'export'}"><i class="fas fa-arrow-up"></i></c:when>
                                <c:otherwise><i class="fas fa-boxes"></i></c:otherwise>
                            </c:choose>
                        </div>
                        <div>
                                    <p>Total Opening Stock</p>
                                    <h4><fmt:formatNumber value="${totalOpeningStock}" pattern="#,##0"/></h4>
                        </div>
                    </div>
                </div>
                    <div class="col-lg-4 col-sm-6 col-12 mb-3">
                        <div class="report-summary-card">
                            <div class="report-summary-icon"><i class="fas fa-box-open"></i></div>
                            <div>
                                <p>Total Closing Stock</p>
                                <h4><fmt:formatNumber value="${totalClosingStock}" pattern="#,##0"/></h4>
                            </div>
                        </div>
                    </div>
                <div class="col-lg-4 col-sm-6 col-12 mb-3">
                    <div class="report-summary-card">
                        <div class="report-summary-icon"><i class="fas fa-list"></i></div>
                        <div>
                            <p>Products in Report</p>
                            <h4><fmt:formatNumber value="${totalRecords}" pattern="#,##0"/></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <form id="specialized-report-form"
                          action="${pageContext.request.contextPath}/${reportPath}" method="get">
                        <input type="hidden" name="sortColumn" id="sortColumn"
                               value="${fn:escapeXml(param.sortColumn)}">
                        <input type="hidden" name="sortOrder" id="sortOrder"
                               value="${fn:escapeXml(param.sortOrder)}">
                        <div class="card mb-3" id="filter_inputs" style="display:block !important;">
                            <div class="card-body pb-0">
                                <c:if test="${not empty filterError}">
                                    <div class="filter-error">
                                        <i class="fas fa-exclamation-circle me-1"></i>
                                        <c:out value="${filterError}"/>
                                    </div>
                                </c:if>
                                <div class="row align-items-end">
                                    <c:if test="${showDateFilter}">
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>From Date</label>
                                                <div class="input-groupicon">
                                                    <input type="text" name="fromDate"
                                                           value="${fn:escapeXml(param.fromDate)}"
                                                           placeholder="DD-MM-YYYY" class="datetimepicker">
                                                    <div class="addonset">
                                                        <img src="${pageContext.request.contextPath}/assets/img/icons/calendars.svg"
                                                             alt="calendar">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>To Date</label>
                                                <div class="input-groupicon">
                                                    <input type="text" name="toDate"
                                                           value="${fn:escapeXml(param.toDate)}"
                                                           placeholder="DD-MM-YYYY" class="datetimepicker">
                                                    <div class="addonset">
                                                        <img src="${pageContext.request.contextPath}/assets/img/icons/calendars.svg"
                                                             alt="calendar">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="col-lg-4 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Search</label>
                                            <input type="text" name="keyword"
                                                   value="${fn:escapeXml(param.keyword)}"
                                                   placeholder="SKU or product name...">
                                        </div>
                                    </div>
                                    <div class="col-lg-1 col-sm-6 col-12 ms-lg-auto">
                                        <div class="form-group d-flex justify-content-lg-end">
                                            <button type="submit" class="btn btn-filters" aria-label="Search">
                                                <img src="${pageContext.request.contextPath}/assets/img/icons/search-whites.svg"
                                                     alt="search">
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="table-responsive" id="specialized-report-table" tabindex="-1">
                            <table class="table specialized-report-table">
                                <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>SKU</th>
                                    <th>Product Name</th>
                                    <th>Category</th>
                                    <th>Unit</th>
                                            <th style="cursor:pointer" onclick="toggleQuantitySort('openingStock')">
                                                Opening Stock
                                                <i class="fas fa-sort${param.sortColumn == 'openingStock' ? (param.sortOrder == 'asc' ? '-up' : '-down') : ''}"></i>
                                            </th>
                                            <th style="cursor:pointer" onclick="toggleQuantitySort('closingStock')">
                                                Closing Stock
                                                <i class="fas fa-sort${param.sortColumn == 'closingStock' ? (param.sortOrder == 'asc' ? '-up' : '-down') : ''}"></i>
                                            </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${reportList}" var="item" varStatus="status">
                                    <tr>
                                        <td>${(page - 1) * pageSize + status.index + 1}</td>
                                        <td><c:out value="${item.sku}"/></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/inventorySummaryDetail?productId=${item.productId}&fromDate=${param.fromDate}&toDate=${param.toDate}&source=stockReport"
                                               class="text-dark fw-bold"
                                               style="text-decoration: none;"
                                               title="View import/export transactions history">
                                                <c:out value="${item.productName}"/>
                                            </a>
                                        </td>
                                        <td><c:out value="${item.category}"/></td>
                                        <td><c:out value="${item.unit}"/></td>
                                        <td><fmt:formatNumber value="${item.openingStock}" pattern="#,##0"/></td>
                                        <td><fmt:formatNumber value="${item.closingStock}" pattern="#,##0"/></td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty reportList}">
                                    <tr><td colspan="7"
                                            class="text-center py-4 text-muted">No data to display</td></tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                        <jsp:include page="/WEB-INF/common/pagination.jsp"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
<script>
    function toggleQuantitySort(column) {
        const sortColumn = document.getElementById('sortColumn');
        const sortOrder = document.getElementById('sortOrder');
        if (sortColumn.value === column) {
            sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
        } else {
            sortColumn.value = column;
            sortOrder.value = 'asc';
        }
        document.getElementById('specialized-report-form').submit();
    }

    document.getElementById('specialized-report-form').addEventListener('submit', function (event) {
        const fromInput = this.querySelector('input[name="fromDate"]');
        const toInput = this.querySelector('input[name="toDate"]');
        if (!fromInput || !toInput || !fromInput.value || !toInput.value) return;
        const parseDate = value => {
            const parts = value.split('-');
            return parts.length === 3 ? new Date(parts[2], parts[1] - 1, parts[0]) : null;
        };
        const from = parseDate(fromInput.value);
        const to = parseDate(toInput.value);
        if (from && to && from > to) {
            event.preventDefault();
            Swal.fire({
                title: 'Invalid Date Range!',
                text: 'From Date cannot be after To Date.',
                icon: 'error',
                type: 'error',
                confirmButtonClass: 'btn btn-primary'
            });
        }
    });
</script>
<c:if test="${focusTable}">
    <script>
        window.addEventListener('load', function () {
            const table = document.getElementById('specialized-report-table');
            table.scrollIntoView({behavior: 'smooth', block: 'start'});
            table.focus({preventScroll: true});
        });
    </script>
</c:if>
</body>
</html>
