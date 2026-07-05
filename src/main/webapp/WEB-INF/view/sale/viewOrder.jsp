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
                    <form action="${pageContext.request.contextPath}/OrderDetail" method="post">
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
                                            <input class="form-control" type="text" name="note" value="${order.note}" readonly>
                                        </div>
                                    </div>

                                    <c:if test="${order.status=='NEW'}">
                                        <div class="table-responsive flex-grow-1"
                                             style="max-height: 250px; overflow-y: auto;">
                                            <table class="table table-hover mb-0">
                                                <thead
                                                    style="position: sticky; top: 0; background-color: #f8f9fa; z-index: 1;">
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Quantity</th>
                                                        <th>Price</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="selected-product-list" >
                                                    <fmt:setLocale value="en_US"/>
                                                    <c:forEach items="${orderItems}" var="oi">
                                                        <tr>
                                                            <th>${oi.productName}</th>
                                                            <th>${oi.quantity}</th>
                                                            <th>
                                                                <div class="input-group input-group-sm" style="width: 150px;">
                                                                    <fmt:formatNumber value="${oi.price}" pattern="#,###"/> VNĐ
                                                                </div>
                                                            </th>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:if>
                                    <c:if test="${order.status!='NEW'}">
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
                                    </c:if>
                                    <div class="col-lg-12">
                                        <a class="btn btn-submit me-2"
                                           <c:if test="${sessionScope.userPermissions.contains('UPDATE_SALE_ORDER')}">
                                                <c:if test="${order.status=='NEW'}">
                                                    href="${pageContext.request.contextPath}/OrderDetail?id=${order.id}&action=update">UPDATE</a>
                                                </c:if>
                                           </c:if>
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
    </body>
</html>
