<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WMS - Welcome</title>
    <link rel="stylesheet" href="assests/css/wms-theme.css">
</head>
<body class="profile-page">
    <article class="profile-card standalone-card">
        <section class="profile-section">
            <h2 style="margin:0 0 8px;color:var(--primary);font-size:28px;">WMS</h2>
            <p style="color:var(--text-muted);margin-bottom:0;">Warehouse Management System</p>
            <div class="standalone-links">
                <a href="${pageContext.request.contextPath}/login" class="btn-primary">Login</a>
                <a href="${pageContext.request.contextPath}/home" class="btn-secondary">Home</a>
                <a href="${pageContext.request.contextPath}/AdminDashBoard">Admin Dashboard</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </section>
    </article>
</body>
</html>
