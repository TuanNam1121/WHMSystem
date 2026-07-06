<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Import History</h4>
                            <h6>Manage your import history</h6>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">

                            <form action="ImportHistory" method="get">
                                <div class="card mb-0" id="filter_inputs" style="display: block !important;">
                                    <div class="card-body pb-0">
                                        <div class="row">
                                            <div class="col-lg-12 col-sm-12">
                                                <div class="row">

                                                    <div class="col-lg col-sm-6 col-12">
                                                        <div class="form-group">
                                                            <input type="text" name="keyword" placeholder="Search Product Name or Serial" value="${param.keyword}">
                                                    </div>
                                                </div>

                                                <div class="col-lg col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <input type="text" name="purchaseid" placeholder="Search Purchase Request ID or Receipt ID" value="${param.purchaseid}">
                                                    </div>
                                                </div>


                                                <div class="col-lg col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <select class="select" name="supplierid">
                                                            <option value="">Choose Supplier</option>
                                                            <c:forEach items="${sessionScope.supplier}" var="c">
                                                                <option value="${c.supplierId}">${c.supplierName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-lg col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <select class="select" name="processedby">
                                                            <option value="">Choose Processor</option>
                                                            <c:forEach items="${sessionScope.userList}" var="c">
                                                                <option value="${c.id}">${c.fullName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-lg col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <select class="select" name="sortBy">
                                                            <option value="">Date Sort</option>                                                        
                                                            <option value="date_latest">Latest</option>
                                                            <option value="date_earliest">Earliest</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-lg-1 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <button type="submit" class="btn btn-filters ms-auto"
                                                                style="border: none; padding: 0;">
                                                            <img src="assets/img/icons/search-whites.svg" alt="img">
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>

                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Receipt ID</th>
                                        <th>Purchase Request ID</th>
                                        <th>Supplier</th>
                                        <th>Import By</th>
                                        <th>Items</th>
                                        <th>Total</th>
                                        <th>Status</th>
                                        <th>Received At</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${list}" var="i">
                                        <tr>
                                            <td>GR-${i.receiptId}</td>
                                            <td>PR-${i.purchaseRequestId}</td>
                                            <td>${i.supplier}</td>
                                            <td>${i.importBy}</td>
                                            <td>${i.items}</td>
                                            <td><fmt:formatNumber value="${i.total}" pattern="#,###"/></td>
                                            <td>
                                                <c:choose>
                                                    <c:when
                                                        test="${i.status == 'NEW'}">
                                                        <span class="badges bg-lightyellow">${i.status}</span>
                                                    </c:when>
                                                    <c:when
                                                        test="${i.status == 'COMPLETED'}">
                                                        <span class="badges bg-lightgreen">${i.status}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badges bg-lightgrey">${i.status}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${i.completedAt}</td>
                                            <td>
                                                <a class="me-3" href="ImportHistoryDetail?receiptId=${i.receiptId}">
                                                    <img src="assets/img/icons/edit.svg" alt="img">
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <jsp:include page="/WEB-INF/common/pagination.jsp"/>
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

        <script src="assets/plugins/select2/js/select2.min.js"></script>

        <script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="assets/js/script.js"></script>
    </body>
</html>