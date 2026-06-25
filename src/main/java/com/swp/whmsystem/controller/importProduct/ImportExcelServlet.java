package com.swp.whmsystem.controller.importProduct;

import com.swp.whmsystem.dal.ProductDAO;
import com.swp.whmsystem.dal.PurchaseItemDAO;
import com.swp.whmsystem.dto.ProductItemRowDTO;
import com.swp.whmsystem.model.Product;
import com.swp.whmsystem.model.PurchaseItem;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.utils.ProductItemValidation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ImportExcel", urlPatterns = { "/ImportExcel" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class ImportExcelServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            int handler = user.getId();

            // Get purchaseRequestId
            String purchaseRequestIdRaw = request.getParameter("purchaseRequestId");
            int purchaseRequestId;
            try {
                purchaseRequestId = Integer.parseInt(purchaseRequestIdRaw);
            } catch (NumberFormatException ex) {
                session.setAttribute("message", "Invalid Purchase Request ID.");
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/importRequestList");
                return;
            }

            // Get invoice number
            String invoiceNumber = request.getParameter("invoiceNumber");

            // Get uploaded file
            Part filePart = request.getPart("excelFile");
            if (filePart == null || filePart.getSize() == 0) {
                session.setAttribute("message", "Please select an Excel file to upload.");
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                return;
            }

            // Validate file extension
            String fileName = filePart.getSubmittedFileName();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                session.setAttribute("message", "Invalid file format. Please upload an Excel file (.xlsx or .xls).");
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                return;
            }

            // Build a map of productId -> price from the PurchaseItems of this PR
            PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAO();
            List<PurchaseItem> purchaseItems = purchaseItemDAO.getItemsByPurchaseRequestId(purchaseRequestId);
            Map<Integer, Integer> productPriceMap = new HashMap<>();
            for (PurchaseItem pi : purchaseItems) {
                productPriceMap.put(pi.getProductId(), pi.getPrice());
            }

            // Parse Excel file
            ProductDAO productDAO = new ProductDAO();
            List<ProductItemRowDTO> filledList = new ArrayList<>();
            List<String> errors = new ArrayList<>();

            try (InputStream inputStream = filePart.getInputStream();
                    Workbook workbook = new XSSFWorkbook(inputStream)) {

                Sheet sheet = workbook.getSheetAt(0);
                if (sheet == null) {
                    session.setAttribute("message", "Excel file is empty or has no sheets.");
                    session.setAttribute("messageType", "danger");
                    response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                    return;
                }

                int lastRow = sheet.getLastRowNum();
                // Start from row 1 (skip header row 0)
                for (int i = 1; i <= lastRow; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null)
                        continue;

                    // Column 0: STT (skip, just index)
                    // Column 1: SKU
                    Cell skuCell = row.getCell(1);
                    // Column 2: Serial
                    Cell serialCell = row.getCell(2);

                    if (skuCell == null || serialCell == null) {
                        errors.add("Row " + (i + 1) + ": SKU or Serial is empty.");
                        continue;
                    }

                    String sku = getCellStringValue(skuCell).trim();
                    String serial = getCellStringValue(serialCell).trim();

                    if (sku.isEmpty()) {
                        errors.add("Row " + (i + 1) + ": SKU is empty.");
                        continue;
                    }
                    if (serial.isEmpty()) {
                        errors.add("Row " + (i + 1) + ": Serial is empty.");
                        continue;
                    }

                    // Look up product by SKU
                    Product product = productDAO.getProductFromSKU(sku);
                    if (product == null) {
                        errors.add("Row " + (i + 1) + ": SKU '" + sku + "' does not exist in the system.");
                        continue;
                    }

                    int productId = product.getProductId();

                    // Check that this product belongs to the purchase request
                    if (!productPriceMap.containsKey(productId)) {
                        errors.add("Row " + (i + 1) + ": Product SKU '" + sku + "' (" + product.getName()
                                + ") is not in this Purchase Request.");
                        continue;
                    }

                    int itemPrice = productPriceMap.get(productId);

                    ProductItemRowDTO dto = new ProductItemRowDTO();
                    dto.setProductId(productId);
                    dto.setSerial(serial);
                    dto.setImportedPrice(itemPrice);
                    filledList.add(dto);
                }
            }

            // Reject entire file if there are any errors
            if (!errors.isEmpty()) {
                StringBuilder errorMsg = new StringBuilder("Excel import failed. Errors found:<br>");
                for (String err : errors) {
                    errorMsg.append("• ").append(err).append("<br>");
                }
                session.setAttribute("message", errorMsg.toString());
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                return;
            }

            if (filledList.isEmpty()) {
                session.setAttribute("message", "Excel file contains no valid data rows.");
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                return;
            }

            // Validate invoice number
            String validInvoiceNumber = ProductItemValidation.validateInvoiceNumber(invoiceNumber);
            if (!"true".equals(validInvoiceNumber)) {
                session.setAttribute("message", validInvoiceNumber);
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                return;
            }

            // Validate serials (duplicates, existing in system)
            String validSerials = ProductItemValidation.validateProductItem(filledList);
            if (!"true".equals(validSerials)) {
                session.setAttribute("message", validSerials);
                session.setAttribute("messageType", "danger");
                response.sendRedirect(request.getContextPath() + "/ImportProduct?prId=" + purchaseRequestId);
                return;
            }

            // All good - perform the import using shared logic from ImportProduct
            ImportProduct importProduct = new ImportProduct();
            importProduct.handleImport(purchaseRequestId, handler, invoiceNumber, filledList);

            // Clear session attributes
            session.removeAttribute("list");
            session.removeAttribute("prCode");
            session.removeAttribute("approvedAt");
            session.removeAttribute("handler");
            session.removeAttribute("creator");
            session.removeAttribute("totalItem");

            session.setAttribute("message", "Excel import saved successfully! " + filledList.size()
                    + " items imported. Inventory has been updated.");
            session.setAttribute("messageType", "success");
            response.sendRedirect(request.getContextPath() + "/importRequestList");

        } catch (Exception ex) {
            session.setAttribute("message", "Error processing Excel import: " + ex.getMessage());
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/importRequestList");
        }
    }

    /**
     * Get string value from a cell regardless of cell type.
     */
    private String getCellStringValue(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Avoid scientific notation for numbers used as SKU/Serial
                double numVal = cell.getNumericCellValue();
                if (numVal == Math.floor(numVal) && !Double.isInfinite(numVal)) {
                    return String.valueOf((long) numVal);
                }
                return String.valueOf(numVal);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return "";
        }
    }
}
