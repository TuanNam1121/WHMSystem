<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="activeMenu" value="categories" scope="request" />
<c:set var="pageTitle" value="${act == 'new' ? 'Add Category' : 'Update Category'}" scope="request" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${act.equals("new") ? "Add New Category" : "Update Category Information"}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assests/css/wms-theme.css" rel="stylesheet">
</head>
<body>
<div class="wrapper">
    <jsp:include page="common/adminSidebar.jsp" />
    <main class="main-content">
        <jsp:include page="common/userTopbar.jsp" />
        <c:if test="${not empty message}">
            <div class="alert-success">${message}</div>
        </c:if>
        <section class="form-container">
            <h2>${act == 'new' ? 'Add New Category' : 'Update Category Information'}</h2>
            <c:if test="${error != null}">
                <div class="alert-error">${error}</div>
            </c:if>
            <form action="${act == 'new' ? 'AddNewCategory' : 'UpdateCategoryInformation'}" method="post" novalidate>
                <input type="hidden" name="id" value="${u != null ? u.id : ''}">
                <div class="form-grid">
                    <div class="form-left">
                        <label>CategoryName</label>
                        <input type="text" name="categoryname" value="${u.categoryName != null ? u.categoryName : ''}">
                        <label>Description</label>
                        <textarea id="description" name="description" rows="6" cols="50" placeholder="Input description here..."></textarea>
                    </div>
                </div>
                <div class="button-area">
                    <button type="submit">${act == 'new' ? 'CREATE' : 'UPDATE'}</button>
                    <a href="ViewCategoryList" class="btn-secondary" style="display:inline-flex;align-items:center;padding:8px 20px;">Cancel</a>
                </div>
            </form>
        </section>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</div>
</body>
</html>
