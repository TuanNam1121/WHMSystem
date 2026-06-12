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
                <title>Perform Inventory Audit</title>

                <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
                <link rel="stylesheet" href="assets/css/bootstrap.min.css">
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
                                    <h4>Perform Inventory Audit</h4>
                                    <h6>Complete details for audit request #${audit.id}</h6>
                                </div>
                            </div>

                            <c:if test="${not empty message}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <strong>Error:</strong> ${message}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"
                                        aria-label="Close"></button>
                                </div>
                            </c:if>


                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label class="font-weight-bold"
                                                    style="font-size: 14px; color: #555;">Auditor</label>
                                                <div class="form-control-static"
                                                    style="font-size: 16px; font-weight: 600; padding: 8px 0;">
                                                    ${audit.userFullName}
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label class="font-weight-bold"
                                                    style="font-size: 14px; color: #555;">Status</label>
                                                <div style="padding: 8px 0;">
                                                    <span class="badges bg-lightgrey"
                                                        style="font-size: 13px; padding: 6px 12px;">
                                                        ${audit.status}
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label class="font-weight-bold"
                                                    style="font-size: 14px; color: #555;">Created At</label>
                                                <div class="form-control-static"
                                                    style="font-size: 16px; padding: 8px 0;">
                                                    ${audit.createdAt}
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <label class="font-weight-bold"
                                                    style="font-size: 14px; color: #555;">Last
                                                    Updated</label>
                                                <div class="form-control-static"
                                                    style="font-size: 16px; padding: 8px 0;">
                                                    ${audit.updatedAt}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <form id="performAuditForm" action="PerformInventoryAudit" method="POST">
                                <input type="hidden" name="id" value="${audit.id}" />
                                <div class="card mt-4">
                                    <div class="card-body">
                                        <div class="card-title">
                                            <h5>Enter Physical Quantities</h5>
                                        </div>
                                        <div class="table-responsive">
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th>Product Name</th>
                                                        <th>SKU</th>
                                                        <th>Category</th>
                                                        <th class="text-end" style="width: 12%;">System Qty</th>
                                                        <th style="width: 15%;">Physical Qty</th>
                                                        <th class="text-end" style="width: 12%;">Discrepancy</th>
                                                        <th>Reason / Notes</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="item" items="${audit.inventoryAuditItems}">
                                                        <tr>
                                                            <td><strong>${item.productName}</strong></td>
                                                            <td><span
                                                                    class="text-secondary font-weight-bold">${item.productSku}</span>
                                                            </td>
                                                            <td>${item.categoryName}</td>
                                                            <td class="text-end font-weight-bold">${item.systemQuantity}
                                                            </td>
                                                            <td>
                                                                <input type="number"
                                                                    class="form-control physical-qty text-center font-weight-bold"
                                                                    name="physicalQuantity_${item.id}"
                                                                    id="physicalQuantity_${item.id}"
                                                                    data-item-id="${item.id}"
                                                                    data-system-qty="${item.systemQuantity}"
                                                                    value="${item.physicalQuantity > 0 ? item.physicalQuantity : ''}"
                                                                    min="0" style="max-width: 120px;" required />
                                                            </td>
                                                            <td class="text-end font-weight-bold">
                                                                <span id="discrepancy_${item.id}"
                                                                    class="text-secondary">0</span>
                                                            </td>
                                                            <td>
                                                                <div class="reason-container">
                                                                    <input type="text" class="form-control reason-input"
                                                                        name="reason_${item.id}" id="reason_${item.id}"
                                                                        value="${item.reason}"
                                                                        placeholder="No discrepancy (optional)"
                                                                        style="min-width: 250px;" />
                                                                    <small id="reason-error_${item.id}"
                                                                        class="text-danger d-none"
                                                                        style="font-size: 11px;">Reason/Notes are
                                                                        required for
                                                                        discrepancies</small>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="row mt-4">
                                            <div class="col-lg-12">
                                                <button type="submit" class="btn btn-submit me-2"
                                                    style="background: #ff9f43; color: white; border: none; padding: 10px 20px; border-radius: 5px; font-weight: 600;">
                                                    Submit Audit
                                                </button>
                                                <a href="InventoryAuditList" class="btn btn-cancel"
                                                    style="background: #637381; color: white; padding: 10px 20px; border-radius: 5px; font-weight: 600;">Cancel</a>
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
                <script src="assets/js/jquery.dataTables.min.js"></script>
                <script src="assets/js/dataTables.bootstrap4.min.js"></script>
                <script src="assets/js/bootstrap.bundle.min.js"></script>
                <script src="assets/plugins/select2/js/select2.min.js"></script>
                <script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
                <script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
                <script src="assets/js/script.js"></script>

                <script>
                    $(document).ready(function () {

                        function updateDiscrepancy($input) {
                            var id = $input.data("item-id");
                            var sysQty = parseInt($input.data("system-qty")) || 0;
                            var val = $input.val();
                            var $disc = $("#discrepancy_" + id);
                            var $reason = $("#reason_" + id);
                            var $err = $("#reason-error_" + id);

                            if (val === "") {
                                $disc.text("-").attr("class", "text-secondary");
                                $reason.prop("required", false).attr("placeholder", "No discrepancy (optional)");
                                return;
                            }

                            var diff = parseInt(val) - sysQty;
                            $disc.text(diff > 0 ? "+" + diff : diff)
                                .attr("class", diff > 0 ? "text-success" : diff < 0 ? "text-danger" : "text-secondary");

                            var hasDisc = diff !== 0;
                            $reason.prop("required", hasDisc)
                                .attr("placeholder", hasDisc ? "Reason is required *" : "No discrepancy (optional)");
                            if (!hasDisc) {
                                $reason.removeClass("border-danger");
                                $err.addClass("d-none");
                            }
                        }


                        $(".physical-qty").on("input change", function () {
                            updateDiscrepancy($(this));
                        })
                            .each(function () {
                                updateDiscrepancy($(this));
                            });


                        $(".physical-qty").on("input", function () {
                            $(this).removeClass("border-danger");
                        });
                        $(".reason-input").on("input", function () {
                            if ($(this).val().trim()) {
                                var id = $(this).attr("id").split("_")[1];
                                $(this).removeClass("border-danger");
                                $("#reason-error_" + id).addClass("d-none");
                            }
                        });


                        $("#performAuditForm").on("submit", function (e) {
                            e.preventDefault();
                            Swal.fire({
                                title: "Submit Audit Results?",
                                text: "This will submit audit result to manager.",
                                icon: "warning",
                                showCancelButton: true,
                                confirmButtonColor: "#3085d6",
                                cancelButtonColor: "#d33",
                                confirmButtonText: "Yes, submit it!"
                            }).then(function (result) {
                                if (result.isConfirmed) document.getElementById("performAuditForm").submit();
                            });
                        });
                    });
                </script>
            </body>

            </html>