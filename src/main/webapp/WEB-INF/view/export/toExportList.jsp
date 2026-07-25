<%--
  Created by IntelliJ IDEA.
  User: tung
  Date: 8/6/26
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
    <title>To Export List - WHM System</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">

    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">

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
                    <h4>To Export List</h4>
                    <h6>Orders need to be exported.</h6>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <form action="toExportList" method="get">
                        <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-12 col-sm-12">
                                        <div class="row">

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" name="keyword" value="${param.keyword}"
                                                           placeholder="Search order code or customer...">
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <div class="input-groupicon">
                                                        <input type="text" name="fromDate" value="${param.fromDate}"
                                                               placeholder="From Date" class="datetimepicker">
                                                        <div class="addonset">
                                                            <img src="assets/img/icons/calendars.svg" alt="calendar">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <div class="input-groupicon">
                                                        <input type="text" name="toDate" value="${param.toDate}"
                                                               placeholder="To Date" class="datetimepicker">
                                                        <div class="addonset">
                                                            <img src="assets/img/icons/calendars.svg" alt="calendar">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="status">
                                                        <option value="">Choose Status</option>
                                                        <option value="NEW" ${param.status == 'NEW' ? 'selected' : ''}>
                                                            New
                                                        </option>
                                                        <option value="DRAFT" ${param.status == 'DRAFT' ? 'selected' : ''}>
                                                            Draft
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="sortBy">
                                                        <option value="">Sort By</option>
                                                        <option value="dateNewest" ${param.sortBy == 'dateNewest' ? 'selected' : ''}>
                                                            Date: Newest
                                                        </option>
                                                        <option value="dateOldest" ${param.sortBy == 'dateOldest' ? 'selected' : ''}>
                                                            Date: Oldest
                                                        </option>
                                                        <option value="totalLow" ${param.sortBy == 'totalLow' ? 'selected' : ''}>
                                                            Total: Low to high
                                                        </option>
                                                        <option value="totalHigh" ${param.sortBy == 'totalHigh' ? 'selected' : ''}>
                                                            Total: High to low
                                                        </option>
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

                        <div class="table-responsive" id="to-export-table" tabindex="-1">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Code</th>
                                    <th>Date</th>
                                    <th>Customer</th>
                                    <th>Created By</th>
                                    <th>Items</th>
                                    <th>Grand total</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${sessionScope.orderList}" var="o" varStatus="v">
                                    <tr>
                                        <td>${v.index +1}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/OrderDetail?id=${o.id}&action=view">
                                                <c:choose>
                                                    <c:when test="${not empty o.code}">
                                                        ${o.code}
                                                    </c:when>
                                                    <c:otherwise>SO-${o.id}</c:otherwise>
                                                </c:choose>
                                            </a>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${o.orderDate}" pattern="dd-MM-yyyy HH:mm:ss"/>
                                        </td>
                                        <td>${o.customer}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty o.creater}">
                                                    ${o.creater}
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${o.totalQuantity}</td>
                                        <td>
                                            <fmt:formatNumber value="${o.totalPrice}" pattern="#,###"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${o.status == 'COMPLETED'}">
                                                    <span class="badges bg-lightgreen">Completed</span>
                                                </c:when>
                                                <c:when test="${o.status == 'NEW'}">
                                                    <span class="badges bg-lightyellow">New</span>
                                                </c:when>
                                                <c:when test="${o.status == 'DRAFT'}">
                                                    <span class="badges bg-lightyellow">Draft</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badges bg-lightgrey">${o.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <c:if test="${sessionScope.userPermissions.contains('PROCESS_EXPORT') && (o.status == 'NEW' || o.status == 'DRAFT')}">
                                                    <a href="exportProduct?orderId=${o.id}">
                                                        <button type="button" class="btn btn-primary btn-sm">Process
                                                        </button>
                                                    </a>
                                                </c:if>
                                            </div>
                                        </td>
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
<c:if test="${focusTable}">
    <script>
        window.addEventListener("load", function () {
            const table = document.getElementById("to-export-table");
            table.scrollIntoView({behavior: "smooth", block: "start"});
            table.focus({preventScroll: true});
        });
    </script>
</c:if>
</body>
</html>
