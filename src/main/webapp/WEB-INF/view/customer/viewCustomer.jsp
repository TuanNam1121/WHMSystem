<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>View Customer - WHM System</title>

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
                                <h4>View Customer</h4>
                            </div>
                        </div>
                    <c:if test="${not empty requestScope.message}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong>${requestScope.message}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.error}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong>${requestScope.error}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                        <div class="card">
                            <div class="card-body">
                                <div class="row" style="display: block">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Customer ID</label>
                                            <input type="text" name="customerId" value="${customer.id}" readonly>
                                        </div>
                                    </div>
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
                                        <a href="${pageContext.request.contextPath}/UpdateCustomer?id=${customer.id}" class="btn btn-submit">UPDATE</a>
                                        <a href="${pageContext.request.contextPath}/CustomerList" class="btn btn-cancel">DONE</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                                    
                                    <fmt:setLocale value="en_US"/>
                                    <table class="table" >
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>Customer name</th>
                                            <th>total price</th>
                                            <th>Note</th>
                                            <th>Order date</th>
                                            <th>Created by</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${orders}" var="o">
                                            <tr>
                                                <td>SO-${o.id}</td>
                                                <td>${o.customer}</td>
                                                <td>
                                                    <fmt:formatNumber
                                                        value="${o.totalPrice}"
                                                        pattern="#,###"/>
                                                </td>
                                                <td style="max-width: 200px; overflow-x: auto">${o.note}</td>
                                                <td>${o.orderDate}</td>
                                                <td>${o.creater}</td>
                                                <td><c:choose>
                                                        <c:when
                                                            test="${o.status == 'NEW' || o.status == 'New'}">
                                                            <span class="badges bg-lightyellow">${o.status}</span>
                                                        </c:when>
                                                        <c:when
                                                            test="${o.status == 'DOING' || o.status == 'Doing'}">
                                                            <span class="badges bg-lightpurple">${o.status}</span>
                                                        </c:when>
                                                        <c:when
                                                            test="${o.status == 'COMPLETED' || o.status== 'Completed'}">
                                                            <span class="badges bg-lightgreen">${o.status}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badges bg-lightgrey">${o.status}</span>
                                                        </c:otherwise>
                                                    </c:choose></td>
                                                
                                            </tr>
                                        </c:forEach>
                                            <tr>
                                                <td></td>
                                                <td>Total spent: </td>
                                                <td><fmt:formatNumber
                                                        value="${totalSpent}"
                                                        pattern="#,###"/></td>
                                            </tr>
                                    </tbody>
                                </table>

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
