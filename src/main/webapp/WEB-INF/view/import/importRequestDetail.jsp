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
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label>Purchase Request</label>
                                                <input type="text"
                                                       value="PR-${importDTO.purchaseRequestId}" disabled
                                                class="form-control" id="detail-request-code">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Created At</label>
                                            <input type="text"
                                                   value="<fmt:formatDate value='${importDTO.createdAt}' pattern='dd MMM yyyy'/>"
                                                   disabled class="form-control" id="detail-created-at">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Created By</label>
                                            <input type="text"
                                                   value="${importDTO.createdBy}"
                                                   disabled class="form-control" id="detail-created-at">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Supplier</label>
                                            <div class="d-flex align-items-center mt-1">
                                                <div>
                                                    <strong id="detail-supplier-name">${importDTO.supplierName}</strong><br>
                                                    <small class="text-muted" id="detail-supplier-id">ID: ${importDTO.supplierId}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Status Timeline -->
                                <div class="status-timeline" id="status-timeline">
                                    <div class="status-step ${pr.status == 'NEW' ? 'active' : 'done'}" id="step-new">
                                        <div class="step-circle"><i class="fas fa-file-alt"></i></div>
                                        <div class="step-label">Approved</div>
                                    </div>
                                    <div class="status-connector ${pr.status != 'NEW' ? 'done' : ''}"
                                         id="connector-1"></div>
                                    <div class="status-step ${pr.status  == 'PROCESSING' ? 'active' : (pr.status  == 'COMPLETED' ? 'done' : '')}"
                                         id="step-pending">
                                        <div class="step-circle"><i class="fas fa-cogs"></i></div>
                                        <div class="step-label">Processing</div>
                                    </div>
                                    <div class="status-connector ${pr.status  == 'COMPLETED' ? 'done' : ''}"
                                         id="connector-2"></div>
                                    <div class="status-step ${pr.status  == 'COMPLETED' ? 'done' : ''}"
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
                                                <th class="text-center">Received</th>
                                                <th class="text-center">Remaining</th>
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
                                                            style="font-size: 16px;">${item.requiredQty}</strong>
                                                    </td>
                                                    <td class="text-center"><strong
                                                            style="font-size: 16px;">${importedMap.get(item.productId) != 0 ? importedMap.get(item.productId) : 0}</strong>
                                                    </td>
                                                    <td class="text-center"><strong
                                                            style="font-size: 16px;">${item.requiredQty - importedMap.get(item.productId)}</strong>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
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

                if (statusParam === 'pending') {
                    setStatusPending();
                } else if (statusParam === 'completed') {
                    setStatusCompleted();
                }





                // === Status Helper Functions ===
                function setStatusPending() {
                    // Timeline
                    $('#step-new').removeClass('active').addClass('done');
                    $('#connector-1').addClass('done');
                    $('#step-pending').addClass('active');

                    // Buttons
                    $('#btn-accept-import').hide();
                    $('#btn-complete-import').show();
                    $('#label-completed').hide();
                }

                function setStatusCompleted() {
                    // Timeline
                    $('#step-new').removeClass('active').addClass('done');
                    $('#connector-1').addClass('done');
                    $('#step-pending').removeClass('active').addClass('done');
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
