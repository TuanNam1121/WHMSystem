<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Purchase Request List for Salesman">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Purchase Request List - Dreams Pos</title>

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
                    <h6>Manage your purchase requests</h6>
                </div>
                <div class="page-btn">
                    <a href="createPurchaseRequest" class="btn btn-added" id="btn-create-request">
                        <img src="assets/img/icons/plus.svg" alt="img">Create Request
                    </a>
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
                    <div class="table-top">
                        <div class="search-set">
                            <div class="search-path">
                                <a class="btn btn-filter" id="filter_search">
                                    <img src="assets/img/icons/filter.svg" alt="img">
                                    <span><img src="assets/img/icons/closes.svg" alt="img"></span>
                                </a>
                            </div>
                            <div class="search-input">
                                <a class="btn btn-searchset"><img src="assets/img/icons/search-white.svg" alt="img"></a>
                            </div>
                        </div>
                        <div class="wordset">
                            <ul>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img
                                            src="assets/img/icons/pdf.svg" alt="img"></a>
                                </li>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img
                                            src="assets/img/icons/excel.svg" alt="img"></a>
                                </li>
                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img
                                            src="assets/img/icons/printer.svg" alt="img"></a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="card" id="filter_inputs">
                        <div class="card-body pb-0">
                            <div class="row">
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <input type="text" placeholder="Enter Request Code" id="filter-code">
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <select class="select" id="filter-status">
                                            <option>Choose Status</option>
                                            <option>New</option>
                                            <option>Approved</option>
                                            <option>Rejected</option>
                                            <option>Processing</option>
                                            <option>Completed</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <input type="text" class="datetimepicker cal-icon" placeholder="Choose Date"
                                               id="filter-date">
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <a class="btn btn-filters ms-auto" id="btn-apply-filter"><img
                                                src="assets/img/icons/search-whites.svg" alt="img"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table datanew" id="purchase-request-table">
                            <thead>
                            <tr>
                                <th>Request Code</th>
                                <th>Note</th>
                                <th>Status</th>
                                <th>Created At</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchaseList}" var="pr">
                                <tr>
                                    <td class="text-bolds"><fmt:formatNumber value="${pr.id}" pattern="000"/></td>
                                    <td style="max-width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" title="<c:out value='${pr.note}'/>">${pr.note}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pr.status == 'New' || pr.status == 'NEW'}">
                                                <span class="badges bg-lightyellow">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Approved' || pr.status == 'APPROVED'}">
                                                <span class="badges bg-lightgreen">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Rejected' || pr.status == 'REJECTED'}">
                                                <span class="badges bg-lightred">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Processing' || pr.status == 'PROCESSING'}">
                                                <span class="badges bg-lightpurple">${pr.status}</span>
                                            </c:when>
                                            <c:when test="${pr.status == 'Completed' || pr.status == 'COMPLETED'}">
                                                <span class="badges bg-lightgreen">${pr.status}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badges bg-lightgrey">${pr.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><fmt:formatDate value="${pr.createdAt}" pattern="dd MMM yyyy"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pr.status == 'New' || pr.status == 'NEW'}">
                                                <a class="me-3" href="updatePurchaseRequest?id=${pr.id}" id="btn-update-pr${pr.id}">
                                                    <img src="assets/img/icons/edit.svg" alt="img">
                                                </a>
                                                <form action="purchaseRequestList" method="POST" style="display:inline;">
                                                    <input type="hidden" name="id" value="${pr.id}">
                                                    <button type="submit" class="me-3 btn-delete-submit" style="border:none; background:none; padding:0;" id="btn-delete-pr${pr.id}">
                                                        <img src="assets/img/icons/delete.svg" alt="img">
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="me-3" href="detailPurchaseRequest?id=${pr.id}"
                                                   title="View Detail - ${pr.status}" id="btn-view-pr${pr.id}">
                                                    <img src="assets/img/icons/eye.svg" alt="img">
                                                </a>
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

<script src="assets/js/moment.min.js"></script>
<script src="assets/js/bootstrap-datetimepicker.min.js"></script>

<script src="assets/plugins/select2/js/select2.min.js"></script>

<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

<script src="assets/js/script.js"></script>
<script>
    $(document).ready(function() {
        $(document).on('click', '.btn-delete-submit', function(e) {
            e.preventDefault();
            var form = $(this).closest('form');
            Swal.fire({
                title: "Are you sure?",
                text: "You won't be able to revert this!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, delete it!"
            }).then(function(result) {
                if (result.value) {
                    form.submit();
                }
            });
        });
    });
</script>
</body>
</html>
