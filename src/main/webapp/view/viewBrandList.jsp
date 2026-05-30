<%-- 
    Document   : ViewBrandList
    Created on : May 29, 2026, 11:41:42 AM
    Author     : LENOVO
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <table>
            <tr>
                <td>id</td>
                <td>name</td>
                <td>img</td>
                <td>description</td>
                <td>created at</td>
                <td>updated at</td>
                <td>edit</td>
            </tr>
            <c:forEach items="${brands}" var="b">
                <tr>
                <td>${b.id}</td>
                <td>${b.name}</td>
                <td><img style="height: 50px; width: 50px" src="${pageContext.request.contextPath}${b.img}" /></td>
                <td>${b.description}</td>
                <td>${b.createdAt}</td>
                <td><a href="${pageContext.request.contextPath}/BrandDetail?id=${b.id}">edit</a></td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
