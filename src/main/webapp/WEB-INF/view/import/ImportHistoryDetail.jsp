
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Import History Detail">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Import History Detail - Dreams Pos</title>

        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" href="assets/css/animate.css">
        <link rel="stylesheet" href="assets/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <style>
            .info-card {
                box-shadow: none;
                border: 1px solid #e9ecef;
                border-radius: 8px;
                background-color: #fafbfe;
            }
            .info-card .card-body {
                padding: 15px;
            }
            .info-label {
                color: #888;
                font-size: 13px;
                margin-bottom: 5px;
                display: block;
            }
            .info-value {
                margin: 0;
                font-weight: 700;
                color: #333;
                font-size: 16px;
            }
            .status-badge {
                color: #1a9f53;
                background-color: #e5f8ed;
                padding: 6px 15px;
                border-radius: 4px;
                font-weight: 700;
                font-size: 13px;
                letter-spacing: 0.5px;
            }
            .table-container {
                border: 1px solid #e9ecef;
                border-radius: 8px;
                overflow: hidden;
                margin-top: 20px;
            }
            .table-container table {
                margin-bottom: 0;
            }
            .table-container thead th {
                background-color: #fafbfe;
                color: #6c757d;
                font-weight: 600;
                border-bottom: 1px solid #e9ecef;
                padding: 15px;
            }
            .table-container tbody td {
                padding: 15px;
                vertical-align: middle;
                border-bottom: 1px solid #f5f5f5;
            }
            .table-container tbody tr:last-child td {
                border-bottom: none;
            }
        </style>
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
                                    <div class="search-addon"><span><img src="assets/img/icons/closes.svg" alt="img"></span></div>
                                </div>
                                <a class="btn" id="searchdiv"><img src="assets/img/icons/search.svg" alt="img"></a>
                            </form>
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
                                <a class="dropdown-item" href="profile.html"> <i class="me-2" data-feather="user"></i> My Profile</a>
                                <a class="dropdown-item" href="generalsettings.html"><i class="me-2" data-feather="settings"></i>Settings</a>
                                <hr class="m-0">
                                <a class="dropdown-item logout pb-0" href="signin.html"><img src="assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
                            </div>
                        </div>
                    </li>
                </ul>

                <div class="dropdown mobile-user-menu">
                    <a href="javascript:void(0);" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
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
                                <a href="index.html"><img src="assets/img/icons/dashboard.svg" alt="img"><span> Dashboard</span> </a>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="assets/img/icons/product.svg" alt="img"><span> Product</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="productlist.html">Product List</a></li>
                                    <li><a href="addproduct.html">Add Product</a></li>
                                    <li><a href="categorylist.html">Category List</a></li>
                                    <li><a href="brandlist.html">Brand List</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="assets/img/icons/purchase1.svg" alt="img"><span> Import Request</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="warehouse-import-request-list.html">My Import Requests</a></li>
                                    <li><a href="import-history-list.html" class="active">Import History</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="assets/img/icons/transfer1.svg" alt="img"><span> Export</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="create-export-order.html">Create Export Order</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="assets/img/icons/time.svg" alt="img"><span> Report</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="inventoryreport.html">Inventory Report</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="assets/img/icons/settings.svg" alt="img"><span> Settings</span> <span class="menu-arrow"></span></a>
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
                    <div class="page-header d-flex justify-content-between align-items-center border-bottom pb-3 mb-4">
                        <div class="page-title mb-0">
                            <h4 class="mb-0" style="font-size: 22px; font-weight: 700; color: #333;">Import History Detail</h4>
                        </div>
                        <div class="page-btn">
                            <span class="status-badge">COMPLETED</span>
                        </div>
                    </div>

                    <div class="row mb-4">
                        <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                            <div class="card info-card h-100">
                                <div class="card-body">
                                    <span class="info-label">Receipt</span>
                                    <h5 class="info-value">GR-2026-0031</h5>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                            <div class="card info-card h-100">
                                <div class="card-body">
                                    <span class="info-label">Purchase Request</span>
                                    <h5 class="info-value">PR-2026-0007</h5>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                            <div class="card info-card h-100">
                                <div class="card-body">
                                    <span class="info-label">Supplier</span>
                                    <h5 class="info-value">FPT Supplier HCM</h5>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                            <div class="card info-card h-100">
                                <div class="card-body">
                                    <span class="info-label">Handled By</span>
                                    <h5 class="info-value">Not assigned</h5>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                            <div class="card info-card h-100">
                                <div class="card-body">
                                    <span class="info-label">Received At</span>
                                    <h5 class="info-value">06/06/2026 14:20</h5>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-container bg-white">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Serial / IMEI</th>
                                        <th>Unit</th>
                                        <th class="text-end">Imported Price</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">DLL5450-0001</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">DLL5450-0002</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">DLL5450-0003</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">DLL5450-0004</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">DLL5450-0005</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">(empty)</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">(empty)</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Dell Latitude 5450</td>
                                        <td style="color: #6c757d;">(empty)</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">15,000,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Logitech Mouse M650</td>
                                        <td style="color: #6c757d;">M650-0001</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">450,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Logitech Mouse M650</td>
                                        <td style="color: #6c757d;">M650-0002</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">450,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Logitech Mouse M650</td>
                                        <td style="color: #6c757d;">M650-0003</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">450,000 d</td>
                                    </tr>
                                    <tr>
                                        <td style="font-weight: 600; color: #333;">Logitech Mouse M650</td>
                                        <td style="color: #6c757d;">M650-0004</td>
                                        <td style="color: #6c757d;">pcs</td>
                                        <td class="text-end" style="color: #6c757d;">450,000 d</td>
                                    </tr>
                                </tbody>
                            </table>
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

    </body>
</html>
