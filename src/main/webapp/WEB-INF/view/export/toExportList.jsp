<%--
  Created by IntelliJ IDEA.
  User: tung
  Date: 8/6/26
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Dreams Pos admin template</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">

    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">

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

    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/sidebar.jsp"></jsp:include>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>To Export List</h4>
                    <h6>Orders need to be exported.</h6>
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
                                <a class="btn btn-searchset">
                                    <img src="assets/img/icons/search-white.svg" alt="img">
                                </a>
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
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <input type="text" class="datetimepicker cal-icon" placeholder="Choose Date">
                                    </div>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <input type="text" placeholder="Enter Reference">
                                    </div>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <select class="select">
                                            <option>Choose Status</option>
                                            <option>Inprogress</option>
                                            <option>Complete</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-1 col-sm-6 col-12 ms-auto">
                                    <div class="form-group">
                                        <a class="btn btn-filters ms-auto"><img src="assets/img/icons/search-whites.svg"
                                                                                alt="img"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table  datanew">
                            <thead>
                            <tr>
                                <th>Date</th>
                                <th>Reference</th>
                                <th>To</th>
                                <th>Items</th>
                                <th>Grand total</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>8 June 2026</td>
                                <td>EX001</td>
                                <td>CellphoneS</td>
                                <td>20</td>
                                <td>100000000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>08 June 2026</td>
                                <td>EX002</td>
                                <td>FPT Shop HBT</td>
                                <td>15</td>
                                <td>120,000,000</td>
                                <td><span class="badges bg-lightgrey">New</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                        <c:if test="${'New' eq 'New'}">
                                            <a href="exportProduct?orderId=?">
                                                <button type="button" class="btn btn-primary btn-sm">Process</button>
                                            </a>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>08 June 2026</td>
                                <td>EX003</td>
                                <td>Thế Giới Di Động</td>
                                <td>50</td>
                                <td>450,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>07 June 2026</td>
                                <td>EX004</td>
                                <td>Phong Vũ Computer</td>
                                <td>8</td>
                                <td>185,000,000</td>
                                <td><span class="badges bg-lightyellow">Pending</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>07 June 2026</td>
                                <td>EX005</td>
                                <td>Hoàng Hà Mobile</td>
                                <td>35</td>
                                <td>145,000,000</td>
                                <td><span class="badges bg-lightred">Canceled</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>06 June 2026</td>
                                <td>EX006</td>
                                <td>An Phát Computer</td>
                                <td>12</td>
                                <td>96,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>06 June 2026</td>
                                <td>EX007</td>
                                <td>GearVN HCM</td>
                                <td>22</td>
                                <td>310,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>05 June 2026</td>
                                <td>EX008</td>
                                <td>ShopDunk Hà Nội</td>
                                <td>10</td>
                                <td>260,000,000</td>
                                <td><span class="badges bg-lightgrey">New</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                        <c:if test="${'New' eq 'New'}">
                                            <button type="button" class="btn btn-primary btn-sm">Process</button>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>05 June 2026</td>
                                <td>EX009</td>
                                <td>CellphoneS Thái Hà</td>
                                <td>18</td>
                                <td>88,500,000</td>
                                <td><span class="badges bg-lightyellow">Pending</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>04 June 2026</td>
                                <td>EX010</td>
                                <td>Vĩnh Phát Mobile</td>
                                <td>25</td>
                                <td>115,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>04 June 2026</td>
                                <td>EX011</td>
                                <td>HACOM Đống Đa</td>
                                <td>14</td>
                                <td>168,000,000</td>
                                <td><span class="badges bg-lightred">Canceled</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>03 June 2026</td>
                                <td>EX012</td>
                                <td>FPT Shop Cầu Giấy</td>
                                <td>30</td>
                                <td>210,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>03 June 2026</td>
                                <td>EX013</td>
                                <td>Di Động Việt</td>
                                <td>40</td>
                                <td>320,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>02 June 2026</td>
                                <td>EX014</td>
                                <td> thinkPro Đội Cấn</td>
                                <td>5</td>
                                <td>135,000,000</td>
                                <td><span class="badges bg-lightyellow">Pending</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>02 June 2026</td>
                                <td>EX015</td>
                                <td>Hoàng Hà Trần Quang Khải</td>
                                <td>19</td>
                                <td>76,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>01 June 2026</td>
                                <td>EX016</td>
                                <td>Bách Khoa Computer</td>
                                <td>11</td>
                                <td>54,000,000</td>
                                <td><span class="badges bg-lightgrey">New</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                        <c:if test="${'New' eq 'New'}">
                                            <button type="button" class="btn btn-primary btn-sm">Process</button>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>01 June 2026</td>
                                <td>EX017</td>
                                <td>Phong Vũ Nguyễn Thị Minh Khai</td>
                                <td>16</td>
                                <td>420,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>31 May 2026</td>
                                <td>EX018</td>
                                <td>Minh Tuấn Mobile</td>
                                <td>21</td>
                                <td>295,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>31 May 2026</td>
                                <td>EX019</td>
                                <td>Thế Giới Số 247</td>
                                <td>7</td>
                                <td>38,000,000</td>
                                <td><span class="badges bg-lightred">Canceled</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td>30 May 2026</td>
                                <td>EX020</td>
                                <td>Vua Kho Lẻ miền Bắc</td>
                                <td>100</td>
                                <td>1,250,000,000</td>
                                <td><span class="badges bg-lightgreen">Completed</span></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <a class="me-3" href="product-details.html">
                                            <img src="assets/img/icons/eye.svg" alt="img">
                                        </a>
                                    </div>
                                </td>
                            </tr>
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