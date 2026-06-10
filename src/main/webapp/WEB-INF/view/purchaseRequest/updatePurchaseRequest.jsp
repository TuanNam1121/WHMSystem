<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
<meta name="description" content="POS - Update Purchase Request for Salesman">
<meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
<meta name="author" content="Dreamguys - Bootstrap Admin Template">
<meta name="robots" content="noindex, nofollow">
<title>Update Purchase Request - Dreams Pos</title>

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
<a href="index.html" class="logo">
<img src="assets/img/logo.png" alt="">
</a>
<a href="index.html" class="logo-small">
<img src="assets/img/logo-small.png" alt="">
</a>
<a id="toggle_btn" href="javascript:void(0);">
</a>
</div>

<a id="mobile_btn" class="mobile_btn" href="#sidebar">
<span class="bar-icon">
<span></span>
<span></span>
<span></span>
</span>
</a>

<ul class="nav user-menu">

<li class="nav-item">
<div class="top-nav-search">
<a href="javascript:void(0);" class="responsive-search">
<i class="fa fa-search"></i>
</a>
<form action="#">
<div class="searchinputs">
<input type="text" placeholder="Search Here ...">
<div class="search-addon">
<span><img src="assets/img/icons/closes.svg" alt="img"></span>
</div>
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
<a href="javascript:void(0);" class="dropdown-item">
<img src="assets/img/flags/us.png" alt="" height="16"> English
</a>
<a href="javascript:void(0);" class="dropdown-item">
<img src="assets/img/flags/fr.png" alt="" height="16"> French
</a>
<a href="javascript:void(0);" class="dropdown-item">
<img src="assets/img/flags/es.png" alt="" height="16"> Spanish
</a>
<a href="javascript:void(0);" class="dropdown-item">
<img src="assets/img/flags/de.png" alt="" height="16"> German
</a>
</div>
</li>


