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

    <link rel="shortcut icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/assets/img/favicon.jpg">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
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
                    <h4>Create Purchase Request</h4>
                    <h6>Create a new purchase request to send to Manager</h6>
                </div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong>${error}</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"
                            aria-label="Close"></button>
                </div>
            </c:if>

            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="form-group">
                                <label>Salesman</label>
                                <input type="text" value="${sessionScope.user.fullName}" disabled
                                       class="form-control" id="salesman-display">
                            </div>
                        </div>
                    </div>

                    <form action="createPurchaseRequest" method="get">

                        <div class="row align-items-stretch">
                            <div class="col-lg-12 col-md-12 d-flex mb-4">
                                <div class="card bg-light w-100 d-flex flex-column mb-0">
                                    <div class="card-body p-3 d-flex flex-column">
                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                            <h5 class="mb-0" style="font-weight: 600;">Products</h5>
                                            <div class="search-set m-0">
                                                <div class="input-group input-group-sm">
                                                    <input type="text" id="product-search"
                                                           name="productSearch" class="form-control"
                                                           placeholder="Search product name..."
                                                           value="${param.productSearch}">
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="fas fa-search"></i> Search
                                                    </button>
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
                                                        <td class="product-category">${p.category.name}
                                                        </td>
                                                        <td class="product-quantity">${p.totalQuantity}
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${p.totalQuantity > 10}">
                                                                    <span class="badges bg-lightgreen">In stock</span>
                                                                </c:when>
                                                                <c:when test="${p.totalQuantity > 0}">
                                                                    <span class="badges bg-lightyellow">Low stock</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="badges bg-lightred">Out of stock</span>
                                                                </c:otherwise>
                                                            </c:choose>
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
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                    <form action="createPurchaseRequest" method="post">
                        <input type="hidden" id="salesman-id" name="salesmanId" value="${sessionScope.user.id}">

                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Supplier <span class="text-danger">*</span></label>
                                    <select class="select" name="supplierId" required>
                                        <option value="" disabled selected>Select a supplier</option>
                                        <c:forEach items="${requestScope.supplierList}" var="s">
                                            <option value="${s.supplierId}">${s.supplierName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row align-items-stretch">
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
                                                    <th>Price</th>
                                                    <th style="width: 150px;">Required Quantity</th>
                                                    <th>Action</th>
                                                </tr>
                                                </thead>
                                                <tbody id="selected-product-list">
                                                </tbody>
                                                <tfoot id="total-amount-footer" style="display: none;">
                                                <tr>
                                                    <td colspan="4" class="text-end" style="font-weight: 600; font-size: 16px;">Total Amount:</td>
                                                    <td colspan="2" style="font-weight: 700; font-size: 18px; color: #FF9F43;" id="total-amount-display">0 VNĐ</td>
                                                </tr>
                                                </tfoot>
                                            </table>
                                        </div>

                                        <div class="mt-auto pt-3">
                                            <div class="form-group mb-3">
                                                <label>Note</label>
                                                <textarea class="form-control" rows="2" name="note"
                                                          placeholder="Enter note for this purchase request..."
                                                          id="request-note"></textarea>
                                            </div>

                                            <div class="text-end">
                                                <a href="purchaseRequestList" class="btn btn-cancel"
                                                   id="btn-cancel-create">Cancel</a>
                                                <input type="submit" class="btn btn-submit me-2"
                                                       id="btn-send-request" value="Send Request">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
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
                selectedItems.forEach((item, index) => {
                    html += `
                        <tr>
                            <input type="hidden" value="\${item.id}" name="selectedId\${index}">
                            <td>\${item.name}</td>
                            <td>\${item.sku}</td>
                            <td>\${item.category}</td>
                            <td>
                                <input type="hidden" name="selectedPrice\${index}" class="price-hidden-input" value="\${item.price}">
                                <div class="input-group input-group-sm" style="width: 150px;">
                                    <input type="text" class="form-control price-display-input"
                                    data-id="\${item.id}" value="\${item.price.toLocaleString('vi-VN')}" required>
                                    <span class="input-group-text">VNĐ</span>
                                </div>
                            </td>
                            <td>
                                <input name="selectedQty\${index}" type="number" min=1 class="form-control form-control-sm qty-input" 
                                data-id="\${item.id}" value="\${item.reqQty}" style="width: 100px;">
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
            updateTotalAmount();
        }

        function updateTotalAmount() {
            if (selectedItems.length === 0) {
                $('#total-amount-footer').hide();
                return;
            }
            $('#total-amount-footer').show();
            let total = 0;
            selectedItems.forEach(item => {
                const price = isNaN(item.price) ? 0 : item.price;
                const qty = isNaN(item.reqQty) ? 0 : item.reqQty;
                total += (price * qty);
            });
            $('#total-amount-display').text(total.toLocaleString('vi-VN') + ' VNĐ');
        }

        // Initial render
        renderSelectedItems();

        // Add Product
        $(document).on('click', '.add-product-btn', function () {
            // const isActive = $(this).data('active');
            // if (isActive === false || isActive === 'false') {
            //     Swal.fire({
            //         icon: 'error',
            //         title: 'Cannot Add Product',
            //         text: 'You cannot add an inactive product.',
            //         confirmButtonColor: '#FF9F43'
            //     });
            //     return;
            // }

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
                    reqQty: 1,
                    price: 1000
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

        // Update Price on input
        $(document).on('input', '.price-display-input', function (e) {
            let val = $(this).val();
            let cleanStr = val.replace(/\D/g, '');
            let numericVal = cleanStr ? parseInt(cleanStr, 10) : 0;

            const id = $(this).data('id');
            const item = selectedItems.find(i => i.id === id);
            item.price = numericVal;

            $(this).closest('td').find('.price-hidden-input').val(numericVal);

            if (cleanStr) {
                $(this).val(numericVal.toLocaleString('vi-VN'));
            } else {
                $(this).val('');
            }

            updateTotalAmount();
        });

        $(document).on('blur', '.price-display-input', function () {
            let cleanStr = $(this).val().replace(/\D/g, '');
            let numericVal = cleanStr ? parseInt(cleanStr, 10) : 0;
            if (numericVal < 1000) {
                numericVal = 1000;
            }
            const id = $(this).data('id');
            const item = selectedItems.find(i => i.id === id);
            item.price = numericVal;
            $(this).closest('td').find('.price-hidden-input').val(numericVal);
            $(this).val(numericVal.toLocaleString('vi-VN'));
            updateTotalAmount();
        });

        // Update Qty
        $(document).on('change', '.qty-input', function () {
            const id = $(this).data('id');
            const newQty = parseInt($(this).val());
            const item = selectedItems.find(i => i.id === id);
            item.reqQty = newQty;
            $(this).val(item.reqQty);
            updateTotalAmount();
        });
    });
</script>
</body>

</html>