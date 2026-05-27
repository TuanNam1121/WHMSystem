<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ram List</title>
</head>
<body>

<h2>Ram List</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Size</th>
        <th>Is Active</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="ram" items="${rams}">
        <tr>
            <td>${ram.id}</td>
            <td>${ram.size}</td>
            <td>
                <c:choose>
                    <c:when test="${ram.active}">
                        Active
                    </c:when>
                    <c:otherwise>
                        In active
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>