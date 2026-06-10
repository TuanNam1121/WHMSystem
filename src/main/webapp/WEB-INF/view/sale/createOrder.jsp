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
                                            <input type="text" name="customerName">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Customer Phone</label>
                                            <input type="text" name="customerPhone">
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <label>Note</label>
                                            <input class="form-control" type="text" name="note" >
                                        </div>
                                    </div>

                                    <table style="max-width: 800px; border: solid 1px black; margin-left: 50px">
                                        <tr>
                                            <td>product</td>
                                            <td>quantity</td>
                                            <td>price</td>
                                            <td>in stock</td>
                                        </tr>
                                        <c:forEach items="${products}" var="p">               
                                            <tr>
                                                <td>${p.name}</td>
                                                <td><input type="number" name="quantity_${p.productId}" min="0" max="${p.totalQuantity}" style="width: 80px"></td>
                                                <td><input type="number"  name="price_${p.productId}" min="0" style="width: 120px"></td>
                                                <td>${p.totalQuantity}</td>
                                                <td hidden>
                                                    <input type="hidden" name="productId" value="${p.productId}">
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>

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
    </body>
</html>
