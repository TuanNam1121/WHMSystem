package com.swp.whmsystem.controller.audit;

import com.swp.whmsystem.dal.InventoryAuditDAO;
import com.swp.whmsystem.dal.InventoryAuditItemSerialDAO;
import com.swp.whmsystem.dal.ProductItemDAO;
import com.swp.whmsystem.enums.InventoryAuditStatus;
import com.swp.whmsystem.model.InventoryAudit;
import com.swp.whmsystem.model.InventoryAuditItem;
import com.swp.whmsystem.model.InventoryAuditItemSerial;
import com.swp.whmsystem.model.ProductItem;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdjustInventoryAuditStock", urlPatterns = { "/AdjustInventoryAuditStock" })
public class AdjustInventoryAuditStock extends HttpServlet {
    private InventoryAuditDAO inventoryAuditDAO;
    private InventoryAuditItemSerialDAO serialDAO;
    private ProductItemDAO productItemDAO;

    @Override
    public void init() {
        inventoryAuditDAO = new InventoryAuditDAO();
        serialDAO = new InventoryAuditItemSerialDAO();
        productItemDAO = new ProductItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PERFORM_INVENTORY_AUDIT,
                "Only staff with perform audit permission are authorized to perform inventory audits.")) {
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
            return;
        }

        try {
            int auditId = Integer.parseInt(idParam);
            InventoryAudit audit = inventoryAuditDAO.getInventoryAuditById(auditId);
            if (audit == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory audit not found.");
                return;
            }

            if (audit.getStatus() != InventoryAuditStatus.SUBMITTED) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only submitted audits can have their stock adjusted.");
                return;
            }

            List<InventoryAuditItem> discrepancyItems = new ArrayList<>();
            for (InventoryAuditItem item : audit.getInventoryAuditItems()) {
                if (item.getPhysicalQuantity() != item.getSystemQuantity()) {
                    discrepancyItems.add(item);
                }
            }

            if (discrepancyItems.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No discrepancies to adjust.");
                return;
            }

            request.setAttribute("audit", audit);
            request.setAttribute("discrepancyItems", discrepancyItems);
            request.getRequestDispatcher("/WEB-INF/view/audit/adjustInventoryAuditStock.jsp").forward(request,
                    response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.PERFORM_INVENTORY_AUDIT,
                "Only staff with perform audit permission are authorized to perform inventory audits.")) {
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
            return;
        }

        try {
            int auditId = Integer.parseInt(idParam);
            InventoryAudit audit = inventoryAuditDAO.getInventoryAuditById(auditId);
            if (audit == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory audit not found.");
                return;
            }

            if (audit.getStatus() != InventoryAuditStatus.SUBMITTED) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only submitted audits can have their stock adjusted.");
                return;
            }

            List<InventoryAuditItem> discrepancyItems = new ArrayList<>();
            for (InventoryAuditItem item : audit.getInventoryAuditItems()) {
                if (item.getPhysicalQuantity() != item.getSystemQuantity()) {
                    discrepancyItems.add(item);
                }
            }

            if (discrepancyItems.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No discrepancies to adjust.");
                return;
            }

            boolean hasError = false;
            String errorMessage = "";
            List<InventoryAuditItemSerial> serialsToInsert = new ArrayList<>();

            for (InventoryAuditItem item : discrepancyItems) {
                int difference = item.getPhysicalQuantity() - item.getSystemQuantity();
                String type = difference > 0 ? "ADD" : "DELETE";
                int requiredCount = Math.abs(difference);

                String serialsParam = request.getParameter("serials_" + item.getId());
                if (serialsParam == null || serialsParam.trim().isEmpty()) {
                    hasError = true;
                    errorMessage = "Please provide serials for " + item.getProductName() + ".";
                    break;
                }

                String[] serialsArray = serialsParam.split("[\\r\\n]+");
                List<String> validSerials = new ArrayList<>();
                for (String s : serialsArray) {
                    String trimmed = s.trim();
                    if (!trimmed.isEmpty()) {
                        validSerials.add(trimmed);
                    }
                }

                if (validSerials.size() != requiredCount) {
                    hasError = true;
                    errorMessage = "For " + item.getProductName() + ", you must provide exactly " + requiredCount
                            + " serials.";
                    break;
                }

                for (String serial : validSerials) {
                    if ("DELETE".equals(type)) {
                        ProductItem pi = productItemDAO.existedSerial(item.getProductId(), serial);
                        if (pi == null || !"AVAILABLE".equals(pi.getStatus())) {
                            hasError = true;
                            errorMessage = "Serial " + serial + " for " + item.getProductName()
                                    + " does not exist or is not AVAILABLE.";
                            break;
                        }
                    }

                    InventoryAuditItemSerial itemSerial = new InventoryAuditItemSerial();
                    itemSerial.setAuditItemId(item.getId());
                    itemSerial.setSerial(serial);
                    itemSerial.setType(type);

                    serialsToInsert.add(itemSerial);
                }

                if (hasError)
                    break;
            }

            if (hasError) {
                request.setAttribute("message", errorMessage);
                request.setAttribute("audit", audit);
                request.setAttribute("discrepancyItems", discrepancyItems);
                request.getRequestDispatcher("/WEB-INF/view/audit/adjustInventoryAuditStock.jsp").forward(request,
                        response);
                return;
            }

            for (InventoryAuditItem item : discrepancyItems) {
                serialDAO.deleteSerialsByAuditItemId(item.getId());
            }

            for (InventoryAuditItemSerial itemSerial : serialsToInsert) {
                serialDAO.insertSerial(itemSerial);
            }

            inventoryAuditDAO.updateInventoryAuditStatus(auditId, InventoryAuditStatus.PENDING);

            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/InventoryAuditList");
        }
    }
}
