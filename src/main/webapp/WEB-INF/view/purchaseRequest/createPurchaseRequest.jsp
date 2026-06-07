<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Create Purchase Request for Salesman">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Create Purchase Request - Dreams Pos</title>

    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.jpg">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>
<div id="global-loader">
    <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">

    <jsp:include page="/common/sidebar.jsp"></jsp:include>
    <jsp:include page="/common/header.jsp"></jsp:include>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Create Purchase Request</h4>
                    <h6>Create a new purchase request to send to Manager</h6>
                </div>
            </div>
            <div class="card">
                <div class="card-body">
                    <input type="hidden" id="salesman-id" name="salesmanId" value="${sessionScope.user.id}">

                    <div class="row">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label>Salesman</label>
                                <input type="text" value="${sessionScope.user.userName}" disabled class="form-control"
                                       id="salesman-display">
                            </div>
                        </div>
                    </div>

                    <div class="row align-items-stretch">
                        <div class="col-lg-12 col-md-12 d-flex mb-4">
                            <div class="card bg-light w-100 d-flex flex-column mb-0">
                                <div class="card-body p-3 d-flex flex-column">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h5 class="mb-0" style="font-weight: 600;">Products</h5>
                                        <div class="search-set m-0">
                                            <div class="search-input">
                                                <input type="text" id="product-search"
                                                       class="form-control form-control-sm"
                                                       placeholder="Search product...">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="table-responsive flex-grow-1"
                                         style="max-height: 400px; overflow-y: auto;">
                                        <table class="table table-hover table-nowrap mb-0">
                                            <thead
                                                    style="position: sticky; top: 0; background-color: #f8f9fa; z-index: 1;">
                                            <tr>
                                                <th>Name</th>
                                                <th>SKU</th>
                                                <th>Category</th>
                                                <th>Quantity</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody id="product-list-body">
                                            <c:forEach items="${requestScope.productListForPurchase}" var="p">
                                                <tr class="product-item">
                                                    <td class="product-name">${p.name}</td>
                                                    <td class="product-sku">${p.sku}</td>
                                                    <td class="product-category">${p.category.name}</td>
                                                    <td class="product-quantity">${p.totalQuantity}</td>
                                                    <td><span class="badges ${p.isActive ? 'bg-lightgreen' : 'bg-lightred'}">
                                                            ${p.isActive ? 'Active' : 'Inactive'}</span>
                                                    </td>
                                                    <td>
                                                        <a class="btn btn-sm btn-outline-primary add-product-btn"
                                                           data-id="${p.productId}"
                                                           data-name="${p.name}"
                                                           data-sku="${p.sku}"
                                                           data-category="${p.category.name}"
                                                           data-stock="${p.totalQuantity}"
                                                           href="javascript:void(0);">
                                                            <i class="fas fa-plus"></i> Add
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-12 col-md-12 d-flex mb-4">
                            <div class="card bg-light w-100 d-flex flex-column mb-0">
                                <div class="card-body p-3 d-flex flex-column">
                                    <h5 class="mb-3" style="font-weight: 600;">Selected Items</h5>
                                    <div class="table-responsive flex-grow-1"
                                         style="max-height: 250px; overflow-y: auto;">
                                        <table class="table table-hover mb-0">
                                            <thead
                                                    style="position: sticky; top: 0; background-color: #f8f9fa; z-index: 1;">
                                            <tr>
                                                <th>Name</th>
                                                <th>SKU</th>
                                                <th>Category</th>
                                                <th>In Stock</th>
                                                <th style="width: 150px;">Required Quantity</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody id="selected-product-list">
                                            </tbody>
                                        </table>
                                    </div>

                                    <div class="mt-auto pt-3">
                                        <div class="form-group mb-3">
                                            <label>Note</label>
                                            <textarea class="form-control" rows="2"
                                                      placeholder="Enter note for this purchase request..."
                                                      id="request-note"></textarea>
                                        </div>

                                        <div class="text-end">
                                            <a href="javascript:void(0);" class="btn btn-submit me-2"
                                               id="btn-send-request">Send Request</a>
                                            <a href="purchase-request-list.html" class="btn btn-cancel"
                                               id="btn-cancel-create">Cancel</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/assets/js/jquery-3.6.0.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/jquery.slimscroll.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/plugins/select2/js/select2.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap-datetimepicker.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/sweetalert/sweetalerts.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

<script>
    $(document).ready(function () {
        let selectedItems = [];

        function renderSelectedItems() {
            let html = '';
            if (selectedItems.length === 0) {
                html = '<tr><td colspan="6" class="text-center text-muted">No products selected</td></tr>';
            } else {
                selectedItems.forEach(item => {
                    html += `
                        <tr>
                            <td>\${item.name}</td>
                            <td>\${item.sku}</td>
                            <td>\${item.category}</td>
                            <td>\${item.stock}</td>
                            <td>
                                <input type="number" class="form-control form-control-sm qty-input" data-id="\${item.id}" value="\${item.reqQty}" min="1" style="width: 100px;">
                            </td>
                            <td>
                                <a class="delete-set remove-item-btn" href="javascript:void(0);" data-id="\${item.id}">
                                    <img src="${pageContext.request.contextPath}/assets/img/icons/delete.svg" alt="Remove">
                                </a>
                            </td>
                        </tr>
                    `;
                });
            }
            $('#selected-product-list').html(html);
        }

        // Initial render
        renderSelectedItems();

        // Search Product directly in DOM
        $('#product-search').on('input', function () {
            const searchTerm = $(this).val().toLowerCase();
            $('.product-item').each(function () {
                const name = $(this).find('.product-name').text().toLowerCase();
                const sku = $(this).find('.product-sku').text().toLowerCase();
                if (name.includes(searchTerm) || sku.includes(searchTerm)) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        });

        // Add Product
        $(document).on('click', '.add-product-btn', function () {
            const id = $(this).data('id');
            const name = $(this).data('name');
            const sku = $(this).data('sku');
            const category = $(this).data('category');
            const stock = $(this).data('stock');

            const existingItem = selectedItems.find(item => item.id === id);
            if (existingItem) {
                existingItem.reqQty++;
            } else {
                selectedItems.push({
                    id: id,
                    name: name,
                    sku: sku,
                    category: category,
                    stock: stock,
                    reqQty: 1
                });
            }
            renderSelectedItems();
        });

        // Remove Product
        $(document).on('click', '.remove-item-btn', function () {
            const id = $(this).data('id');
            selectedItems = selectedItems.filter(item => item.id !== id);
            renderSelectedItems();
        });

        // Update Qty
        $(document).on('change', '.qty-input', function () {
            const id = $(this).data('id');
            const newQty = parseInt($(this).val());
            const item = selectedItems.find(i => i.id === id);
            if (item) {
                item.reqQty = newQty > 0 ? newQty : 1;
                $(this).val(item.reqQty);
            }
        });

        // Send Request
        $('#btn-send-request').on('click', function () {
            if (selectedItems.length === 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Validation Error',
                    text: 'Please select at least one product!',
                    confirmButtonColor: '#FF9F43'
                });
                return;
            }

            Swal.fire({
                title: 'Send Purchase Request?',
                text: 'This request will be sent to your Manager for approval.',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#FF9F43',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, Send it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Request Sent!',
                        text: 'Your purchase request has been sent to the Manager.',
                        confirmButtonColor: '#FF9F43'
                    }).then(() => {
                        window.location.href = 'purchase-request-list.html';
                    });
                }
            });
        });
    });
</script>
</body>

</html>
