<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset password - WMS</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assests/css/wms-theme.css">
    <style>body, html { font-family: 'Inter', sans-serif; }</style>
</head>
<body class="auth-page">
    <div class="page-wrapper">
        <div class="left-section">
            <div class="logo-box">WMS</div>
            <div class="photo-placeholder">Warehouse Management System</div>
        </div>
        <div class="right-section">
            <div class="login-card">
                <h1 class="signin-title">Reset your password</h1>
                <p class="register-text">If the account exists, we will email you a code to reset the password.</p>
                <form action="forgetpassword" method="POST">
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" name="username" class="input-field" required/>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="text" name="email" class="input-field" required/>
                    </div>
                    <c:if test="${not empty error}">
                        <div class="alert-error">${error}</div>
                    </c:if>
                    <button type="submit" class="login-btn">Send request</button>
                    <a href="login" class="forgot-pass">Return to Sign in</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
