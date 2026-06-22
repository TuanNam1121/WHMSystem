<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Manager Purchase Request Detail">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Manager - Purchase Request Detail - Dreams Pos</title>

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
                    <h4>Purchase Request Detail</h4>
                    <h6>Review and approve/reject purchase request</h6>
                </div>
                <div class="page-btn">
                    <a href="managerPurchaseRequestList" class="btn btn-cancel" id="btn-back-to-list">
                        <i class="fas fa-arrow-left me-2"></i>Back to List
                    </a>
                </div>
            </div>

            <!-- Request Info Header -->
            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-2 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Request Code</label>
                                <strong class="form-control" id="detail-request-code"
                                        style="border: none; background-color: #e9ecef; display: flex; align-items: center;">
                                    <fmt:formatNumber value='${purchaseRequest.id}' pattern='000'/>
                                </strong>
                            </div>
                        </div>
                        <div class="col-lg-2 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Status</label>
                                <div>
                                    <c:choose>
                                        <c:when
                                                test="${purchaseRequest.status == 'New' || purchaseRequest.status == 'NEW'}">
                                                            <span class="badges bg-lightyellow"
                                                                  style="font-size: 14px; padding: 6px 16px;">${purchaseRequest.status}</span>
                                        </c:when>
                                        <c:when
                                                test="${purchaseRequest.status == 'Approved' || purchaseRequest.status == 'APPROVED'}">
                                                            <span class="badges bg-lightgreen"
                                                                  style="font-size: 14px; padding: 6px 16px;">${purchaseRequest.status}</span>
                                        </c:when>
                                        <c:when
                                                test="${purchaseRequest.status == 'Rejected' || purchaseRequest.status == 'REJECTED'}">
                                                            <span class="badges bg-lightred"
                                                                  style="font-size: 14px; padding: 6px 16px;">${purchaseRequest.status}</span>
                                        </c:when>
                                        <c:when
                                                test="${purchaseRequest.status == 'Processing' || purchaseRequest.status == 'PROCESSING'}">
                                                            <span class="badges bg-lightpurple"
                                                                  style="font-size: 14px; padding: 6px 16px;">${purchaseRequest.status}</span>
                                        </c:when>
                                        <c:when
                                                test="${purchaseRequest.status == 'Completed' || purchaseRequest.status == 'COMPLETED'}">
                                                            <span class="badges bg-lightgreen"
                                                                  style="font-size: 14px; padding: 6px 16px;">${purchaseRequest.status}</span>
                                        </c:when>
                                        <c:otherwise>
                                                            <span class="badges bg-lightgrey"
                                                                  style="font-size: 14px; padding: 6px 16px;">${purchaseRequest.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Created At</label>
                                <strong class="form-control" id="detail-created-at"
                                        style="border: none; background-color: #e9ecef; display: flex; align-items: center;">
                                    <fmt:formatDate value='${purchaseRequest.createdAt}'
                                                    pattern='dd MMM yyyy - hh:mm a'/>
                                </strong>
                            </div>
                        </div>
                        <div class="col-lg-2 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Salesman</label>
                                <div class="d-flex align-items-center mt-1">
                                    <div>
                                        <strong
                                                id="detail-salesman-name">${salesman.fullName}</strong><br>
                                        <small class="text-muted"
                                               id="detail-salesman-id">${salesman.id}</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="form-group">
                                <label>Supplier</label>
                                <div class="d-flex align-items-center mt-1">
                                    <div>
                                        <strong
                                                id="detail-supplier-name">${purchaseRequest.supplierName}</strong><br>
                                        <small class="text-muted"
                                               id="detail-supplier-id">ID: ${purchaseRequest.supplierId}</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Product List -->
            <div class="card">
                <div class="card-body">
                    <div class="form-group mb-3">
                        <h5
                                style="font-weight: 600; color: #333; border-bottom: 1px solid #eee; padding-bottom: 10px;">
                            <i class="fas fa-box me-2"></i>Product List
                        </h5>
                    </div>

                    <div class="table-responsive">
                        <table class="table" id="detail-product-table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Product Name</th>
                                <th>Category</th>
                                <th>Price</th>
                                <th class="text-center">Quantity Requested</th>
                                <th class="text-center">Current Stock</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchaseItems}" var="item" varStatus="status">
                                <c:set var="product" value="${productMap[item.productId]}"/>
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td class="productimgname">
                                        <a class="product-img">
                                            <img src="${product.imgUrl != null ? product.imgUrl : 'assets/img/product/product7.jpg'}"
                                                 alt="product">
                                        </a>
                                        <a href="javascript:void(0);">${product.name}</a>
                                    </td>
                                    <td>${product.category.name}</td>
                                    <td>
                                        <fmt:formatNumber value="${item.price}" pattern="#,###" var="formattedPrice"/>
                                            ${fn:replace(formattedPrice, ',', '.')}đ
                                    </td>
                                    <td class="text-center"><strong>${item.requiredQty}</strong></td>
                                    <td class="text-center">
                                                            <span
                                                                    class="badges ${product.totalQuantity <= 10 ? 'bg-lightred' : 'bg-lightgreen'}">${product.totalQuantity}
                                                                left</span>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Note from Salesman -->
                    <div class="form-group mt-3">
                        <label><strong><i class="fas fa-sticky-note me-1"></i> Note from
                            Salesman</strong></label>
                        <div class="p-3 mt-1"
                             style="background: #f8f9fa; border-radius: 8px; border-left: 4px solid #FF9F43;">
                            <p class="mb-0" id="detail-note">
                                <c:choose>
                                    <c:when test="${not empty purchaseRequest.note}">
                                        ${purchaseRequest.note}</c:when>
                                    <c:otherwise>No additional note provided.</c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${purchaseRequest.status == 'New' || purchaseRequest.status == 'NEW'}">
                <form id="action-form" action="managerPurchaseRequestDetail" method="POST">
                    <div class="row mt-3">
                        <div class="col-lg-12 d-flex justify-content-between">
                            <a href="managerPurchaseRequestList" class="btn btn-cancel"
                               id="btn-cancel-action" style="padding: 10px 30px;">
                                <i class="fas fa-times me-2"></i>Cancel
                            </a>
                            <div>
                                <button type="submit" name="buttonSubmit" value="Reject"
                                        class="btn btn-danger me-2" id="btn-reject-request"
                                        style="padding: 10px 30px;">
                                    <i class="fas fa-ban me-2"></i>Reject
                                </button>
                                <button type="submit" name="buttonSubmit" value="Accept"
                                        class="btn btn-submit" id="btn-accept-request"
                                        style="padding: 10px 30px; background: #28C76F;">
                                    <i class="fas fa-check me-2"></i>Accept
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </c:if>

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
    $(document).ready(function () {
        // === ACCEPT REQUEST ===
        $('#btn-accept-request').on('click', function (e) {
            e.preventDefault();
            var assignedStaff = $('#assign-warehouse-staff').val();

            if (!assignedStaff || assignedStaff === '') {
                alert('Please assign a Warehouse Staff before approving the request.');
                $('#assign-warehouse-staff').focus();
                return;
            }

            $('#hiddenWarehouseStaffId').val(assignedStaff);
            $('#actionType').val('Accept');
            $('#action-form').submit();
        });

        // === REJECT REQUEST ===
        $('#btn-reject-request').on('click', function (e) {
            e.preventDefault();
            $('#actionType').val('Reject');
            $('#action-form').submit();
        });
    });
</script>
</body>

</html>