<li class="nav-item dropdown">
<a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
<img src="assets/img/icons/notification-bing.svg" alt="img"> <span class="badge rounded-pill">4</span>
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
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-02.jpg">
</span>
<div class="media-body flex-grow-1">
<p class="noti-details"><span class="noti-title">John Doe</span> added new task <span class="noti-title">Patient appointment booking</span></p>
<p class="noti-time"><span class="notification-time">4 mins ago</span></p>
</div>
</div>
</a>
</li>
<li class="notification-message">
<a href="activities.html">
<div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-03.jpg">
</span>
<div class="media-body flex-grow-1">
<p class="noti-details"><span class="noti-title">Tarah Shropshire</span> changed the task name <span class="noti-title">Appointment booking with payment gateway</span></p>
<p class="noti-time"><span class="notification-time">6 mins ago</span></p>
</div>
</div>
</a>
</li>
<li class="notification-message">
<a href="activities.html">
<div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-06.jpg">
</span>
<div class="media-body flex-grow-1">
<p class="noti-details"><span class="noti-title">Misty Tison</span> added <span class="noti-title">Domenic Houston</span> and <span class="noti-title">Claire Mapes</span> to project <span class="noti-title">Doctor available module</span></p>
<p class="noti-time"><span class="notification-time">8 mins ago</span></p>
</div>
</div>
</a>
</li>
<li class="notification-message">
<a href="activities.html">
<div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-17.jpg">
</span>
<div class="media-body flex-grow-1">
<p class="noti-details"><span class="noti-title">Rolland Webber</span> completed task <span class="noti-title">Patient and Doctor video conferencing</span></p>
<p class="noti-time"><span class="notification-time">12 mins ago</span></p>
</div>
</div>
</a>
</li>
<li class="notification-message">
<a href="activities.html">
<div class="media d-flex">
<span class="avatar flex-shrink-0">
<img alt="" src="assets/img/profiles/avatar-13.jpg">
</span>
<div class="media-body flex-grow-1">
<p class="noti-details"><span class="noti-title">Bernardo Galaviz</span> added new task <span class="noti-title">Private chat module</span></p>
<p class="noti-time"><span class="notification-time">2 days ago</span></p>
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
<h6>John Doe</h6>
<h5>Salesman</h5>
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
<li><a href="addcategory.html">Add Category</a></li>
<li><a href="subcategorylist.html">Sub Category List</a></li>
<li><a href="subaddcategory.html">Add Sub Category</a></li>
<li><a href="brandlist.html">Brand List</a></li>
<li><a href="addbrand.html">Add Brand</a></li>
<li><a href="importproduct.html">Import Products</a></li>
<li><a href="barcode.html">Print Barcode</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/sales1.svg" alt="img"><span> Sales</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="saleslist.html">Sales List</a></li>
<li><a href="pos.html">POS</a></li>
<li><a href="pos.html">New Sales</a></li>
<li><a href="salesreturnlists.html">Sales Return List</a></li>
<li><a href="createsalesreturns.html">New Sales Return</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/purchase1.svg" alt="img"><span> Purchase</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="purchaselist.html">Purchase List</a></li>
<li><a href="addpurchase.html">Add Purchase</a></li>
<li><a href="importpurchase.html">Import Purchase</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/transfer1.svg" alt="img"><span> Purchase Request</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="purchase-request-list.html">Request List</a></li>
<li><a href="create-purchase-request.html">Create Request</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/expense1.svg" alt="img"><span> Expense</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="expenselist.html">Expense List</a></li>
<li><a href="createexpense.html">Add Expense</a></li>
<li><a href="expensecategory.html">Expense Category</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/quotation1.svg" alt="img"><span> Quotation</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="quotationList.html">Quotation List</a></li>
<li><a href="addquotation.html">Add Quotation</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/transfer1.svg" alt="img"><span> Transfer</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="transferlist.html">Transfer List</a></li>
<li><a href="addtransfer.html">Add Transfer </a></li>
<li><a href="importtransfer.html">Import Transfer </a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/return1.svg" alt="img"><span> Return</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="salesreturnlist.html">Sales Return List</a></li>
<li><a href="createsalesreturn.html">Add Sales Return </a></li>
<li><a href="purchasereturnlist.html">Purchase Return List</a></li>
<li><a href="createpurchasereturn.html">Add Purchase Return </a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/users1.svg" alt="img"><span> People</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="customerlist.html">Customer List</a></li>
<li><a href="addcustomer.html">Add Customer </a></li>
<li><a href="supplierlist.html">Supplier List</a></li>
<li><a href="addsupplier.html">Add Supplier </a></li>
<li><a href="userlist.html">User List</a></li>
<li><a href="adduser.html">Add User</a></li>
<li><a href="storelist.html">Store List</a></li>
<li><a href="addstore.html">Add Store</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/time.svg" alt="img"><span> Report</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="purchaseorderreport.html">Purchase order report</a></li>
<li><a href="inventoryreport.html">Inventory Report</a></li>
<li><a href="salesreport.html">Sales Report</a></li>
<li><a href="invoicereport.html">Invoice Report</a></li>
<li><a href="purchasereport.html">Purchase Report</a></li>
<li><a href="supplierreport.html">Supplier Report</a></li>
<li><a href="customerreport.html">Customer Report</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/users1.svg" alt="img"><span> Users</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="newuser.html">New User </a></li>
<li><a href="userlists.html">Users List</a></li>
</ul>
</li>
<li class="submenu">
<a href="javascript:void(0);"><img src="assets/img/icons/settings.svg" alt="img"><span> Settings</span> <span class="menu-arrow"></span></a>
<ul>
<li><a href="generalsettings.html">General Settings</a></li>
<li><a href="emailsettings.html">Email Settings</a></li>
<li><a href="paymentsettings.html">Payment Settings</a></li>
<li><a href="currencysettings.html">Currency Settings</a></li>
<li><a href="grouppermissions.html">Group Permissions</a></li>
<li><a href="taxrates.html">Tax Rates</a></li>
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
<h4>Update Purchase Request</h4>
<h6>Edit your purchase request (only available when status is "New")</h6>
</div>
</div>

<!-- Status Banner -->
<div class="alert alert-warning d-flex align-items-center mb-3" role="alert" id="status-banner" style="border-radius: 8px;">
<i class="fas fa-info-circle me-2" style="font-size: 18px;"></i>
<div>
<strong>Request Code: PR-001</strong> &nbsp;|&nbsp; Status: <span class="badges bg-lightyellow">New</span>
&nbsp;|&nbsp; You can edit this request because it has not been approved by Manager yet.
</div>
</div>

<div class="card">
<div class="card-body">
<!-- Hidden Salesman ID -->
<input type="hidden" id="salesman-id" name="salesmanId" value="SM-1001">
<input type="hidden" id="request-id" name="requestId" value="PR-001">

