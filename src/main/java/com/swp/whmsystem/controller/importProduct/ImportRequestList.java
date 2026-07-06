package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.controller.purchaseRequest.PurchaseRequestList;
import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dal.PurchaseRequestDAO;
import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.model.*;
import com.swp.whmsystem.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.swp.whmsystem.model.User;
import com.swp.whmsystem.model.GoodReceipt;
import com.swp.whmsystem.dal.GoodReceiptDAO;
import com.swp.whmsystem.dal.SupplierDAO;
import com.swp.whmsystem.dto.PurchaseRequestDTO;
import com.swp.whmsystem.utils.AuthorizationUtils;
import com.swp.whmsystem.utils.PermissionConstants;

import java.util.ArrayList;

@WebServlet(name = "ImportRequestList", urlPatterns = {"/importRequestList"})
public class ImportRequestList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AuthorizationUtils.checkAccess(request, response, PermissionConstants.VIEW_IMPORT_HISTORY,
                "You are not authorized to view import history")) {
            return;
        }
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login");
                return;
            }
            PurchaseRequestDAO purchaseRequestDAO = new PurchaseRequestDAO();

            String pageSizeRaw = request.getParameter("pageSize");
            String pageRaw = request.getParameter("page");

            String codeStr = request.getParameter("code");
            String status = request.getParameter("status");
            String dateStr = request.getParameter("date");

            int pageSize = 10;
            int page = 1;

            if (pageSizeRaw != null && !pageSizeRaw.trim().isEmpty()) {
                try {
                    int parsedPageSize = Integer.parseInt(pageSizeRaw.trim());
                    if (parsedPageSize > 0 && parsedPageSize <= 100) {
                        pageSize = parsedPageSize;
                    }
                } catch (NumberFormatException ignored) {
                    pageSize = 10;
                }
            }

            if (pageRaw != null && !pageRaw.trim().isEmpty()) {
                try {
                    page = Math.max(1, Integer.parseInt(pageRaw.trim()));
                } catch (NumberFormatException ignored) {
                    page = 1;
                }
            }

            int id = 0;
            if (codeStr != null && !codeStr.isEmpty()) {
                try {
                    id = Integer.parseInt(codeStr.replace("PR-", "").trim());
                } catch (NumberFormatException e) {
                    // ignore
                }
            }

            int totalRecords = purchaseRequestDAO.countImportRequest(id, status, dateStr);
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

            List<PurchaseRequest> purchaseRequests = purchaseRequestDAO.searchImportRequest(id, status, dateStr, pageSize, page);
            List<PurchaseRequestDTO> importRequests = new ArrayList<>();
            for (PurchaseRequest i : purchaseRequests) {
                importRequests.add(toPurchaseDTO(i));
            }

            request.setAttribute("importRequests", importRequests);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("page", page);
            request.setAttribute("totalPages", totalPages);
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/view/import/importRequestList.jsp").forward(request, response);
        }
        request.getRequestDispatcher("WEB-INF/view/import/importRequestList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private PurchaseRequestDTO toPurchaseDTO(PurchaseRequest pr) {
        SupplierDAO supplierDao = new SupplierDAO();
        PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
        PurchaseRequestDTO dto = new PurchaseRequestDTO();
        dto.setPurchaseRequestId(pr.getId());
        dto.setCreatedAt(pr.getCreatedAt());
        dto.setNote(pr.getNote());
        dto.setStatus(pr.getStatus());
        dto.setSupplier(supplierDao.getSupplierById(pr.getSupplierId()).getSupplierName());
        int total = 0;
        List<PurchaseItem> purchaseItems = purchaseItemDAO.getItemsByPurchaseRequestId(pr.getId());
        for (PurchaseItem i : purchaseItems) {
            total += i.getRequiredQty();
        }
        dto.setTotalItem(total);
        dto.setTotalPrice(pr.getTotalPrice());
        return dto;
    }

}
