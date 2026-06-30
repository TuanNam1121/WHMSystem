<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Manager Purchase Request List">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Manager - Purchase Request List - Dreams Pos</title>

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
                    <h4>PURCHASE REQUEST LIST</h4>
                    <h6>View and manage all purchase requests from Salesmen</h6>
                </div>
            </div>

            <c:if test="${not empty sessionScope.message}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong>${sessionScope.message}</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("message"); %>
            </c:if>
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong>${sessionScope.error}</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("error"); %>
            </c:if>

            <div class="card">
                <div class="card-body">

                    <form action="managerPurchaseRequestList" method="get">
                        <div class="card" id="filter_inputs" style="display: block;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <input type="text" placeholder="Enter Request Code" id="filter-code" name="code" value="${param.code}">
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <select class="select" id="filter-status" name="status">
                                                <option value="">Choose Status</option>
                                                <option value="New" ${param.status == 'New' ? 'selected' : ''}>New</option>
                                                <option value="Approved" ${param.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                                <option value="Rejected" ${param.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                <option value="Processing" ${param.status == 'Processing' ? 'selected' : ''}>Processing</option>
                                                <option value="Completed" ${param.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <input type="text" class="datetimepicker cal-icon" placeholder="Choose Date"
                                                   id="filter-date" name="date" value="${param.date}">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <select class="select" id="filter-sort" name="sort">
                                                <option value="">Sort By</option>
                                                <option value="id_desc" ${param.sort == 'id_desc' ? 'selected' : ''}>ID (Desc)</option>
                                                <option value="id_asc" ${param.sort == 'id_asc' ? 'selected' : ''}>ID (Asc)</option>
                                                <option value="status_desc" ${param.sort == 'status_desc' ? 'selected' : ''}>Status (Desc)</option>
                                                <option value="status_asc" ${param.sort == 'status_asc' ? 'selected' : ''}>Status (Asc)</option>
                                                <option value="date_desc" ${param.sort == 'date_desc' ? 'selected' : ''}>Date (Desc)</option>
                                                <option value="date_asc" ${param.sort == 'date_asc' ? 'selected' : ''}>Date (Asc)</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group d-flex justify-content-end">
                                            <button type="submit" class="btn btn-filters ms-auto" id="btn-apply-filter" style="border: none;">
                                                <img src="assets/img/icons/search-whites.svg" alt="img">
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    <div class="table-responsive">
                        <table class="table custom-datanew" id="manager-purchase-request-table">
                            <thead>
                            <tr>
                                <th>Request Code</th>
                                <th>Created By</th>
                                <th>Supplier</th>
                                <th>Status</th>
                                <th>Created At</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchaseList}" var="pr">
                                <tr>
                                    <td class="text-bolds"><fmt:formatNumber value="${pr.id}" pattern="PR-"/></td>
                                    <td>${pr.createdByUsername}</td>
                                    <td>${pr.supplierName}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pr.status == 'New' || pr.status == 'NEW'}">
                                                <span class="badges bg-lightyellow" style="min-width: 110px; display: inline-block; text-align: center;">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Approved' || pr.status == 'APPROVED'}">
                                                <span class="badges bg-blue" style="min-width: 110px; display: inline-block; text-align: center;">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Rejected' || pr.status == 'REJECTED'}">
                                                <span class="badges bg-lightred" style="min-width: 110px; display: inline-block; text-align: center;">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Processing' || pr.status == 'PROCESSING'}">
                                                <span class="badges bg-lightpurple" style="min-width: 110px; display: inline-block; text-align: center;">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Completed' || pr.status == 'COMPLETED'}">
                                                <span class="badges bg-lightgreen" style="min-width: 110px; display: inline-block; text-align: center;">${pr.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badges bg-lightgrey" style="min-width: 110px; display: inline-block; text-align: center;">${pr.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><fmt:formatDate value="${pr.createdAt}" pattern="dd MMM yyyy"/></td>
                                    <td>
                                        <a href="managerPurchaseRequestDetail?id=${pr.id}" id="btn-view-pr${pr.id}" title="View Detail" class="me-2" style="font-size: 1.2rem; color: #202b36;">
                                            <i class="fas fa-eye"></i>
                                        </a>
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
<script>
    $(document).ready(function () {
        // DataTables removed for server-side pagination
    });
</script>
</body>
</html>
