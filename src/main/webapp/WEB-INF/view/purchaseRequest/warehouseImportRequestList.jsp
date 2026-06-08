<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
<meta name="description" content="POS - Warehouse Staff Import Request List">
<meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
<meta name="author" content="Dreamguys - Bootstrap Admin Template">
<meta name="robots" content="noindex, nofollow">
<title>Import Request List - Dreams Pos</title>

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

<li class="nav-item dropdown has-arrow flag-nav">
<a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="javascript:void(0);" role="button">
<img src="assets/img/flags/us1.png" alt="" height="20">
</a>
<div class="dropdown-menu dropdown-menu-right">
<a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/us.png" alt="" height="16"> English</a>
<a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/fr.png" alt="" height="16"> French</a>
<a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/es.png" alt="" height="16"> Spanish</a>
<a href="javascript:void(0);" class="dropdown-item"><img src="assets/img/flags/de.png" alt="" height="16"> German</a>
</div>
</li>

<li class="nav-item dropdown">
<a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
<img src="assets/img/icons/notification-bing.svg" alt="img"> <span class="badge rounded-pill">2</span>
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
<span class="avatar flex-shrink-0"><img alt="" src="assets/img/profiles/avator1.jpg"></span>
<div class="media-body flex-grow-1">
<p class="noti-details"><span class="noti-title">Manager</span> assigned you a new <span class="noti-title">Import Request IR-001</span></p>
<p class="noti-time"><span class="notification-time">5 mins ago</span></p>
</div>
</div>
</a>
</li>
<li class="notification-message">
<a href="activities.html">
<div class="media d-flex">
<span class="avatar flex-shrink-0"><img alt="" src="assets/img/profiles/avator1.jpg"></span>
<div class="media-body flex-grow-1">
<p class="noti-details"><span class="noti-title">Manager</span> assigned you a new <span class="noti-title">Import Request IR-004</span></p>
<p class="noti-time"><span class="notification-time">2 hours ago</span></p>
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
<li><a href="warehouse-import-request-list.html" class="active">My Import Requests</a></li>
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
<div class="page-header">
<div class="page-title">
<h4>IMPORT REQUEST LIST</h4>
<h6>View import requests assigned to you</h6>
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
<li><a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img src="assets/img/icons/pdf.svg" alt="img"></a></li>
<li><a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img src="assets/img/icons/excel.svg" alt="img"></a></li>
<li><a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img src="assets/img/icons/printer.svg" alt="img"></a></li>
</ul>
</div>
</div>

<div class="card" id="filter_inputs">
<div class="card-body pb-0">
<div class="row">
<div class="col-lg-3 col-sm-6 col-12">
<div class="form-group">
<input type="text" placeholder="Enter Request Code" id="filter-code">
</div>
</div>
<div class="col-lg-3 col-sm-6 col-12">
<div class="form-group">
<select class="select" id="filter-status">
<option>Choose Status</option>
<option>New</option>
<option>Doing</option>
<option>Completed</option>
</select>
</div>
</div>
<div class="col-lg-3 col-sm-6 col-12">
<div class="form-group">
<input type="text" class="datetimepicker cal-icon" placeholder="Choose Date" id="filter-date">
</div>
</div>
<div class="col-lg-3 col-sm-6 col-12">
<div class="form-group">
<a class="btn btn-filters ms-auto" id="btn-apply-filter"><img src="assets/img/icons/search-whites.svg" alt="img"></a>
</div>
</div>
</div>
</div>
</div>

<div class="table-responsive">
<table class="table datanew" id="import-request-table">
<thead>
<tr>
<th>
<label class="checkboxs">
<input type="checkbox" id="select-all">
<span class="checkmarks"></span>
</label>
</th>
<th>Request Code</th>
<th>Note</th>
<th>Status</th>
<th>Created At</th>
<th>Action</th>
</tr>
</thead>
<tbody>
<tr>
<td>
<label class="checkboxs"><input type="checkbox"><span class="checkmarks"></span></label>
</td>
<td class="text-bolds">IR-001</td>
<td>Import Apple products - approved from PR-001 by Manager</td>
<td><span class="badges bg-lightyellow">New</span></td>
<td>06 Jun 2026</td>
<td>
<a class="btn btn-sm btn-outline-primary" href="warehouse-import-request-detail.html" id="btn-view-ir001">
<i class="fas fa-eye me-1"></i> View Detail
</a>
</td>
</tr>
<tr>
<td>
<label class="checkboxs"><input type="checkbox"><span class="checkmarks"></span></label>
</td>
<td class="text-bolds">IR-002</td>
<td>Import Samsung accessories - approved from PR-002 by Manager</td>
<td><span class="badges bg-lightpurple">Doing</span></td>
<td>05 Jun 2026</td>
<td>
<a class="btn btn-sm btn-outline-info" href="warehouse-import-request-detail.html?status=doing" id="btn-view-ir002">
<i class="fas fa-eye me-1"></i> View Detail
</a>
</td>
</tr>
<tr>
<td>
<label class="checkboxs"><input type="checkbox"><span class="checkmarks"></span></label>
</td>
<td class="text-bolds">IR-003</td>
<td>Import laptop peripherals and cables - approved from PR-003</td>
<td><span class="badges bg-lightgreen">Completed</span></td>
<td>03 Jun 2026</td>
<td>
<a class="btn btn-sm btn-outline-secondary" href="warehouse-import-request-detail.html?status=completed" id="btn-view-ir003">
<i class="fas fa-eye me-1"></i> View Detail
</a>
</td>
</tr>
<tr>
<td>
<label class="checkboxs"><input type="checkbox"><span class="checkmarks"></span></label>
</td>
<td class="text-bolds">IR-004</td>
<td>Import printer ink cartridges HP & Canon - approved from PR-005</td>
<td><span class="badges bg-lightyellow">New</span></td>
<td>28 May 2026</td>
<td>
<a class="btn btn-sm btn-outline-primary" href="warehouse-import-request-detail.html" id="btn-view-ir004">
<i class="fas fa-eye me-1"></i> View Detail
</a>
</td>
</tr>
<tr>
<td>
<label class="checkboxs"><input type="checkbox"><span class="checkmarks"></span></label>
</td>
<td class="text-bolds">IR-005</td>
<td>Import USB-C hubs and docking stations - approved from PR-007</td>
<td><span class="badges bg-lightpurple">Doing</span></td>
<td>25 May 2026</td>
<td>
<a class="btn btn-sm btn-outline-info" href="warehouse-import-request-detail.html?status=doing" id="btn-view-ir005">
<i class="fas fa-eye me-1"></i> View Detail
</a>
</td>
</tr>
<tr>
<td>
<label class="checkboxs"><input type="checkbox"><span class="checkmarks"></span></label>
</td>
<td class="text-bolds">IR-006</td>
<td>Import iPhone cases and screen protectors - approved from PR-008</td>
<td><span class="badges bg-lightgreen">Completed</span></td>
<td>20 May 2026</td>
<td>
<a class="btn btn-sm btn-outline-secondary" href="warehouse-import-request-detail.html?status=completed" id="btn-view-ir006">
<i class="fas fa-eye me-1"></i> View Detail
</a>
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
