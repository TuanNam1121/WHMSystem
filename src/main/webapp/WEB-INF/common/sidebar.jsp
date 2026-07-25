<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li>
                    <a href="home"><img src="assets/img/icons/dashboard.svg" alt="img"><span> Dashboard</span>
                    </a>
                </li>
                <c:if test="${sessionScope.userPermissions.contains('VIEW_PRODUCT') ||
                              sessionScope.userPermissions.contains('VIEW_CATEGORY') ||
                              sessionScope.userPermissions.contains('VIEW_BRAND') ||
                              sessionScope.userPermissions.contains('VIEW_SPECIFICATION')}">
                      <li class="submenu">
                          <a href="javascript:void(0);"><img src="assets/img/icons/product.svg"
                                                             alt="img"><span> Product</span> <span
                                                             class="menu-arrow"></span></a>
                          <ul>
                              <c:if test="${sessionScope.userPermissions.contains('VIEW_PRODUCT')}">
                                    <li><a href="productlist">Product List</a></li></c:if>
                                  <c:if test="${sessionScope.userPermissions.contains('VIEW_CATEGORY')}">
                                    <li><a href="categoryList">Category List</a></li></c:if>
                                  <c:if test="${sessionScope.userPermissions.contains('VIEW_BRAND')}">
                                    <li><a href="brandList">Brand List</a></li></c:if>
                                  <c:if test="${sessionScope.userPermissions.contains('VIEW_SPECIFICATION')}">
                                    <li><a href="ChipList">Chip List</a></li>
                                    <li><a href="RamList">Ram List</a></li>
                                    <li><a href="StorageList">Storage List</a></li>
                                    <li><a href="ModelList">Model List</a></li>
                                    <li><a href="UnitList">Unit List</a></li></c:if>
                              </ul>
                          </li></c:if>
                <c:if test="${sessionScope.userPermissions.contains('VIEW_SALE_ORDER')}">
                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/sales1.svg"
                                                           alt="img"><span> Sales</span> <span
                                                           class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="OrderList">Sales List</a></li>
                        </ul>
                    </li></c:if>
                    <c:if test="${sessionScope.userPermissions.contains('APPROVE_REJECT_PURCHASE_REQUEST') ||
                                  sessionScope.userPermissions.contains('VIEW_PURCHASE_ORDER')}">
                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/purchase1.svg"
                                                           alt="img"><span> Purchase</span>
                            <span class="menu-arrow"></span></a>
                        <ul>
                            <li>
                            <c:choose>
                                <c:when test="${sessionScope.userPermissions.contains('APPROVE_REJECT_PURCHASE_REQUEST')}">
                                    <a href="managerPurchaseRequestList">Purchase List</a>
                                </c:when>
                                <c:when test="${sessionScope.userPermissions.contains('VIEW_PURCHASE_ORDER')}">
                                    <a href="purchaseRequestList">Purchase List</a>
                                </c:when>
                            </c:choose>
                        </li>
                        <c:if test="${sessionScope.userPermissions.contains('CREATE_PURCHASE_ORDER')}">
                            <li>
                                <a href="createPurchaseRequest">Create Purchase Request</a>
                            </li>
                        </c:if>
                    </ul>
                </li></c:if>
                <c:if test="${sessionScope.userPermissions.contains('VIEW_IMPORT_REQUEST') ||
                              sessionScope.userPermissions.contains('VIEW_IMPORT_HISTORY')}">
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/expense1.svg"
                                                       alt="img"><span> Import</span>
                        <c:if test="${requestScope.pendingImportRequestCount > 0}">
                            <span class="sidebar-notification-badge">
                                ${requestScope.pendingImportRequestCount}
                            </span>
                        </c:if>
                        <span class="menu-arrow"></span>
                    </a>
                    <ul>
                    <c:if test="${sessionScope.userPermissions.contains('VIEW_IMPORT_REQUEST')}">
                        <li><a href="importRequestList">Import Request List</a></li></c:if>
                    <c:if test="${sessionScope.userPermissions.contains('VIEW_IMPORT_HISTORY')}">
                        <li><a href="ImportHistory">Import History</a></li></c:if>
                    </ul>
                </li></c:if>
                <c:if test="${sessionScope.userPermissions.contains('VIEW_EXPORT_PRODUCT') || sessionScope.userPermissions.contains('VIEW_EXPORT_HISTORY')}">
                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/transfer1.svg"
                                                           alt="img"><span> Export</span>
                            <c:if test="${requestScope.newSaleOrderCount > 0}">
                                <span class="sidebar-notification-badge">
                                    ${requestScope.newSaleOrderCount}
                                </span>
                            </c:if>
                            <span class="menu-arrow"></span>
                        </a>
                        <ul>
                            <c:if test="${sessionScope.userPermissions.contains('VIEW_EXPORT_PRODUCT')}">
                                <li><a href="toExportList">Export Request List</a></li>
                                </c:if>
                                <c:if test="${sessionScope.userPermissions.contains('VIEW_EXPORT_HISTORY')}">
                                <li><a href="exportHistory">Export History</a></li>
                                </c:if>

                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userPermissions.contains('VIEW_SUPPLIER') || sessionScope.userPermissions.contains('VIEW_CUSTOMER')}">
                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/users1.svg"
                                                           alt="img"><span> People</span> <span
                                                           class="menu-arrow"></span></a>
                        <ul>
                            <c:if test="${sessionScope.userPermissions.contains('VIEW_CUSTOMER')}">
                                <li><a href="CustomerList">Customer List</a></li></c:if>
                                <c:if test="${sessionScope.userPermissions.contains('VIEW_SUPPLIER')}">
                                <li><a href="listSupplier">Supplier List</a></li></c:if>
                            </ul>
                        </li></c:if>

                    <li>
                        <a href="inventory"><img src="assets/img/icons/product.svg"><span> Inventory</span></a>
                    </li>


                <c:if test="${sessionScope.userPermissions.contains('VIEW_INVENTORY_AUDIT')}">
                    <li>
                        <a href="InventoryAuditList"><i data-feather="layers"></i><span> Inventory Audit List</span> </a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.userPermissions.contains('VIEW_INVENTORY_TRANSACTION')}">
                    <li>
                        <a href="InventoryTransaction"><i data-feather="repeat"></i><span> Inventory Transaction List</span> </a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.userPermissions.contains('VIEW_REPORT')}">
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/time.svg"
                                                       alt="img"><span> Report</span> <span
                                                       class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/inventorySummaryReport">Inventory Report</a></li>
                        <li><a href="${pageContext.request.contextPath}/importReport">Import Report</a></li>
                        <li><a href="${pageContext.request.contextPath}/exportReport">Export Report</a></li>
                        <li><a href="${pageContext.request.contextPath}/stockReport">Stock Report</a></li>
                        <li><a href="${pageContext.request.contextPath}/ImportExportByDayReport">Daily Transaction Report</a></li>
                    </ul>
                </li></c:if>

                <c:if test="${sessionScope.user.roleId == 1}">
                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/users1.svg"
                                                           alt="img"><span> Admin</span> <span
                                                           class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/AdminDashBoard">Dashboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/ViewUserList">User Management</a></li>
                            <li><a href="${pageContext.request.contextPath}/ViewRoleList">Role Management</a></li>
                            <li><a href="${pageContext.request.contextPath}/ViewPermissionList">Permission
                                    Management</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
