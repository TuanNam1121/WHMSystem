<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Warehouse Staff Import Request Detail">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Import Request Detail - Dreams Pos</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <style>
        .status-timeline {
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 15px 0;
            gap: 0;
        }

        .status-step {
            text-align: center;
            position: relative;
            flex: 1;
            max-width: 160px;
        }

        .status-step .step-circle {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: #e9ecef;
            color: #999;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 16px;
            margin-bottom: 6px;
            border: 3px solid #dee2e6;
            transition: all 0.3s;
        }

        .status-step.active .step-circle {
            background: #FF9F43;
            color: #fff;
            border-color: #FF9F43;
            box-shadow: 0 0 0 4px rgba(255, 159, 67, 0.2);
        }

        .status-step.done .step-circle {
            background: #28C76F;
            color: #fff;
            border-color: #28C76F;
        }

        .status-step .step-label {
            font-size: 13px;
            color: #999;
            font-weight: 500;
        }

        .status-step.active .step-label,
        .status-step.done .step-label {
            color: #333;
            font-weight: 600;
        }

        .status-connector {
            width: 60px;
            height: 3px;
            background: #dee2e6;
            margin-bottom: 22px;
        }

        .status-connector.done {
            background: #28C76F;
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
            <div class="page-header">
                <div class="page-title">
                    <h4>Import Request Detail</h4>
                    <h6>View and process import request</h6>
                </div>

                <div class="page-btn">
                    <a href="importRequestList" class="btn btn-cancel" id="btn-back-to-list">
                        <i class="fas fa-arrow-left me-2"></i>Back to List
                    </a>
                </div>
            </div>

            <form action="importRequestDetail" method="post">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Request Code</label>
                                    <input type="text"
                                           value="<fmt:formatNumber value='${goodReceipt.id}' pattern='000'/>" disabled
                                           class="form-control" id="detail-request-code">
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Related Purchase Request</label>
                                    <input type="text"
                                           value="<fmt:formatNumber value='${goodReceipt.purchaseRequestId}' pattern='000'/>"
                                           disabled class="form-control" id="detail-related-pr">
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Assigned By</label>
                                    <input type="text" value="${approvedBy}" disabled class="form-control"
                                           id="detail-assigned-by">
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Created At</label>
                                    <input type="text"
                                           value="<fmt:formatDate value='${goodReceipt.createdAt}' pattern='dd MMM yyyy'/>"
                                           disabled class="form-control" id="detail-created-at">
                                </div>
                            </div>
                        </div>

                        <!-- Status Timeline -->
                        <div class="status-timeline" id="status-timeline">
                            <div class="status-step ${goodReceipt.status == 'NEW' ? 'active' : 'done'}" id="step-new">
                                <div class="step-circle"><i class="fas fa-file-alt"></i></div>
                                <div class="step-label">New</div>
                            </div>
                            <div class="status-connector ${goodReceipt.status != 'NEW' ? 'done' : ''}"
                                 id="connector-1"></div>
                            <div class="status-step ${goodReceipt.status == 'DOING' ? 'active' : (goodReceipt.status == 'COMPLETED' ? 'done' : '')}"
                                 id="step-doing">
                                <div class="step-circle"><i class="fas fa-cogs"></i></div>
                                <div class="step-label">Doing</div>
                            </div>
                            <div class="status-connector ${goodReceipt.status == 'COMPLETED' ? 'done' : ''}"
                                 id="connector-2"></div>
                            <div class="status-step ${goodReceipt.status == 'COMPLETED' ? 'done' : ''}"
                                 id="step-completed">
                                <div class="step-circle"><i class="fas fa-check"></i></div>
                                <div class="step-label">Completed</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <div class="form-group mb-3">
                            <h5 style="font-weight: 600; color: #333; border-bottom: 1px solid #eee; padding-bottom: 10px;">
                                <i class="fas fa-box me-2"></i>Product List to Import
                            </h5>
                        </div>

                        <div class="table-responsive">
                            <table class="table" id="detail-product-table">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Product Name</th>
                                    <th>Category</th>
                                    <th class="text-center">Quantity to Import</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${purchaseItems}" var="item" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td class="productimgname">
                                            <a class="product-img">
                                                <img src="${productMap[item.productId].imgUrl}" alt="product">
                                            </a>
                                            <a href="javascript:void(0);">${productMap[item.productId].name}</a>
                                        </td>
                                        <td>${productMap[item.productId].category.name}</td>
                                        <td class="text-center"><strong
                                                style="font-size: 16px;">${item.requiredQuantity}</strong>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="form-group mt-3">
                            <label><strong><i class="fas fa-comment-alt me-1"></i> Note from Manager</strong></label>
                            <div class="p-3 mt-1"
                                 style="background: #f8f9fa; border-radius: 8px; border-left: 4px solid #7367F0;">
                                <p class="mb-0"
                                   id="detail-manager-note">${not empty goodReceipt.note ? goodReceipt.note : 'No special note from manager.'}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card" id="action-card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-12 d-flex justify-content-between align-items-center">
                                <a href="importRequestList" class="btn btn-cancel" id="btn-cancel-action"
                                   style="padding: 10px 30px;">
                                    <i class="fas fa-times me-2"></i>Cancel
                                </a>
                                <input type="hidden" name="goodReId" value='${goodReceipt.id}'>
                                <button type="submit" name="action" value="accept" class="btn btn-submit"
                                        id="btn-accept-import"
                                        style="padding: 10px 35px; background: #FF9F43;">
                                    <i class="fas fa-play me-2"></i>Accept
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
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

        // Check URL params for demo status
        var urlParams = new URLSearchParams(window.location.search);
        var statusParam = urlParams.get('status');

        if (statusParam === 'doing') {
            setStatusDoing();
        } else if (statusParam === 'completed') {
            setStatusCompleted();
        }



        // === COMPLETE (Doing → Completed) ===
        $('#btn-complete-import').on('click', function () {
            Swal.fire({
                title: 'Complete Import?',
                html: `
                <div style="text-align:left; line-height: 1.8;">
                    <p>Confirm that <strong>all products</strong> have been imported into the warehouse.</p>
                    <p><strong>Request:</strong> IR-001</p>
                    <p><strong>Products:</strong> 3 items (45 units total)</p>
                    <hr>
                    <p class="text-muted">Status will change from <strong>Doing</strong> → <strong>Completed</strong>.</p>
                    <p class="text-muted">Inventory stock will be updated accordingly.</p>
                </div>
            `,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#28C76F',
                cancelButtonColor: '#6c757d',
                confirmButtonText: '<i class="fas fa-check-double me-1"></i> Yes, Complete!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    setStatusCompleted();
                    Swal.fire({
                        icon: 'success',
                        title: 'Import Completed!',
                        html: '<p>Import Request <strong>IR-001</strong> has been completed.</p><p>Inventory stock has been updated.</p>',
                        confirmButtonColor: '#28C76F'
                    });
                }
            });
        });

        // === Status Helper Functions ===
        function setStatusDoing() {
            // Timeline
            $('#step-new').removeClass('active').addClass('done');
            $('#connector-1').addClass('done');
            $('#step-doing').addClass('active');

            // Buttons
            $('#btn-accept-import').hide();
            $('#btn-complete-import').show();
            $('#label-completed').hide();
        }

        function setStatusCompleted() {
            // Timeline
            $('#step-new').removeClass('active').addClass('done');
            $('#connector-1').addClass('done');
            $('#step-doing').removeClass('active').addClass('done');
            $('#connector-2').addClass('done');
            $('#step-completed').addClass('done');

            // Buttons
            $('#btn-accept-import').hide();
            $('#btn-complete-import').hide();
            $('#label-completed').show();

            // Hide action card cancel button text
            $('#btn-cancel-action').html('<i class="fas fa-arrow-left me-2"></i>Back to List');
        }
    });
</script>
</body>
</html>
