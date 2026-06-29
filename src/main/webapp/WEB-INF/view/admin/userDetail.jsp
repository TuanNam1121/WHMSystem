<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="users" scope="request" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>${act.equals("new") ? "Add New User" : "Update User Information"}</title>
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
    <jsp:include page="/WEB-INF/common/adminTopbar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/common/adminSidebar.jsp"></jsp:include>
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>${act == 'new' ? 'Add New User' : 'Update User Information'}</h4>
                    <h6>${act == 'new' ? 'Create new user profile' : 'Edit user profile'}</h6>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <strong>${message}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <c:if test="${error != null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>${error}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${act == 'new' ? 'AddNewUser' : 'UpdateUserInformation'}" method="post" novalidate>
                        <input type="hidden" name="id" value="${u != null ? u.id : ''}">
                        
                        <div class="row">
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>UserName</label>
                                    <input type="text" name="username" value="${u.userName != null ? u.userName : ''}" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>FullName</label>
                                    <input type="text" name="fullname" value="${u.fullName != null ? u.fullName : ''}" class="form-control" required>
                                </div>
                                <c:if test="${act == 'new'}">
                                    <div class="form-group">
                                        <label>Password</label>
                                        <input type="password" name="password" class="form-control" required>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label>Role</label>
                                    <select name="role" class="select">
                                        <c:forEach items="${rolelist}" var="i">
                                            <option value="${i.roleId}" ${u.roleId == i.roleId ? 'selected' : ''}>
                                                ${roleDao.getRoleNamFromRoleID(i.roleId)}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="col-lg-6 col-sm-12">
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" name="phone" value="${u.phone != null ? u.phone : ''}" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" name="email" value="${u.email != null ? u.email : ''}" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>Gender</label>
                                    <div class="d-flex gap-4 align-items-center border rounded p-3 bg-white">
                                        <div class="form-check">
                                            <input id="gender-male" class="form-check-input" type="radio" name="gender" value="MALE" ${u.gender == 'MALE' ? 'checked' : ''}>
                                            <label for="gender-male" class="form-check-label">Male</label>
                                        </div>
                                        <div class="form-check">
                                            <input id="gender-female" class="form-check-input" type="radio" name="gender" value="FEMALE" ${u.gender == 'FEMALE' ? 'checked' : ''}>
                                            <label for="gender-female" class="form-check-label">Female</label>
                                        </div>
                                        <div class="form-check">
                                            <input id="gender-other" class="form-check-input" type="radio" name="gender" value="OTHER" ${u.gender == 'OTHER' ? 'checked' : ''}>
                                            <label for="gender-other" class="form-check-label">Other</label>
                                        </div>
                                    </div>
                                </div>
                                
                                <c:if test="${act == 'update'}">
                                    <div class="form-group">
                                        <label>Account Status</label>
                                        <div class="status-toggle d-flex justify-content-between align-items-center">
                                            <input type="checkbox" id="user_active" class="check" name="active" value="true" ${u.isActive ? 'checked' : ''}>
                                            <label for="user_active" class="checktoggle">checkbox</label>
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                            <div class="col-lg-12 mt-3">
                                <button type="submit" class="btn btn-submit me-2">${act == 'new' ? 'Create' : 'Update'}</button>
                                <a href="ViewUserList" class="btn btn-cancel">Cancel</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
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
<script src="assets/js/script.js"></script>
</body>
</html>

