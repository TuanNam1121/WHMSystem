<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="permissions" scope="request" />
<c:set var="pageTitle" value="Create Order" scope="request" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Dreams Pos admin template</title>

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
            <div class="whirly-loader"> </div>
        </div>

        <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>



            <div class="main-wrapper">

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Create Order</h4>
                            </div>
                        </div>
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger" role="alert">${message}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/CreateOder" method="post">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Customer Name</label>
                                            <input type="text" name="customerName" value="${customer.name}" readonly>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Customer Phone</label>
                                            <input type="text" name="customerPhone" value="${customer.phone}" readonly>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <label>Note</label>
                                            <input class="form-control" type="text" name="note" >
                                        </div>
                                    </div>

                                    <div class="table-responsive flex-grow-1"
                                             style="max-height: 400px; overflow-y: auto;">
                                        <input id="product-search" type="text" placeholder="Search">
                                            <table class="table table-hover table-nowrap mb-0">
                                                <thead style="position: sticky; top: 0; background-color: #f8f9fa; z-index: 1;">
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
                                                <c:forEach items="${products}" var="p">
                                                    <c:if test="${p.totalQuantity>0}">
                                                    <tr class="product-item">
                                                        <td class="product-name">${p.name}</td>
                                                        <td class="product-sku">${p.sku}</td>
                                                        <td class="product-category">${p.category.name}</td>
                                                        <td class="product-quantity">${p.totalQuantity}</td>
                                                        <td><span
                                                                class="badges ${p.isActive ? 'bg-lightgreen' : 'bg-lightred'}">
                                                                ${p.isActive ? 'Active' : 'Inactive'}</span>
                                                        </td>
                                                        <td>
                                                            <a class="btn btn-sm btn-outline-primary add-product-btn"
                                                               data-id="${p.productId}"
                                                               data-name="${p.name}"
                                                               data-sku="${p.sku}"
                                                               data-category="${p.category.name}"
                                                               data-stock="${p.totalQuantity}"
                                                               data-active="${p.isActive}"
                                                               href="javascript:void(0);">
                                                                <i class="fas fa-plus"></i> Add
                                                            </a>
                                                        </td>
                                                    </tr>
                                                    </c:if>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    <div class="table-responsive flex-grow-1"
                                             style="max-height: 250px; overflow-y: auto;">
                                            <table class="table table-hover mb-0">
                                                <thead
                                                        style="position: sticky; top: 0; background-color: #f8f9fa; z-index: 1;">
                                                <tr>
                                                    <th>Name</th>
                                                    <th>In Stock</th>
                                                    <th>Quantity</th>
                                                    <th>Price</th>
                                                    <th>Action</th>
                                                </tr>
                                                </thead>
                                                <tbody id="selected-product-list">
                                                </tbody>
                                            </table>
                                        </div>
                                    <div class="col-lg-12">
                                        <input class="btn btn-submit me-2" type="submit" value="CREATE">
                                        <a href="${pageContext.request.contextPath}/OrderList" class="btn btn-cancel">CANCEL</a>
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
        let selectedItems = [];

        function renderSelectedItems() {
            let html = '';
            if (selectedItems.length === 0) {
                html = '<tr><td colspan="6" class="text-center text-muted">No products selected</td></tr>';
            } else {
                selectedItems.forEach((item, index) => {
                    html += `
                        <tr>
                            <input type="hidden" value="\${item.id}" name="productId">
                            <td>\${item.name}</td>
                            <td>\${item.stock}</td>
                            <td><input type="number" name="quantity_\${item.id}" min="1" max="\${item.stock}" value="\${item.quantity}"></td>
                            <td><input type="number" name="price_\${item.id}" min="1" ></td>
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
            const isActive = $(this).data('active');
            if (isActive === false || isActive === 'false') {
                Swal.fire({
                    icon: 'error',
                    title: 'Cannot Add Product',
                    text: 'You cannot add an inactive product.',
                    confirmButtonColor: '#FF9F43'
                });
                return;
            }

            const id = $(this).data('id');
            const name = $(this).data('name');
            const sku = $(this).data('sku');
            const category = $(this).data('category');
            const stock = $(this).data('stock');

            const existingItem = selectedItems.find(item => item.id === id);
            if (existingItem) {
                existingItem.quantity++;
            } else {
                selectedItems.push({
                    id: id,
                    name: name,
                    stock: stock,
                    quantity: 1
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

        // Send Request Validation
        $('form').on('submit', function (e) {
            if (selectedItems.length === 0) {
                e.preventDefault();
                Swal.fire({
                    icon: 'error',
                    title: 'Validation Error',
                    text: 'Please select at least one product!',
                    confirmButtonColor: '#FF9F43'
                });
                return false;
            }
        });
    });
        </script>
    </body>
</html>
