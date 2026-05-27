<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chip List</title>
</head>
<body>

<h2>Chip List</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Is Active</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="chip" items="${chips}">
        <tr>
            <td>${chip.id}</td>
            <td>${chip.name}</td>
            <td>
                <c:choose>
                    <c:when test="${chip.active}">
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