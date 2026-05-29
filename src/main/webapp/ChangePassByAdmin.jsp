<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="users" scope="request"/>
<c:set var="pageTitle" value="Change Password" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="assests/css/wms-theme.css">
</head>
<body>
<div class="wrapper">
    <jsp:include page="common/adminSidebar.jsp"/>
    <main class="main-content">
        <jsp:include page="common/adminTopbar.jsp"/>
        <section class="content-box">
            <h2>Change Password</h2>

            <c:if test="${not empty message}">
                <div class="alert-success">${message}</div>
            </c:if>

            <form action="changepassbyadmin" method="POST" style="max-width:420px;">

                <input type="hidden" name="userId" value="${userId}">

                <div class="input-group">
                    <label>User ID: ${userId}</label>
                </div>

                <div class="input-group">
                    <label>New password</label>
                    <input type="password" name="newPass" required>
                </div>

                <div class="input-group">
                    <label>Confirm password</label>
                    <input type="password" name="cfNewPass" required>
                </div>

                <c:if test="${not empty error}">
                    <div style="color: red; font-weight: bold; margin-top: 10px;">
                            ${error}
                    </div>
                </c:if>

                <div class="btn-wrapper">
                    <button type="submit" class="btn-primary">Confirm</button>
                </div>
            </form>
        </section>
    </main>
</div>

</body>
</html>