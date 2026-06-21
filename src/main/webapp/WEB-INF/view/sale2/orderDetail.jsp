<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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


<fmt:setLocale value="en_US"/>
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
                        <form id="${order.status=='NEW'?'new-form':''}" action="${pageContext.request.contextPath}/${order.status=='NEW'?'OrderDetail':'OrderUpdateStatus'}" method="post">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <input type="hidden" name="orderid" value="${order.id}">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Customer Name</label>
                                            <input type="text" name="customerName" value="${order.customer}" readonly>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Customer Phone</label>
                                            <c:forEach items="${customers}" var="c">
                                                <c:if test="${c.id == order.customerId}">
                                                    <input type="text" name="customerPhone" value="${c.phone}" readonly>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <label>Note</label>
                                            <input class="form-control" type="text" name="note" value="${order.note}">
                                        </div>
                                    </div>

                                    <c:if test="${order.status=='NEW'}">
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
                                                                   href="UpdateAddItem?id=${p.productId}&orderId=${order.id}">
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
                                                <tbody id="selected-product-list" >
                                                    <c:forEach items="${sessionScope.orderItems}" var="oi">
                                                    <tr>
                                                        <input type="hidden" value="${oi.productId}" name="productId">
                                                        <td>${oi.productName}</td>
                                                        <td>${oi.inStock}</td>
                                                        <td>
                                                            <input required type="number" name="quantity_${oi.productId}" min="1" max="${oi.inStock}" value="${oi.quantity!=null?oi.quantity:''}"></td>
                                                        
                                                        <td><input required type="number" name="price_${oi.productId}" min="1" value="${oi.price!=null?oi.price:''}" ></td>
                                                        <td>
                                                            <a class="btn btn-sm btn-outline-primary add-product-btn"
                                                               href="UpdateRemoveItem?id=${oi.productId}&orderId=${order.id}">
                                                                <img src="${pageContext.request.contextPath}/assets/img/icons/delete.svg" alt="Remove">
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-lg-12">
                                        <input class="btn btn-submit me-2" type="submit" value="UPDATE">
                                        <a href="${pageContext.request.contextPath}/OrderList" class="btn btn-cancel">${action=='update'?'RETURN':'CANCEL'}</a>
                                    </div>
                                    
                                    </c:if>
                                        
                                        
                                    <c:if test="${order.status != 'NEW'}">
                                        <div class="row">
                                            <div class="table-responsive ">
                                                <table class="table">
                                                    <thead>
                                                        <tr>
                                                            <th>Product Name</th>
                                                            <th>SKU</th>
                                                            <th>S/N</th>
                                                            <th>Price</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${itemList}" var="s">
                                                            <tr class="bor-b1">
                                                                <td class="productimgname">
                                                                    <a class="product-img">
                                                                        <img src="${s.imgUrl}" alt="product">
                                                                    </a>
                                                                    <a href="javascript:void(0);">${s.name}</a>
                                                                </td>
                                                                <td>${s.sku}</td>
                                                                <td>${s.serial}</td>
                                                                <td>
                                                                    <fmt:formatNumber value="${s.price}" pattern="#,###"/>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group" style="margin-top: 40px; margin-bottom: 40px;">
                                                    <input checked type="radio" name="orderStatus" value="COMPLETED">COMPLETED
                                                    <input type="radio" name="orderStatus" value="CANCELLED">CANCELLED
                                            <div class="col-lg-12">
                                        <input class="btn btn-submit me-2" type="submit" value="UPDATE">
                                        <a href="${pageContext.request.contextPath}/OrderList" class="btn btn-cancel">${action=='update'?'RETURN':'CANCEL'}</a>
                                    </div>
                                                </div>
                                                </div>
                                    </c:if>
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
        </script>
    </body>
</html>
