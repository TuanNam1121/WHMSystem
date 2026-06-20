<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    Required request attributes: page, pageSize, totalPages.
    Include this file inside the table's GET form.
--%>
<div class="table-footer">
    <div class="page-size">
        <label for="pageSize">Show per page:</label>
        <select id="pageSize" name="pageSize"
                class="form-select form-select-sm page-size-select"
                onchange="this.form.submit()">
            <option value="10" ${pageSize == 10 ? "selected" : ""}>10</option>
            <option value="25" ${pageSize == 25 ? "selected" : ""}>25</option>
            <option value="50" ${pageSize == 50 ? "selected" : ""}>50</option>
            <option value="100" ${pageSize == 100 ? "selected" : ""}>100</option>
        </select>
    </div>

    <nav class="table-pagination" aria-label="Table pages">
        <ul class="pagination">
            <li class="page-item ${page == 1 ? 'disabled' : ''}">
                <button class="page-link" type="submit" name="page" value="${page - 1}"
                        ${page == 1 ? 'disabled' : ''} aria-label="Previous page">«</button>
            </li>

            <c:choose>
                <c:when test="${totalPages <= 5}">
                    <c:forEach begin="1" end="${totalPages}" var="pageNumber">
                        <li class="page-item ${page == pageNumber ? 'active' : ''}">
                            <button class="page-link" type="submit" name="page" value="${pageNumber}">
                                    ${pageNumber}
                            </button>
                        </li>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <c:forEach begin="1" end="3" var="pageNumber">
                        <li class="page-item ${page == pageNumber ? 'active' : ''}">
                            <button class="page-link" type="submit" name="page" value="${pageNumber}">
                                    ${pageNumber}
                            </button>
                        </li>
                    </c:forEach>

                    <li class="page-item disabled">
                        <span class="page-link">...</span>
                    </li>

                    <c:forEach begin="${totalPages - 1}" end="${totalPages}" var="pageNumber">
                        <li class="page-item ${page == pageNumber ? 'active' : ''}">
                            <button class="page-link" type="submit" name="page" value="${pageNumber}">
                                    ${pageNumber}
                            </button>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            <li class="page-item ${page == totalPages ? 'disabled' : ''}">
                <button class="page-link" type="submit" name="page" value="${page + 1}"
                        ${page == totalPages ? 'disabled' : ''} aria-label="Next page">»</button>
            </li>
        </ul>
    </nav>
</div>
