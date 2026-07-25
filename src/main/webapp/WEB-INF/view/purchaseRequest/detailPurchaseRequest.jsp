<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>Purchase Request Detail - WHM System</title>

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

    <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Purchase Request Detail</h4>
                    <h6>View purchase request details</h6>
                </div>
            </div>

            <div class="alert alert-warning d-flex align-items-center mb-3" role="alert" id="status-banner" style="border-radius: 8px;">
                <i class="fas fa-info-circle me-2" style="font-size: 18px;"></i>
                <div>
                    <strong>Request Code: <fmt:formatNumber value="${purchaseRequest.id}" pattern="000"/></strong> &nbsp;|&nbsp; Status: 
                    <c:choose>
                        <c:when test="${purchaseRequest.status == 'New' || purchaseRequest.status == 'NEW'}">
                            <span class="badges bg-lightyellow">${purchaseRequest.status}</span>
                        </c:when>
                        <c:when test="${purchaseRequest.status == 'Approved' || purchaseRequest.status == 'APPROVED'}">
                            <span class="badges bg-lightyellow">${purchaseRequest.status}</span>
                        </c:when>
                        <c:when test="${purchaseRequest.status == 'Rejected' || purchaseRequest.status == 'REJECTED'}">
                            <span class="badges bg-lightred">${purchaseRequest.status}</span>
                        </c:when>
                        <c:when test="${purchaseRequest.status == 'Processing' || purchaseRequest.status == 'PROCESSING'}">
                            <span class="badges bg-lightyellow">${purchaseRequest.status}</span>
                        </c:when>
                        <c:when test="${purchaseRequest.status == 'Completed' || purchaseRequest.status == 'COMPLETED'}">
                            <span class="badges bg-lightgreen">${purchaseRequest.status}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badges bg-lightgrey">${purchaseRequest.status}</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="form-group">
                                <label>Created By</label>
                                <input type="text" value="${sessionScope.user.fullName}" disabled
                                       class="form-control"
                                       id="salesman-display">
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="form-group">
                                <label>Supplier</label>
                                <input type="text" value="${purchaseRequest.supplierName}" disabled
                                       class="form-control"
                                       id="supplier-display">
                            </div>
                        </div>
                    </div>

                    <div class="row align-items-stretch">
                        <div class="col-lg-12 col-md-12 d-flex mb-4">
                            <div class="card bg-light w-100 d-flex flex-column mb-0">
                                <div class="card-body p-3 d-flex flex-column">
                                    <h5 class="mb-3" style="font-weight: 600;">Request Items</h5>
                                    <div class="table-responsive flex-grow-1"
                                         style="max-height: 400px; overflow-y: auto;">
                                        <table class="table table-hover mb-0">
                                            <thead
                                                    style="position: sticky; top: 0; background-color: #f8f9fa; z-index: 1;">
                                            <tr>
                                                <th>Name</th>
                                                <th>SKU</th>
                                                <th>Category</th>
                                                <th>In Stock</th>
                                                <th style="width: 150px;">Requested Quantity</th>
                                                <th style="text-align: right;">Total Price</th>
                                            </tr>
                                            </thead>
                                            <tbody id="selected-product-list">
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <td colspan="6" style="text-align: right; font-weight: 600;">Grand Total:</td>
                                                    <td id="grand-total-amount" style="text-align: right; font-weight: 700; color: #28C76F; font-size: 16px;">
                                                        0đ
                                                    </td>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>

                                    <div class="mt-auto pt-3">
                                        <div class="form-group mb-3">
                                            <label>Note</label>
                                            <c:choose>
                                                <c:when test="${not empty purchaseRequest.note}">
                                                    <textarea class="form-control" rows="2" name="note" readonly
                                                              id="request-note">${purchaseRequest.note}</textarea>
                                                </c:when>
                                                <c:otherwise>
                                                    <textarea class="form-control" rows="2" name="note" readonly
                                                              id="request-note" style="color: #6c757d; font-style: italic;">No additional note provided.</textarea>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="text-end">
                                            <a href="purchaseRequestList" class="btn btn-cancel"
                                               id="btn-cancel-create">Back to List</a>
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
        let selectedItems = [
            <c:forEach items="${purchaseItems}" var="item" varStatus="loop">
            {
                id: ${item.productId},
                name: "${productMap[item.productId].name}",
                sku: "${productMap[item.productId].sku}",
                category: "${productMap[item.productId].category.name}",
                stock: ${productMap[item.productId].totalQuantity},
                reqQty: ${item.requiredQty},
                price: ${item.price}
            }${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        function renderSelectedItems() {
            let html = '';
            let grandTotal = 0;
            if (selectedItems.length === 0) {
                html = '<tr><td colspan="6" class="text-center text-muted">No products selected</td></tr>';
            } else {
                selectedItems.forEach((item, index) => {
                    let itemTotal = item.price * item.reqQty;
                    grandTotal += itemTotal;
                    html += `
                        <tr>
                            <td>\${item.name}</td>
                            <td>\${item.sku}</td>
                            <td>\${item.category}</td>
                            <td>\${item.stock}</td>
                            <td>\${item.reqQty}</td>
                            <td style="text-align: right;">\${new Intl.NumberFormat('vi-VN').format(itemTotal)}đ</td>
                        </tr>
                    `;
                });
            }
            $('#selected-product-list').html(html);
            $('#grand-total-amount').text(new Intl.NumberFormat('vi-VN').format(grandTotal) + 'đ');
        }

        // Initial render
        renderSelectedItems();
    });
</script>
</body>

</html>