<div class="row">
<div class="col-lg-6">
<div class="form-group">
<label>Salesman</label>
<input type="text" value="John Doe (SM-1001)" disabled class="form-control" id="salesman-display">
</div>
</div>
<div class="col-lg-3">
<div class="form-group">
<label>Request Code</label>
<input type="text" value="PR-001" disabled class="form-control" id="request-code-display">
</div>
</div>
<div class="col-lg-3">
<div class="form-group">
<label>Created At</label>
<input type="text" value="06 Jun 2026" disabled class="form-control" id="created-at-display">
</div>
</div>
</div>

<!-- Product List Section -->
<div class="row">
<div class="col-lg-12">
<div class="form-group">
<h5 class="mb-3" style="font-weight: 600; color: #333;">Product List</h5>
</div>
</div>
</div>

<div id="product-list-container">
<!-- Product Row 1 (pre-filled data) -->
<div class="row product-row align-items-center" id="product-row-1">
<div class="col-lg-5 col-sm-5 col-12">
<div class="form-group">
<label>Product</label>
<select class="select product-select" id="product-select-1">
<option>Choose Product</option>
<option selected>Apple Earpods</option>
<option>Macbook Pro</option>
<option>iPhone 15 Pro</option>
<option>Samsung Galaxy S24</option>
<option>Dell Monitor 27"</option>
<option>HP Printer LaserJet</option>
<option>Logitech MX Master 3</option>
<option>USB-C Hub Adapter</option>
<option>Canon Ink Cartridge</option>
<option>Wireless Keyboard Combo</option>
</select>
</div>
</div>
<div class="col-lg-3 col-sm-3 col-12">
<div class="form-group">
<label>Quantity</label>
<input type="number" min="1" value="20" class="form-control product-qty" id="product-qty-1">
</div>
</div>
<div class="col-lg-2 col-sm-2 col-12">
<div class="form-group pt-4">
<a class="delete-set remove-product-btn" href="javascript:void(0);" title="Remove product" id="remove-product-1">
<img src="assets/img/icons/delete.svg" alt="Remove">
</a>
</div>
</div>
</div>

<!-- Product Row 2 (pre-filled data) -->
<div class="row product-row align-items-center" id="product-row-2">
<div class="col-lg-5 col-sm-5 col-12">
<div class="form-group">
<label>Product</label>
<select class="select product-select" id="product-select-2">
<option>Choose Product</option>
<option>Apple Earpods</option>
<option selected>iPhone 15 Pro</option>
<option>Macbook Pro</option>
<option>Samsung Galaxy S24</option>
<option>Dell Monitor 27"</option>
<option>HP Printer LaserJet</option>
<option>Logitech MX Master 3</option>
<option>USB-C Hub Adapter</option>
<option>Canon Ink Cartridge</option>
<option>Wireless Keyboard Combo</option>
</select>
</div>
</div>
<div class="col-lg-3 col-sm-3 col-12">
<div class="form-group">
<label>Quantity</label>
<input type="number" min="1" value="15" class="form-control product-qty" id="product-qty-2">
</div>
</div>
<div class="col-lg-2 col-sm-2 col-12">
<div class="form-group pt-4">
<a class="delete-set remove-product-btn" href="javascript:void(0);" title="Remove product" id="remove-product-2">
<img src="assets/img/icons/delete.svg" alt="Remove">
</a>
</div>
</div>
</div>

<!-- Product Row 3 (pre-filled data) -->
<div class="row product-row align-items-center" id="product-row-3">
<div class="col-lg-5 col-sm-5 col-12">
<div class="form-group">
<label>Product</label>
<select class="select product-select" id="product-select-3">
<option>Choose Product</option>
<option>Apple Earpods</option>
<option>iPhone 15 Pro</option>
<option selected>Macbook Pro</option>
<option>Samsung Galaxy S24</option>
<option>Dell Monitor 27"</option>
<option>HP Printer LaserJet</option>
<option>Logitech MX Master 3</option>
<option>USB-C Hub Adapter</option>
<option>Canon Ink Cartridge</option>
<option>Wireless Keyboard Combo</option>
</select>
</div>
</div>
<div class="col-lg-3 col-sm-3 col-12">
<div class="form-group">
<label>Quantity</label>
<input type="number" min="1" value="10" class="form-control product-qty" id="product-qty-3">
</div>
</div>
<div class="col-lg-2 col-sm-2 col-12">
<div class="form-group pt-4">
<a class="delete-set remove-product-btn" href="javascript:void(0);" title="Remove product" id="remove-product-3">
<img src="assets/img/icons/delete.svg" alt="Remove">
</a>
</div>
</div>
</div>
</div>

