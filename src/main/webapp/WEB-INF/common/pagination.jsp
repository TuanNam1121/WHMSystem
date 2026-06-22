<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    Required request attributes: page, pageSize, totalPages.
    Include this file inside the table's GET form.
--%>
<style>
    .table-footer {
        display: flex !important;
        align-items: center !important;
        justify-content: space-between !important;
        width: 100% !important;
        margin-top: 15px !important;
    }

    .table-footer .page-size {
        display: flex !important;
        align-items: center !important;
        flex: 0 0 auto !important;
        gap: 6px !important;
    }

    .table-footer .page-size label {
        margin: 0 !important;
        white-space: nowrap !important;
        font-size: 12px !important;
    }

    .table-footer .page-size-select {
        width: 70px !important;
        min-width: 70px !important;
        height: 38px !important;
        font-size: 12px !important;
    }

    .table-footer .table-pagination {
        display: flex !important;
        flex: 1 1 auto !important;
        justify-content: flex-end !important;
        margin-left: auto !important;
    }

    .table-footer .pagination {
        display: flex !important;
        justify-content: flex-end !important;
        margin: 0 !important;
        padding-left: 0 !important;
        list-style: none !important;
    }

    .table-footer .pagination .page-item {
        margin: 0 5px !important;
    }

    .table-footer .pagination .page-link {
        display: flex !important;
        align-items: center !important;
        justify-content: center !important;
        min-width: 38px !important;
        height: 38px !important;
        padding: 6px 12px !important;
        border: 1px solid #e8ebed !important;
        border-radius: 5px !important;
        background: #fff !important;
        color: #637381 !important;
    }

    .table-footer .pagination button.page-link:hover,
    .table-footer .pagination .active .page-link {
        border-color: #ff9f43 !important;
        background: #ff9f43 !important;
        color: #fff !important;
    }

    .table-footer .pagination .disabled .page-link {
        border-color: #e8ebed !important;
        background: #fff !important;
        color: #637381 !important;
        opacity: 0.65 !important;
    }

    @media (max-width: 575px) {
        .table-footer {
            align-items: flex-start !important;
            flex-direction: column !important;
            gap: 15px !important;
        }

        .table-footer .table-pagination {
            width: 100% !important;
            overflow-x: auto !important;
        }
    }
</style>

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
