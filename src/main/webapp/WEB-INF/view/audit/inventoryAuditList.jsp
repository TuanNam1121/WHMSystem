<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                <title>Inventory Audit List</title>

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
                    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
                    <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

                    <div class="page-wrapper">
                        <div class="content">
                            <div class="page-header">
                                <div class="page-title">
                                    <h4>Inventory Audit List</h4>
                                </div>
                                <c:if
                                    test="${sessionScope.userPermissions.contains('CREATE_INVENTORY_AUDIT') && !hasActiveAudit}">
                                    <div class="page-btn">
                                        <a href="AddInventoryAudit" class="btn btn-added">
                                            <img src="assets/img/icons/plus.svg" alt="img" class="mr-2">Add New Audit
                                        </a>
                                    </div>
                                </c:if>
                            </div>

                            <div class="card">
                                <div class="card-body">
                                    <form id="filterForm" action="InventoryAuditList" method="GET">
                                        <div class="row align-items-center mb-4">
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" class="form-control" name="searchId"
                                                        value="${param.searchId}" placeholder="Search ID or Serial"
                                                        title="Search ID or Serial">
                                                </div>
                                            </div>
                                            <div class="col-lg col-sm-6 col-12">
                                                <div class="form-group d-flex align-items-center">
                                                    <label class="mb-0 me-2" style="white-space: nowrap;">From</label>
                                                    <input type="date" class="form-control" name="startDate"
                                                        value="${param.startDate}" title="Start Date">
                                                    <label class="mb-0 mx-2" style="white-space: nowrap;">To</label>
                                                    <input type="date" class="form-control" name="endDate"
                                                        value="${param.endDate}" title="End Date">
                                                </div>
                                            </div>
                                            <div class="col-lg-1 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-filters ms-auto">
                                                        <img src="assets/img/icons/search-whites.svg" alt="img">
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="table-responsive">
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th>Id</th>
                                                        <th>Created By</th>
                                                        <th>Status</th>
                                                        <th>Created At</th>
                                                        <th>Updated At</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="audit" items="${inventoryAudits}">
                                                        <tr>
                                                            <td>${audit.codeId}</td>
                                                            <td>${audit.creator.fullName}</td>
                                                            <td><span class="badges bg-lightgrey">${audit.status}</span>
                                                            </td>
                                                            <td>${audit.createdAt}</td>
                                                            <td>${audit.updatedAt}</td>
                                                            <td>
                                                                <a class="mr-3"
                                                                    href="InventoryAuditDetail?id=${audit.id}">
                                                                    <img src="assets/img/icons/eye.svg" alt="img">
                                                                </a>
                                                                <c:if
                                                                    test="${sessionScope.userPermissions.contains('PERFORM_INVENTORY_AUDIT') && audit.status == 'SUBMITTED'}">
                                                                    <a class="me-3"
                                                                        href="PerformInventoryAudit?id=${audit.id}">
                                                                        <img src="assets/img/icons/edit.svg" alt="img">
                                                                    </a>
                                                                </c:if>
                                                                <c:if
                                                                    test="${sessionScope.userPermissions.contains('CREATE_INVENTORY_AUDIT') && audit.status == 'DRAFT'}">
                                                                    <a class="me-3"
                                                                        href="EditInventoryAudit?id=${audit.id}">
                                                                        <img src="assets/img/icons/edit.svg" alt="img">
                                                                    </a>
                                                                </c:if>
                                                                <c:if
                                                                    test="${sessionScope.userPermissions.contains('APPROVE_INVENTORY_AUDIT') && audit.status == 'PENDING'}">
                                                                    <a class="me-3"
                                                                        href="ReviewInventoryAudit?id=${audit.id}"
                                                                        title="Review Audit">
                                                                        <img src="assets/img/icons/edit.svg"
                                                                            alt="Review">
                                                                    </a>
                                                                </c:if>
                                                                <c:if
                                                                    test="${sessionScope.userPermissions.contains('CREATE_INVENTORY_AUDIT') && (audit.status == 'DRAFT' || audit.status == 'SUBMITTED')}">
                                                                    <a class="me-3 cancel-audit"
                                                                        href="javascript:void(0);" data-id="${audit.id}"
                                                                        data-status="${audit.status}">
                                                                        <img src="assets/img/icons/delete.svg"
                                                                            alt="img">
                                                                    </a>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <jsp:include page="/WEB-INF/common/pagination.jsp" />
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
                        $(document).on("click", ".cancel-audit", function (e) {
                            e.preventDefault();
                            var auditId = $(this).data("id");
                            var status = $(this).data("status");
                            var confirmMsg = status === "DRAFT"
                                ? "This draft audit request will be deleted permanently."
                                : "This audit request status will be updated to CANCELLED.";

                            Swal.fire({
                                title: "Are you sure?",
                                text: confirmMsg,
                                icon: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#3085d6",
                                cancelButtonColor: "#d33",
                                confirmButtonText: "Yes, cancel it!"
                            }).then(function (result) {
                                if (result.isConfirmed) {
                                    window.location.href = "CancelInventoryAudit?id=" + auditId;
                                }
                            });
                        });
                    });
                </script>
            </body>

            </html>