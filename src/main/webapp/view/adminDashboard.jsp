<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="activeMenu" value="dashboard" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="assests/css/wms-theme.css">
</head>
<body>
<div class="wrapper">
    <jsp:include page="../common/adminSidebar.jsp"/>
    <main class="main-content">
        <jsp:include page="../common/userTopbar.jsp"/>
        <c:if test="${not empty message}">
            <div class="alert-success">${message}</div>
        </c:if>
        <section class="content-box">
            <h2>Request From Staff</h2>
            <table>
                <thead>
                <tr>
                    <th>User Id</th>
                    <th>Request Type</th>
                    <th>Created At</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestlist}" var="r">
                    <tr>
                        <td>${r.userId}</td>
                        <td>${r.message}</td>
                        <td>${r.createdAt}</td>
                        <td>${r.status}</td>
                        <td>
                            <c:if test="${r.status == 'NEW'}">
                                <a href="solverequest?type=${r.message}&userid=${r.userId}">Solve</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </main>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
