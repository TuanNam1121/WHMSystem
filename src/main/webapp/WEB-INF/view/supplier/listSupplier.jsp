<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Supplier List">
    <meta name="keywords" content="admin, bootstrap, business, corporate, pos, supplier">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Supplier List - WHM System</title>

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

<jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>
<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

<div class="main-wrapper">

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>SUPPLIER LIST</h4>
                    <h6>Manage your suppliers</h6>
                </div>
                <div class="page-btn">
                    <c:if test="${sessionScope.userPermissions.contains('CREATE_SUPPLIER')}">
                        <a href="createSupplier" class="btn btn-added" id="btn-create-supplier">
                            <img src="assets/img/icons/plus.svg" alt="img">Add Supplier
                        </a>
                    </c:if>
                </div>
            </div>

            <c:if test="${not empty sessionScope.message}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>${sessionScope.message}</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"
                            aria-label="Close"></button>
                </div>
                <% session.removeAttribute("message"); %>
            </c:if>
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>${sessionScope.error}</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"
                            aria-label="Close"></button>
                </div>
                <% session.removeAttribute("error"); %>
            </c:if>

            <div class="card">
                <div class="card-body">
                    <form action="listSupplier" method="get">
                        <div class="card" id="filter_inputs" style="display: block;">
                            <div class="card-body pb-0">
                                <div class="row">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <input type="text" placeholder="Enter Supplier Code" id="filter-code" name="code" value="${param.code}">
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-sm-6 col-12">
                                        <div class="form-group">
                                            <input type="text" placeholder="Enter Supplier Name" id="filter-name" name="name" value="${param.name}">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <select class="select" id="filter-active" name="active">
                                                <option value="">Choose Status</option>
                                                <option value="1" ${param.active == '1' ? 'selected' : ''}>Active</option>
                                                <option value="0" ${param.active == '0' ? 'selected' : ''}>Inactive</option>
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
                        <table class="table custom-datanew" id="supplier-table">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Supplier Name</th>
                                <th>Phone</th>
                                <th>Email</th>
                                <th>Address</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${supplierList}" var="sup">
                                <tr>
                                    <td class="text-bolds">
                                        <fmt:formatNumber value="${sup.supplierId}" pattern="000"/>
                                    </td>
                                    <td><a href="createPurchaseRequest?supplierId=${sup.supplierId}">${sup.supplierName}</a></td>
                                    <td>${sup.phone}</td>
                                    <td>${sup.email}</td>
                                    <td style="max-width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"
                                        title="<c:out value='${sup.address}'/>">${sup.address}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sup.active}">
                                                <span class="badges bg-lightgreen">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badges bg-lightred">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a class="me-3" href="updateSupplier?id=${sup.supplierId}"
                                           id="btn-update-sup${sup.supplierId}">
                                            <img src="assets/img/icons/edit.svg" alt="img">
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

</script>
</body>

</html>
