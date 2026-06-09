<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            <form action="changePassword" method="post">
                <div class="card">
                    <div class="card-body">

                        <c:if test="${not empty requestScope.error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong>${requestScope.error}</strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"
                                        aria-label="Close"></button>
                            </div>
                            <% request.removeAttribute("error"); %>
                        </c:if>
                        <c:if test="${not empty requestScope.message}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong>${requestScope.message}</strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert"
                                        aria-label="Close"></button>
                            </div>
                            <% request.removeAttribute("message"); %>
                        </c:if>
                        <div class="profile-set">
                            <div class="profile-head">
                            </div>
                            <div class="profile-top">
                                <div class="profile-content">
                                    <div class="profile-contentimg">
                                        <img src="assets/img/customer/customer5.jpg" alt="img" id="blah">
                                        <div class="profileupload">
                                            <input type="file" id="imgInp">
                                            <a href="javascript:void(0);"><img src="assets/img/icons/edit-set.svg"
                                                                               alt="img"></a>
                                        </div>
                                    </div>
                                    <div class="profile-contentname">
                                        <h2>${sessionScope.user.firstname} ${sessionScope.user.lastname}</h2>
                                        <h4>${sessionScope.roleName}</h4>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Current Password</label>
                                    <input type="password" name="currentPass" required>
                                </div>
                            </div>

                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>New Password</label>
                                    <input type="password" name="newPass" required>
                                </div>
                            </div>

                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Confirm New Password</label>
                                    <input type="password" name="cfNewPass" required>
                                </div>
                            </div>

                            <div class="col-12">
                                <button type="submit" class="btn btn-submit d-inline-block me-2">Submit</button>
                                <a href="viewprofile" class="btn btn-cancel">Cancel</a>
                            </div>


                            <div style="padding-bottom: 20px">
                                <form action="changePassword" method="post" id="changePasswordForm">

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

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

<script src="assets/js/script.js">
    window.addEventListener('load', function () {
        setTimeout(finalClearAutofill, 50);
    });

    function finalClearAutofill() {
        var usernameInput = document.querySelector('input[name="username"]');
        var passwordInput = document.querySelector('input[name="password"]');

        if (usernameInput) usernameInput.value = '';
        if (passwordInput) passwordInput.value = '';
    }
</script>
</body>
</html>
