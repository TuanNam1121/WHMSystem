<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="users" scope="request" />
<c:set var="pageTitle" value="${act == 'new' ? 'Add User' : 'Update User'}" scope="request" />
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${act.equals("new") ? "Add New User" : "Update User Information"}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assests/css/wms-theme.css" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/common/adminSidebar.jsp" />
            <main class="main-content">
                <jsp:include page="/common/userTopbar.jsp" />
                <c:if test="${not empty message}">
                    <div class="alert-success">${message}</div>
                </c:if>
                <section class="form-container">
                    <h2>${act == 'new' ? 'Add New User' : 'Update User Information'}</h2>
                    <c:if test="${error != null}">
                        <div class="alert-error">${error}</div>
                    </c:if>
                    <form action="${act == 'new' ? 'AddNewUser' : 'UpdateUserInformation'}" method="post" novalidate>
                        <input type="hidden" name="id" value="${u != null ? u.id : ''}">
                        <div class="form-grid">
                            <div class="form-left">
                                <label>UserName</label>
                                <input type="text" name="username" value="${u.userName != null ? u.userName : ''}">
                                <label>FullName</label>
                                <input type="text" name="fullname" value="${u.fullName != null ? u.fullName : ''}">
                                <c:if test="${act == 'new'}">
                                    <label>Password</label>
                                    <input type="password" name="password">
                                </c:if>
                                <label>Role</label>
                                <c:forEach items="${rolelist}" var="i">
                                    <label style="display:inline-flex;margin-right:16px;margin-top:8px;">
                                        <input type="radio" name="role" value="${i.roleId}" ${u.roleId == i.roleId ? 'checked' : ''}>
                                        ${roleDao.getRoleNamFromRoleID(i.roleId)}
                                    </label>
                                </c:forEach>
                            </div>
                            <div class="form-right">
                                <label>Phone</label>
                                <input type="text" name="phone" value="${u.phone != null ? u.phone : ''}">
                                <label>Email</label>
                                <input type="email" name="email" value="${u.email != null ? u.email : ''}">
                                <div class="gender-group">
                                    <span style="display:block;margin-bottom:8px;font-weight:500;">Gender</span>
                                    <label><input type="radio" name="gender" value="MALE" ${u.gender == 'MALE' ? 'checked' : ''}> Male</label>
                                    <label><input type="radio" name="gender" value="FEMALE" ${u.gender == 'FEMALE' ? 'checked' : ''}> Female</label>
                                    <label><input type="radio" name="gender" value="OTHER" ${u.gender == 'OTHER' ? 'checked' : ''}> Other</label>
                                </div>
                                        
                                <c:if test="${act == 'update'}">
                                    <label>Account Status</label>
                                    <div class="status-group">
                                        <label style="display:inline-flex;gap:8px;">
                                            <input type="checkbox" name="active" value="true" ${u.isActive ? 'checked' : ''}>
                                            Active User
                                        </label>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="button-area">
                            <button type="submit">${act == 'new' ? 'CREATE' : 'UPDATE'}</button>
                            <a href="ViewUserList" class="btn-secondary" style="display:inline-flex;align-items:center;padding:8px 20px;">Cancel</a>
                        </div>
                    </form>
                </section>
            </main>
           <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </div>
    </body>
</html>