<!-- Add Product Button -->
<div class="row">
<div class="col-lg-12">
<a href="javascript:void(0);" class="btn btn-submit" id="btn-add-product" style="background: #28C76F; margin-bottom: 20px;">
<i class="fas fa-plus me-2"></i>Add Product
</a>
</div>
</div>

<!-- Note -->
<div class="row">
<div class="col-lg-12">
<div class="form-group">
<label>Note</label>
<textarea class="form-control" rows="4" placeholder="Enter note for this purchase request..." id="request-note">Need restock for Apple products - running low on inventory</textarea>
</div>
</div>
</div>

<!-- Action Buttons -->
<div class="row">
<div class="col-lg-12">
<a href="javascript:void(0);" class="btn btn-submit me-2" id="btn-update-request">Update Request</a>
<a href="purchase-request-list.html" class="btn btn-cancel" id="btn-cancel-update">Cancel</a>
</div>
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
$(document).ready(function() {
    var productCount = 3;

    // Add Product Row
    $('#btn-add-product').on('click', function() {
        productCount++;
        var newRow = `
        <div class="row product-row align-items-center" id="product-row-${productCount}">
            <div class="col-lg-5 col-sm-5 col-12">
                <div class="form-group">
                    <label>Product</label>
                    <select class="select product-select" id="product-select-${productCount}">
                        <option>Choose Product</option>
                        <option>Apple Earpods</option>
                        <option>Macbook Pro</option>
                        <option>iPhone 15 Pro</option>
                        <option>Samsung Galaxy S24</option>
                        <option>Dell Monitor 27"</option>
                        <option>HP Printer LaserJet</option>
                        <option>Logitech MX Master 3</option>
                        <option>USB-C Hub Adapter</option>
                        <option>Canon Ink Cartridge</option>
                        <option>Wireless Keyboard Combo</option>
                    </select>
                </div>
            </div>
            <div class="col-lg-3 col-sm-3 col-12">
                <div class="form-group">
                    <label>Quantity</label>
                    <input type="number" min="1" value="1" class="form-control product-qty" id="product-qty-${productCount}">
                </div>
            </div>
            <div class="col-lg-2 col-sm-2 col-12">
                <div class="form-group pt-4">
                    <a class="delete-set remove-product-btn" href="javascript:void(0);" title="Remove product" id="remove-product-${productCount}">
                        <img src="assets/img/icons/delete.svg" alt="Remove">
                    </a>
                </div>
            </div>
        </div>`;
        $('#product-list-container').append(newRow);
        // Re-initialize select2 for the new select
        $('#product-select-' + productCount).select2({
            minimumResultsForSearch: -1
        });
    });

    // Remove Product Row
    $(document).on('click', '.remove-product-btn', function() {
        if ($('.product-row').length > 1) {
            $(this).closest('.product-row').remove();
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Cannot Remove',
                text: 'At least one product is required!',
                confirmButtonColor: '#FF9F43'
            });
        }
    });

    // Update Request
    $('#btn-update-request').on('click', function() {
        var hasEmptyProduct = false;
        $('.product-select').each(function() {
            if ($(this).val() === 'Choose Product') {
                hasEmptyProduct = true;
            }
        });

        if (hasEmptyProduct) {
            Swal.fire({
                icon: 'error',
                title: 'Validation Error',
                text: 'Please select a product for all rows!',
                confirmButtonColor: '#FF9F43'
            });
            return;
        }

        Swal.fire({
            title: 'Update Purchase Request?',
            text: 'Are you sure you want to update this purchase request?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#FF9F43',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, Update it!'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    icon: 'success',
                    title: 'Request Updated!',
                    text: 'Your purchase request has been updated successfully.',
                    confirmButtonColor: '#FF9F43'
                }).then(() => {
                    window.location.href = 'purchase-request-list.html';
                });
            }
        });
    });
});
</script>
</body>
</html>
