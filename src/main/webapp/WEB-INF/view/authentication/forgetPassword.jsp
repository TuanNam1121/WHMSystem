<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
    <title>Reset password - WHM System</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">

    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body class="account-page">

<div class="main-wrapper">
    <div class="account-content">
        <div class="login-wrapper">
            <div class="login-content">
                <div class="login-userset">
                    <div class="login-logo">
                        <img src="assets/img/logo.png" alt="img">
                    </div>
                    <div class="login-userheading">
                        <h3>Reset Password</h3>
                        <h4>If the account exists, we will email you a code to reset the password.</h4>
                    </div>
                    <form action="forgetPassword" method="POST">

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                    ${error}
                            </div>
                        </c:if>

                        <div class="form-login">
                            <label>Username</label>
                            <div class="form-addons">
                                <input type="text" name="username" placeholder="Enter your username">
                                <img src="assets/img/icons/mail.svg" alt="img">
                            </div>
                        </div>
                        <div class="form-login">
                            <label>Email</label>
                            <div class="form-addons">
                                <input type="text" name="email" placeholder="Enter your email">
                                <img src="assets/img/icons/mail.svg" alt="img">
                            </div>
                        </div>

                        <div class="form-login">
                            <button type="submit" class="btn btn-login">Send request</button>
                            <a href="login" class="btn btn-login" style="background-color: #898f9e">Return to Sign in</a>
                        </div>
                    </form>

                </div>
            </div>
            <div class="login-img">
                <img src="assets/img/login.jpg" alt="img">
            </div>
        </div>
    </div>
</div>


<script src="assets/js/jquery-3.6.0.min.js"></script>

<script src="assets/js/feather.min.js"></script>

<script src="assets/js/bootstrap.bundle.min.js"></script>

<script src="assets/js/script.js"></script>
</body>
</html>
