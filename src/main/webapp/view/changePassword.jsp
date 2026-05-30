<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="../assests/css/wms-theme.css">
</head>
<body class="profile-page">
    <header class="topbar" style="max-width:480px;margin:0 auto 16px;">
        <a href="home" style="color:var(--text);font-weight:600;text-decoration:none;">← Change password</a>
    </header>
    <article class="profile-card" style="max-width:480px;">
        <section class="profile-section">
            <h2 class="profile-section-title">Change password</h2>
            <c:if test="${not empty error}">
                <div class="alert-error">${error}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert-success">${message}</div>
            </c:if>
            <form action="changePassword" method="post">
                <div class="input-group">
                    <label>Current password</label>
                    <input type="password" name="currentPass" required />
                </div>
                <div class="input-group">
                    <label>New password</label>
                    <input type="password" name="newPass" required />
                </div>
                <div class="input-group">
                    <label>Confirm new password</label>
                    <input type="password" name="cfNewPass" required />
                </div>
                <div class="btn-wrapper">
                    <a href="home" class="btn-secondary" style="display:inline-flex;align-items:center;padding:8px 20px;text-decoration:none;">Cancel</a>
                    <button type="submit" class="btn-primary">Save</button>
                </div>
            </form>
        </section>
    </article>
    <script>
        window.onload = function () {
            const message = document.querySelector('.alert-success');
            if (message && message.innerText.trim() !== '') {
                setTimeout(function () { window.location.href = 'home'; }, 1000);
            }
        };
    </script>
</body>
</html>
