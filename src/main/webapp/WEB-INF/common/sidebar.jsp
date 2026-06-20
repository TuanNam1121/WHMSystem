<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li class="active">
                    <a href="home"><img src="assets/img/icons/dashboard.svg" alt="img"><span> Dashboard</span>
                    </a>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/product.svg"
                                                       alt="img"><span> Product</span> <span
                            class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="productlist">Product List</a></li>
                        <li><a href="categoryList">Category List</a></li>
                        <li><a href="brandList">Brand List</a></li>
                        <li><a href="ChipList">Chip List</a></li>
                        <li><a href="RamList">Ram List</a></li>
                        <li><a href="StorageList">Storage List</a></li>
                        <li><a href="ModelList">Model List</a></li>
                        <li><a href="UnitList">Unit List</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/sales1.svg"
                                                       alt="img"><span> Sales</span> <span
                            class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="OrderList">Sales List</a></li>
                        <li><a href="pos.html">POS</a></li>
                        <li><a href="pos.html">New Sales</a></li>
                        <li><a href="salesreturnlists.html">Sales Return List</a></li>
                        <li><a href="createsalesreturns.html">New Sales Return</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/purchase1.svg"
                                                       alt="img"><span> Purchase</span>
                        <span class="menu-arrow"></span></a>
                    <ul>
                        <li>
                            <c:if test="${sessionScope.user.roleId == 2}">
                                <a href="managerPurchaseRequestList">Purchase List</a>
                            </c:if>
                            <c:if test="${sessionScope.user.roleId == 4}">
                                <a href="purchaseRequestList">Purchase List</a>
                            </c:if>
                            <c:if test="${sessionScope.user.roleId != 2 && sessionScope.user.roleId != 4}">
                                <a href="home">Purchase List</a>
                            </c:if>
                        </li>
                        <c:if test="${sessionScope.user.roleId == 4}">
                            <li>
                                <a href="createPurchaseRequest">Create Purchase Request</a>
                            </li>
                        </c:if>
                        <%--<li><a href="purchaseRequestList">Purchase List for salesman</a></li>--%>
                        <%--<li><a href="managerPurchaseRequestList">Purchase List for manager</a></li>--%>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/expense1.svg"
                                                       alt="img"><span> Import</span> <span
                            class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="importRequestList">Import Request List</a></li>
                        <li><a href="ImportHistory">Import History</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/quotation1.svg"
                                                       alt="img"><span> Quotation</span>
                        <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="quotationList.html">Quotation List</a></li>
                        <li><a href="addquotation.jsp">Add Quotation</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/transfer1.svg"
                                                       alt="img"><span> Transfer</span>
                        <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="toExportList">Export Product</a></li>
                        <li><a href="exportHistory">Export History</a></li>

                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/return1.svg"
                                                       alt="img"><span> Return</span> <span
                            class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="salesreturnlist.html">Sales Return List</a></li>
                        <li><a href="createsalesreturn.html">Add Sales Return </a></li>
                        <li><a href="purchasereturnlist.html">Purchase Return List</a></li>
                        <li><a href="createpurchasereturn.html">Add Purchase Return </a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/users1.svg"
                                                       alt="img"><span> People</span> <span
                            class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="CustomerList">Customer List</a></li>
                        <li><a href="listSupplier">Supplier List</a></li>
                    </ul>
                </li>

                <li>
                    <a href="InventoryAuditList"><i data-feather="layers"></i><span> Inventory Audit List</span> </a>
                </li>

                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/time.svg"
                                                       alt="img"><span> Report</span> <span
                            class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="purchaseorderreport.html">Purchase order report</a></li>
                        <li><a href="inventoryreport.html">Inventory Report</a></li>
                        <li><a href="salesreport.html">Sales Report</a></li>
                        <li><a href="invoicereport.html">Invoice Report</a></li>
                        <li><a href="purchasereport.html">Purchase Report</a></li>
                        <li><a href="supplierreport.html">Supplier Report</a></li>
                        <li><a href="customerreport.html">Customer Report</a></li>
                    </ul>
                </li>

                <li class="submenu">
                    <a href="javascript:void(0);"><img src="assets/img/icons/settings.svg"
                                                       alt="img"><span> Settings</span> <span
                            class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="generalsettings.html">General Settings</a></li>
                        <li><a href="emailsettings.html">Email Settings</a></li>
                        <li><a href="paymentsettings.html">Payment Settings</a></li>
                        <li><a href="currencysettings.html">Currency Settings</a></li>
                        <li><a href="grouppermissions.html">Group Permissions</a></li>
                        <li><a href="taxrates.html">Tax Rates</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
