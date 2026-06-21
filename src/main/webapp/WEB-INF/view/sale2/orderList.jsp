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
                                <h4>ORDER LIST</h4>
                            </div>
                            <div class="page-btn">
                                <a href="CustomerList" class="btn btn-added" id="btn-create-request">
                                    <img src="assets/img/icons/plus.svg" alt="img">Create Order
                                </a>
                            </div>
                        </div>

                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong>${sessionScope.message}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% session.removeAttribute("message"); %>
                    </c:if>
                    <c:if test="${not empty sessionScope.error}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                            <strong>${sessionScope.error}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% session.removeAttribute("error"); %>
                    </c:if>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div class="search-path">
                                        <a class="btn btn-filter" id="filter_search">
                                            <img src="assets/img/icons/filter.svg" alt="img">
                                            <span><img src="assets/img/icons/closes.svg" alt="img"></span>
                                        </a>
                                    </div>
                                    <div class="search-input">
                                        <a class="btn btn-searchset"><img src="assets/img/icons/search-white.svg" alt="img"></a>
                                    </div>
                                </div>
                                <div class="wordset">
                                    <ul>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img
                                                    src="assets/img/icons/pdf.svg" alt="img"></a>
                                        </li>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img
                                                    src="assets/img/icons/excel.svg" alt="img"></a>
                                        </li>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img
                                                    src="assets/img/icons/printer.svg" alt="img"></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                            <div class="card" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">  


                                        <form style="display:flex" action="${pageContext.request.contextPath}/OrderList" method="post">
                                            <div class="col-lg-3 col-sm-6 col-12">

                                                <div class="form-group">
                                                    <input name="searchName" type="text" placeholder="Enter customer name" value="${searchName}" id="filter-code">
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select name="searchStatus" class="select" id="filter-status">
                                                        <option ${searchStatus==null?'selected':''} value="ALL">Choose Status</option>
                                                        <option ${searchStatus=='NEW'?'selected':''} value="NEW">New</option>
                                                        <option ${searchStatus=='DOING'?'selected':''} value="DOING">On going</option>
                                                        <option ${searchStatus=='COMPLETED'?'selected':''} value="COMPLETED">Completed</option>
                                                        <option ${searchStatus=='CANCELLED'?'selected':''} value="CANCELLED">Cancelled</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="submit" value="Search"
                                                           id="filter-date">
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="table-responsive">
                                <fmt:setLocale value="en_US"/>
                                <table class="table datanew" >
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>Customer name</th>
                                            <th>total price</th>
                                            <th>Note</th>
                                            <th>Order date</th>
                                            <th>Created by</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${orders}" var="o">
                                            <tr>
                                                <td>${o.id}</td>
                                                <td>${o.customer}</td>
                                                <td>
                                                    <fmt:formatNumber
                                                        value="${o.totalPrice}"
                                                        pattern="#,##0.00"/>
                                                </td>
                                                <td style="max-width: 200px; overflow-x: auto">${o.note}</td>
                                                <td>${o.orderDate}</td>
                                                <td>${o.creater}</td>
                                                <td>${o.status}</td>
                                                <td>
                                                    <a class="me-3"
                                                       href="${pageContext.request.contextPath}/OrderDetail?id=${o.id}&action=view">
                                                        <img src="assets/img/icons/eye.svg" alt="img">
                                                    </a>
                                                    <c:if test="${o.status != 'COMPLETED'}">
                                                    <c:if test="${o.status != 'CANCELLED'}">
                                                        <a class="me-3"
                                                           href="${pageContext.request.contextPath}/OrderDetail2?id=${o.id}&action=update">
                                                            <img src="assets/img/icons/edit.svg" alt="img">
                                                        </a>
                                                    </c:if>
                                                    </c:if>
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


