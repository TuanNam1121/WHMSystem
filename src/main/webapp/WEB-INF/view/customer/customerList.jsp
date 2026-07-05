<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Manager Purchase Request List">
        <meta name="keywords"
              content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Manager - Purchase Request List - Dreams Pos</title>

        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
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

            <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>
            <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>CUSTOMER LIST</h4>
                            </div>
                        <c:if test="${sessionScope.userPermissions.contains('CREATE_CUSTOMER')}">
                            <div class="page-btn">
                                <a href="CreateCustomer" class="btn btn-added" id="btn-create-request">
                                    ADD CUSTOMER
                                </a>
                            </div>
                        </c:if>
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


                                        <form action="${pageContext.request.contextPath}/CustomerList" method="post">
                                        <div style="display:flex">
                                            <div class="col-lg-3 col-sm-6 col-12">

                                                <div class="form-group">
                                                    <input name="searchName" type="text" placeholder="Enter name or phone" value="${searchName}" id="filter-code">
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-sm-6 col-12">
                                                <div class="form-group" style="margin-left: 24px">
                                                    <button type="submit" class="btn btn-filters"
                                                            style="border: none; padding: 0;">
                                                        <img src="assets/img/icons/search-whites.svg" alt="img">
                                                    </button>
                                                </div>
                                            </div>
                                        </div>

                            <div class="table-responsive">
                                <fmt:setLocale value="en_US"/>
                                <table class="table" >
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>Customer name</th>
                                            <th>Phone</th>
                                                <c:if test="${sessionScope.userPermissions.contains('UPDATE_CUSTOMER')}">
                                                <th>Update</th>
                                                </c:if>
                                                <c:if test="${sessionScope.userPermissions.contains('CREATE_SALE_ORDER')}">
                                                <th>Create Order</th>
                                                </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${customers}" var="c">
                                            <tr>
                                                <td>${c.id}</td>
                                                <td><a href="${pageContext.request.contextPath}/ViewCustomer?id=${c.id}">${c.name}</a></td>
                                                <td>${c.phone}</td>
                                                <c:if test="${sessionScope.userPermissions.contains('UPDATE_CUSTOMER')}">
                                                    <td>
                                                        <a class="btn btn-added"  id="btn-create-request"
                                                           href="${pageContext.request.contextPath}/UpdateCustomer?id=${c.id}">
                                                            <img src="assets/img/icons/edit.svg" alt="img">
                                                        </a>
                                                    </td></c:if>
                                                <c:if test="${sessionScope.userPermissions.contains('CREATE_SALE_ORDER')}">
                                                    <td>
                                                        <a class="btn btn-added"  id="btn-create-request"
                                                           href="${pageContext.request.contextPath}/CreateOder?id=${c.id}">
                                                            <img src="assets/img/icons/plus.svg" alt="img">
                                                        </a>
                                                    </td></c:if>
                                                </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                                <jsp:include page="/WEB-INF/common/pagination.jsp"/>
                                </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>


        <script src="assets/js/jquery-3.6.0.min.js"></script>

        <script src="assets/js/feather.min.js"></script>

        <script src="assets/js/jquery.slimscroll.min.js"></script>

        <script src="assets/js/jquery.dataTables.min.js"></script>
        <script src="assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <script src="assets/js/moment.min.js"></script>
        <script src="assets/js/bootstrap-datetimepicker.min.js"></script>

        <script src="assets/plugins/select2/js/select2.min.js"></script>

        <script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="assets/js/script.js"></script>
    </body>
</html>


