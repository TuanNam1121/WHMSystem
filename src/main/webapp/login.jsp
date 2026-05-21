<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign in - WMS</title>
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
                <h1 class="signin-title">Sign in</h1>
                <p class="register-text">Do not have account yet? <a href="register">Register now</a></p>
                <c:if test="${not empty error}">
                    <div class="alert-error">${error}</div>
                </c:if>
                <form action="login" method="post">
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" name="username" class="input-field" value="${param.username}" placeholder="username" required/>
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" class="input-field" placeholder="••••••••" required/>
                    </div>
                    <div class="options-row">
                        <input type="checkbox" id="remember" name="remember">
                        <label for="remember">Remember me</label>
                    </div>
                    <button type="submit" class="login-btn">Login</button>
                    <a href="forgetpassword" class="forgot-pass">Forgot password</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
