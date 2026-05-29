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
                <td>description</td>
                <td>created at</td>
                <td>updated at</td>
            </tr>
            <c:forEach items="${brands}" var="b">
                <tr>
                <td>${b.id}</td>
                <td>${b.name}</td>
                <td>${b.description}</td>
                <td>${b.createdAt}</td>
                <td>${b.updatedAt}</td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
