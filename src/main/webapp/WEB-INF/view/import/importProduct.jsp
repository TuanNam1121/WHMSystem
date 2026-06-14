<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Import Products">
    <meta name="keywords"
        content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>Import Products - Dreams Pos</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/importProduct.css">
</head>

<body>
    <div id="global-loader">
        <div class="whirly-loader"> </div>
    </div>

    <div class="main-wrapper">

        <div class="header">
            <div class="header-left active">
                <a href="index.html" class="logo"><img src="assets/img/logo.png" alt=""></a>
                <a href="index.html" class="logo-small"><img src="assets/img/logo-small.png" alt=""></a>
                <a id="toggle_btn" href="javascript:void(0);"></a>
            </div>

            <a id="mobile_btn" class="mobile_btn" href="#sidebar">
                <span class="bar-icon"><span></span><span></span><span></span></span>
            </a>

            <ul class="nav user-menu">
                <li class="nav-item">
                    <div class="top-nav-search">
                        <a href="javascript:void(0);" class="responsive-search"><i class="fa fa-search"></i></a>
                        <form action="#">
                            <div class="searchinputs">
                                <input type="text" placeholder="Search Here ...">
                                <div class="search-addon"><span><img src="assets/img/icons/closes.svg" alt="img"></span>
                                </div>
                            </div>
                            <a class="btn" id="searchdiv"><img src="assets/img/icons/search.svg" alt="img"></a>
                        </form>
                    </div>
                </li>

                <li class="nav-item dropdown has-arrow flag-nav">
                    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="javascript:void(0);"
                        role="button">
                        <img src="assets/img/flags/us1.png" alt="" height="20">
                    </a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/us.png" alt=""
                                height="16"> English</a>
                        <a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/fr.png" alt=""
                                height="16"> French</a>
                        <a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/es.png" alt=""
                                height="16"> Spanish</a>
                        <a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/de.png" alt=""
                                height="16"> German</a>
                    </div>
                </li>

                <li class="nav-item dropdown">
                    <a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
                        <img src="assets/img/icons/notification-bing.svg" alt="img"> <span
                            class="badge rounded-pill">2</span>
                    </a>
                    <div class="dropdown-menu notifications">
                        <div class="topnav-dropdown-header">
                            <span class="notification-title">Notifications</span>
                            <a href="javascript:void(0)" class="clear-noti"> Clear All </a>
                        </div>
                        <div class="noti-content">
                            <ul class="notification-list">
                                <li class="notification-message">
                                    <a href="activities.html">
                                        <div class="media d-flex">
                                            <span class="avatar flex-shrink-0"><img alt=""
                                                    src="assets/img/profiles/avator1.jpg"></span>
                                            <div class="media-body flex-grow-1">
                                                <p class="noti-details"><span class="noti-title">Manager</span> assigned
                                                    you <span class="noti-title">Import Request IR-001</span></p>
                                                <p class="noti-time"><span class="notification-time">5 mins ago</span>
                                                </p>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="topnav-dropdown-footer">
                            <a href="activities.html">View all Notifications</a>
                        </div>
                    </div>
                </li>

                <li class="nav-item dropdown has-arrow main-drop">
                    <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                        <span class="user-img"><img src="assets/img/profiles/avator1.jpg" alt="">
                            <span class="status online"></span></span>
                    </a>
                    <div class="dropdown-menu menu-drop-user">
                        <div class="profilename">
                            <div class="profileset">
                                <span class="user-img"><img src="assets/img/profiles/avator1.jpg" alt="">
                                    <span class="status online"></span></span>
                                <div class="profilesets">
                                    <h6>Nguyen Van A</h6>
                                    <h5>Warehouse Staff</h5>
                                </div>
                            </div>
                            <hr class="m-0">
                            <a class="dropdown-item" href="profile.html"> <i class="me-2" data-feather="user"></i> My
                                Profile</a>
                            <a class="dropdown-item" href="generalsettings.html"><i class="me-2"
                                    data-feather="settings"></i>Settings</a>
                            <hr class="m-0">
                            <a class="dropdown-item logout pb-0" href="signin.html"><img
                                    src="assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
                        </div>
                    </div>
                </li>
            </ul>

            <div class="dropdown mobile-user-menu">
                <a href="javascript:void(0);" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
                    aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a class="dropdown-item" href="profile.html">My Profile</a>
                    <a class="dropdown-item" href="generalsettings.html">Settings</a>
                    <a class="dropdown-item" href="signin.html">Logout</a>
                </div>
            </div>
        </div>


        <div class="sidebar" id="sidebar">
            <div class="sidebar-inner slimscroll">
                <div id="sidebar-menu" class="sidebar-menu">
                    <ul>
                        <li>
                            <a href="index.html"><img src="assets/img/icons/dashboard.svg" alt="img"><span>
                                    Dashboard</span> </a>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="assets/img/icons/product.svg" alt="img"><span>
                                    Product</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="productlist.html">Product List</a></li>
                                <li><a href="addproduct.html">Add Product</a></li>
                                <li><a href="categorylist.html">Category List</a></li>
                                <li><a href="brandlist.html">Brand List</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="assets/img/icons/purchase1.svg" alt="img"><span>
                                    Import Request</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="warehouse-import-request-list.html">My Import Requests</a></li>
                                <li><a href="import-product.html" class="active">Import Products</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="assets/img/icons/transfer1.svg" alt="img"><span>
                                    Export</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="create-export-order.html">Create Export Order</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="assets/img/icons/time.svg" alt="img"><span>
                                    Report</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="inventoryreport.html">Inventory Report</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="assets/img/icons/settings.svg" alt="img"><span>
                                    Settings</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="generalsettings.html">General Settings</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="page-wrapper">
            <div class="content">

                <!-- Page Header -->
                <div class="page-header">
                    <div class="page-title">
                        <h4>Import Products</h4>
                        <h6>Fill in serial numbers and pricing for imported products</h6>
                    </div>
                    <div class="page-btn">
                        <a href="warehouse-import-request-list.html" class="btn btn-cancel" id="btn-back-to-list">
                            <i class="fas fa-arrow-left me-2"></i>Back to List
                        </a>
                    </div>
                </div>

                <!-- Main Card -->
                <div class="card">
                    <div class="card-body">

                        <!-- Info Row: Purchase Request, Approved At, Handled By, Created By -->
                        <div class="row mb-3" id="import-info-row">
                            <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                <div class="import-info-box">
                                    <span class="info-box-label">Purchase Request</span>
                                    <div class="info-box-value" id="info-purchase-request">PR-2026-0007</div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                <div class="import-info-box">
                                    <span class="info-box-label">Approved At</span>
                                    <div class="info-box-value" id="info-approved-at">06/06/2026 10:15</div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                <div class="import-info-box">
                                    <span class="info-box-label">Handled By</span>
                                    <div class="info-box-value" id="info-handled-by">Not assigned</div>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 col-sm-6 col-12 mb-3">
                                <div class="import-info-box">
                                    <span class="info-box-label">Created By</span>
                                    <div class="info-box-value" id="info-created-by">Not assigned</div>
                                </div>
                            </div>
                        </div>

                        <!-- Supplier -->
                        <div class="supplier-field">
                            <label>Supplier</label>
                            <input type="text" value="FPT Supplier HCM" id="import-supplier" class="form-control"
                                style="border: 1px solid #dee2e6; border-radius: 6px;">
                        </div>

                        <!-- Summary Row: Total Items, Serials Filled, Total Payment -->
                        <div class="row mb-4" id="import-summary-row">
                            <div class="col-lg-4 col-md-4 col-sm-6 col-12 mb-3">
                                <div class="summary-box">
                                    <span class="summary-label">Total Items</span>
                                    <div class="summary-value" id="summary-total-items">120</div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 col-12 mb-3">
                                <div class="summary-box">
                                    <span class="summary-label">Serials Filled</span>
                                    <div class="summary-value" id="summary-serials-filled">10</div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-12 col-12 mb-3">
                                <div class="summary-box highlight">
                                    <span class="summary-label">Total Payment</span>
                                    <div class="summary-value" id="summary-total-payment">1,509,000,000 đ</div>
                                </div>
                            </div>
                        </div>

                        <!-- Product Groups -->
                        <div id="product-groups-container">

                            <!-- Product Group 1: Dell Latitude 5450 -->
                            <div class="product-group" id="product-group-1">
                                <div class="product-group-header" onclick="toggleProductGroup(1)">
                                    <div class="toggle-icon open" id="toggle-icon-1">
                                        <i class="fas fa-minus"></i>
                                    </div>
                                    <div>
                                        <span class="product-name">Dell Latitude 5450</span>
                                        <span class="serial-count" id="serial-count-1">5/100 serials filled</span>
                                    </div>
                                    <div class="product-meta">
                                        <span class="product-qty">100</span>
                                        <span class="product-unit">pcs</span>
                                        <div class="product-price">
                                            <input type="text" value="15000000" id="price-product-1"
                                                onchange="recalcTotal()">
                                        </div>
                                        <span class="product-currency">VND</span>
                                    </div>
                                </div>
                                <div class="product-group-body" id="product-body-1">
                                    <div class="serial-rows-container">
                                        <!-- Serial Row 1 -->
                                        <div class="serial-row">
                                            <span class="serial-index">1</span>
                                            <div class="serial-input">
                                                <input type="text" value="DLL5450-0001"
                                                    placeholder="Enter serial / IMEI" class="serial-field"
                                                    data-group="1">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="15000000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 2 -->
                                        <div class="serial-row">
                                            <span class="serial-index">2</span>
                                            <div class="serial-input">
                                                <input type="text" value="DLL5450-0002"
                                                    placeholder="Enter serial / IMEI" class="serial-field"
                                                    data-group="1">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="15000000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 3 -->
                                        <div class="serial-row">
                                            <span class="serial-index">3</span>
                                            <div class="serial-input">
                                                <input type="text" value="DLL5450-0003"
                                                    placeholder="Enter serial / IMEI" class="serial-field"
                                                    data-group="1">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="15000000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 4 -->
                                        <div class="serial-row">
                                            <span class="serial-index">4</span>
                                            <div class="serial-input">
                                                <input type="text" value="DLL5450-0004"
                                                    placeholder="Enter serial / IMEI" class="serial-field"
                                                    data-group="1">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="15000000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 5 -->
                                        <div class="serial-row">
                                            <span class="serial-index">5</span>
                                            <div class="serial-input">
                                                <input type="text" value="DLL5450-0005"
                                                    placeholder="Enter serial / IMEI" class="serial-field"
                                                    data-group="1">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="15000000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 6 (empty placeholder) -->
                                        <div class="serial-row">
                                            <span class="serial-index">6</span>
                                            <div class="serial-input">
                                                <input type="text" value="" placeholder="Enter serial / IMEI"
                                                    class="serial-field" data-group="1">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="15000000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Product Group 2: Logitech Mouse M650 -->
                            <div class="product-group" id="product-group-2">
                                <div class="product-group-header" onclick="toggleProductGroup(2)">
                                    <div class="toggle-icon" id="toggle-icon-2">
                                        <i class="fas fa-plus"></i>
                                    </div>
                                    <div>
                                        <span class="product-name">Logitech Mouse M650</span>
                                        <span class="serial-count" id="serial-count-2">5/20 serials filled</span>
                                    </div>
                                    <div class="product-meta">
                                        <span class="product-qty">20</span>
                                        <span class="product-unit">pcs</span>
                                        <div class="product-price">
                                            <input type="text" value="450000" id="price-product-2"
                                                onclick="event.stopPropagation();" onchange="recalcTotal()">
                                        </div>
                                        <span class="product-currency">VND</span>
                                    </div>
                                </div>
                                <div class="product-group-body" id="product-body-2" style="display: none;">
                                    <div class="serial-rows-container">
                                        <!-- Serial Row 1 -->
                                        <div class="serial-row">
                                            <span class="serial-index">1</span>
                                            <div class="serial-input">
                                                <input type="text" value="M650-0001" placeholder="Enter serial / IMEI"
                                                    class="serial-field" data-group="2">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="450000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 2 -->
                                        <div class="serial-row">
                                            <span class="serial-index">2</span>
                                            <div class="serial-input">
                                                <input type="text" value="M650-0002" placeholder="Enter serial / IMEI"
                                                    class="serial-field" data-group="2">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="450000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 3 -->
                                        <div class="serial-row">
                                            <span class="serial-index">3</span>
                                            <div class="serial-input">
                                                <input type="text" value="M650-0003" placeholder="Enter serial / IMEI"
                                                    class="serial-field" data-group="2">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="450000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 4 -->
                                        <div class="serial-row">
                                            <span class="serial-index">4</span>
                                            <div class="serial-input">
                                                <input type="text" value="M650-0004" placeholder="Enter serial / IMEI"
                                                    class="serial-field" data-group="2">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="450000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Serial Row 5 -->
                                        <div class="serial-row">
                                            <span class="serial-index">5</span>
                                            <div class="serial-input">
                                                <input type="text" value="M650-0005" placeholder="Enter serial / IMEI"
                                                    class="serial-field" data-group="2">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="450000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                        <!-- Empty serial rows for remaining -->
                                        <div class="serial-row">
                                            <span class="serial-index">6</span>
                                            <div class="serial-input">
                                                <input type="text" value="" placeholder="Enter serial / IMEI"
                                                    class="serial-field" data-group="2">
                                            </div>
                                            <span class="serial-qty">1</span>
                                            <span class="serial-unit">pcs</span>
                                            <div class="serial-price">
                                                <input type="text" value="450000">
                                            </div>
                                            <span class="serial-currency">VND</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <!-- Action Buttons -->
                        <div class="import-actions">
                            <a href="warehouse-import-request-list.html" class="btn btn-cancel-import"
                                id="btn-cancel-import">
                                Cancel
                            </a>
                            <a href="javascript:void(0);" class="btn btn-save-draft" id="btn-save-draft">
                                Save Draft
                            </a>
                            <a href="javascript:void(0);" class="btn btn-save-import" id="btn-save-import">
                                Save Import
                            </a>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>


    <script src="assets/js/jquery-3.6.0.min.js"></script>
    <script src="assets/js/feather.min.js"></script>
    <script src="assets/js/jquery.slimscroll.min.js"></script>
    <script src="assets/js/bootstrap.bundle.min.js"></script>
    <script src="assets/plugins/select2/js/select2.min.js"></script>
    <script src="assets/js/moment.min.js"></script>
    <script src="assets/js/bootstrap-datetimepicker.min.js"></script>
    <script src="assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
    <script src="assets/plugins/sweetalert/sweetalerts.min.js"></script>
    <script src="assets/js/script.js"></script>

    <script>
        // ===== Toggle Product Group (Expand/Collapse) =====
        function toggleProductGroup(groupId) {
            var body = document.getElementById('product-body-' + groupId);
            var icon = document.getElementById('toggle-icon-' + groupId);

            if (body.style.display === 'none') {
                body.style.display = 'block';
                icon.classList.add('open');
                icon.innerHTML = '<i class="fas fa-minus"></i>';
            } else {
                body.style.display = 'none';
                icon.classList.remove('open');
                icon.innerHTML = '<i class="fas fa-plus"></i>';
            }
        }

        // ===== Update serial filled counts =====
        function updateSerialCounts() {
            var groups = document.querySelectorAll('.product-group');
            var totalFilled = 0;

            groups.forEach(function (group, index) {
                var groupId = index + 1;
                var serialFields = group.querySelectorAll('.serial-field');
                var filled = 0;
                serialFields.forEach(function (field) {
                    if (field.value.trim() !== '') {
                        filled++;
                    }
                });

                var total = serialFields.length;
                var countEl = document.getElementById('serial-count-' + groupId);
                if (countEl) {
                    countEl.textContent = filled + '/' + total + ' serials filled';
                }
                totalFilled += filled;
            });

            document.getElementById('summary-serials-filled').textContent = totalFilled;
        }

        // ===== Recalculate Total Payment =====
        function recalcTotal() {
            // Product 1: Dell Latitude 5450 - 100 pcs
            var price1 = parseInt(document.getElementById('price-product-1').value) || 0;
            var qty1 = 100;

            // Product 2: Logitech Mouse M650 - 20 pcs
            var price2 = parseInt(document.getElementById('price-product-2').value) || 0;
            var qty2 = 20;

            var total = (price1 * qty1) + (price2 * qty2);
            document.getElementById('summary-total-payment').textContent = total.toLocaleString('vi-VN') + ' đ';
        }

        $(document).ready(function () {

            // Listen for serial field changes
            $(document).on('input', '.serial-field', function () {
                updateSerialCounts();
            });

            // Prevent clicks on inputs inside header from toggling
            $('.product-group-header input').on('click', function (e) {
                e.stopPropagation();
            });

            // ===== Save Draft =====
            $('#btn-save-draft').on('click', function () {
                Swal.fire({
                    title: 'Save as Draft?',
                    html: '<p>Your progress will be saved. You can continue filling in serials later.</p>',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#EA5455',
                    cancelButtonColor: '#6c757d',
                    confirmButtonText: '<i class="fas fa-save me-1"></i> Save Draft',
                    cancelButtonText: 'Cancel'
                }).then(function (result) {
                    if (result.isConfirmed) {
                        Swal.fire({
                            icon: 'success',
                            title: 'Draft Saved!',
                            html: '<p>Your import draft has been saved successfully.</p>',
                            confirmButtonColor: '#FF9F43'
                        });
                    }
                });
            });

            // ===== Save Import =====
            $('#btn-save-import').on('click', function () {
                // Check if all serials are filled
                var allFilled = true;
                var emptyCount = 0;
                $('.serial-field').each(function () {
                    if ($(this).val().trim() === '') {
                        allFilled = false;
                        emptyCount++;
                    }
                });

                if (!allFilled) {
                    Swal.fire({
                        title: 'Incomplete Serials',
                        html: '<p>There are <strong>' + emptyCount + '</strong> serial fields that are still empty.</p><p>Do you want to continue saving the import?</p>',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#28C76F',
                        cancelButtonColor: '#6c757d',
                        confirmButtonText: '<i class="fas fa-check me-1"></i> Yes, Save Import',
                        cancelButtonText: 'Cancel'
                    }).then(function (result) {
                        if (result.isConfirmed) {
                            completeImport();
                        }
                    });
                } else {
                    Swal.fire({
                        title: 'Save Import?',
                        html: '<div style="text-align:left; line-height:1.8;">' +
                            '<p>You are about to <strong style="color:#28C76F;">save</strong> this import.</p>' +
                            '<p><strong>Purchase Request:</strong> PR-2026-0007</p>' +
                            '<p><strong>Supplier:</strong> ' + $('#import-supplier').val() + '</p>' +
                            '<p><strong>Total Payment:</strong> ' + $('#summary-total-payment').text() + '</p>' +
                            '<hr>' +
                            '<p class="text-muted">Products will be added to inventory and stock will be updated.</p>' +
                            '</div>',
                        icon: 'question',
                        showCancelButton: true,
                        confirmButtonColor: '#28C76F',
                        cancelButtonColor: '#6c757d',
                        confirmButtonText: '<i class="fas fa-check-double me-1"></i> Yes, Save Import!',
                        cancelButtonText: 'Cancel'
                    }).then(function (result) {
                        if (result.isConfirmed) {
                            completeImport();
                        }
                    });
                }
            });

            function completeImport() {
                Swal.fire({
                    icon: 'success',
                    title: 'Import Saved!',
                    html: '<p>Import for <strong>PR-2026-0007</strong> has been saved successfully.</p><p>Inventory has been updated.</p>',
                    confirmButtonColor: '#28C76F'
                }).then(function () {
                    window.location.href = 'warehouse-import-request-list.html';
                });
            }
        });
    </script>
</body>

</html>