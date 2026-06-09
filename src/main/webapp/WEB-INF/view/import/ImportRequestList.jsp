<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords"
              content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>LWMS</title>

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

            <div id="global-loader">
                <div class="whirly-loader"></div>
            </div>
            <div class="main-wrapper">
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Import Request List</h4>
                                <h6>View All Import Request</h6>
                                ${message}
                            </div>
                        </div>

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
                                <form action="productlist" method="get">
                                    <div class="card mb-0" id="filter_inputs">
                                        <div class="card-body pb-0">
                                            <div class="row">
                                                <div class="col-lg-12 col-sm-12">
                                                    <div class="row">

                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <input type="text" name="receiptId" placeholder="Search Receipt ID">
                                                            </div>
                                                        </div>

                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <input type="text" name="purchaseRequestId" placeholder="Search Purchase Request ID">
                                                            </div>
                                                        </div>

                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <div class="form-group">
                                                                    <input type="text" name="supplierName" placeholder="Search Supplier Name">
                                                                </div>
                                                            </div>
                                                        </div>


                                                        <div class="col-lg col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <select class="select" name="sortBy">
                                                                    <option value="">Sort By</option>
                                                                    <option value="skuAZ">Latest</option>
                                                                    <option value="nameAZ">Earliest</option>
                                                                </select>
                                                            </div>
                                                        </div>

                                                        <div class="col-lg-1 col-sm-6 col-12">
                                                            <div class="form-group">
                                                                <button type="submit" class="btn btn-filters ms-auto"
                                                                        style="border: none; padding: 0;">
                                                                    <img src="assets/img/icons/search-whites.svg" alt="img">
                                                                </button>
                                                                <button type="reset" class="btn btn-filters ms-auto"
                                                                        style="border: none; padding: 0;">
                                                                    <img src="assets/img/icons/delete1.svg" alt="img">
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- GoodReceiptID | PurchaseRequestID | CreatedBy | Created At | Total Item | Action -->
                                <div class="table-responsive">
                                    <table class="table  datanew">
                                        <thead>
                                            <tr>
                                                <th>Receipt ID</th>
                                                <th>Purchase Request ID</th>
                                                <th>Created By</th>
                                                <th>Created At</th>
                                                <th>Total Items</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${list}" var="p">
                                            <tr>
                                                <td>GR-${p.goodReceiptId}</td>
                                                <td>PR-${p.purchaseRequestId.name}</td>
                                                <td>${p.createdBy}</td>
                                                <td>${p.createdAt}</td>
                                                <td>${p.totalItem}</td>
                                                <td>
                                                    <a class="me-3" href="UpdateProduct?productid=${p.productId}">
                                                        <img src="assets/img/icons/eye.svg" alt="img">
                                                    </a>
                                                    <a class="me-3" href="ImportProduct?goodReceipt=${p.goodReceiptId}">
                                                        <img src="assets/img/icons/eye.svg" alt="img">
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
