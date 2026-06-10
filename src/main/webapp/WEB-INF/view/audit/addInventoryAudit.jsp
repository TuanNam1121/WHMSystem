<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Add Inventory Audit</title>

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
                    <h4>${empty audit ? 'Add' : 'Edit'} Inventory Audit</h4>
                    <h6>${empty audit ? 'Perform a new warehouse stock take and check discrepancies' : 'Edit
                            draft warehouse stock take'}</h6>
                </div>
            </div>

            <c:if test="${not empty message}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Error!</strong> ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"
                            aria-label="Close"></button>
                </div>
            </c:if>

            <form action="${empty audit ? 'AddInventoryAudit' : 'EditInventoryAudit'}" method="POST"
                  id="auditForm">
                <c:if test="${not empty audit}">
                    <input type="hidden" name="id" value="${audit.id}">
                </c:if>
                <div class="card">
                    <div class="card-body">
                        <div class="table-top">
                            <div class="wordset">
                                <h6>Select the products that need to be physically audited below.</h6>
                            </div>
                            <div class="search-set">
                                <div class="search-input">
                                    <a class="btn btn-searchset"><img
                                            src="assets/img/icons/search-whites.svg" alt="img"></a>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-hover audit-table">
                                <thead>
                                <tr>
                                    <th style="width: 50px;">Select</th>
                                    <th>SKU</th>
                                    <th>Product Name</th>
                                    <th>Category</th>
                                    <th>Current System Qty</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="product" items="${products}">
                                    <c:set var="isChecked" value="false"/>
                                    <c:if test="${not empty audit}">
                                        <c:forEach var="item" items="${audit.inventoryAuditItems}">
                                            <c:if test="${item.productId == product.productId}">
                                                <c:set var="isChecked" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <tr>
                                        <td>
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox"
                                                       name="selectedProductIds"
                                                       value="${product.productId}"
                                                       id="chk_${product.productId}"
                                                        ${isChecked ? 'checked' : '' }>
                                            </div>
                                        </td>
                                        <td><span
                                                class="text-secondary font-weight-bold">${product.sku}</span>
                                        </td>
                                        <td><strong>${product.name}</strong></td>
                                        <td>${product.category.name}</td>
                                        <td>
                                                            <span
                                                                    class="font-weight-bold">${product.totalQuantity}</span>
                                            <input type="hidden"
                                                   name="systemQuantity_${product.productId}"
                                                   value="${product.totalQuantity}">
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="row mt-4">
                            <div class="col-lg-12 text-end">
                                <a href="InventoryAuditList" class="btn btn-cancel me-2">Cancel</a>
                                <button type="submit" name="action" value="draft"
                                        class="btn btn-cancel me-2" style="background: #82868b;">Save as Draft
                                </button>
                                <button type="submit" name="action" value="submit"
                                        class="btn btn-submit">Submit
                                    Request
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
<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/dataTables.bootstrap4.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/plugins/select2/js/select2.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="assets/js/script.js"></script>

<script>

    function validateForm() {
        var table = $('.audit-table').DataTable();
        var checkedCount = table.$('input[name="selectedProductIds"]:checked').length;
        if (checkedCount === 0) {
            Swal.fire({
                icon: 'warning',
                title: 'No Products Selected',
                text: 'Please select at least one product to add to the audit request.'
            });
            return false;
        }
        return true;
    }

    $(document).ready(function () {
        var table = $('.audit-table').DataTable({
            "bFilter": true,
            "paging": false,
            "info": false,
            "sDom": 'fBt',
            "ordering": true,
            "language": {
                search: ' ',
                searchPlaceholder: "Search...",
            },
            initComplete: function () {
                $('.dataTables_filter').appendTo('.search-input');
            }
        });

        $('#auditForm').on('submit', function (e) {
            if (!validateForm()) {
                e.preventDefault();
            }

            var form = this;


            table.$('input[name="selectedProductIds"]:checked').each(function () {

                if (!$.contains(document, this)) {

                    $(form).append(
                        $('<input>')
                            .attr('type', 'hidden')
                            .attr('name', this.name)
                            .val(this.value)
                    );


                    var productId = this.value;
                    var systemQtyInput = table.$('input[name="systemQuantity_' + productId + '"]')[0];
                    if (systemQtyInput) {
                        $(form).append(
                            $('<input>')
                                .attr('type', 'hidden')
                                .attr('name', systemQtyInput.name)
                                .val(systemQtyInput.value)
                        );
                    }
                }
            });
        });
    });
</script>
</body>

</html>