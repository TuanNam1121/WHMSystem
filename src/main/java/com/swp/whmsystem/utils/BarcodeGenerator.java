package com.swp.whmsystem.utils;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/barcode")
public class BarcodeGenerator extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code == null || code.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing code parameter");
            return;
        }

        // Thiết lập trả về định dạng ảnh PNG
        response.setContentType("image/png");

        try (OutputStream out = response.getOutputStream()) {
            // Khởi tạo bộ ghi mã vạch chuẩn CODE_128 của ZXing
            Code128Writer barcodeWriter = new Code128Writer();

            // Tạo ma trận bit cho mã vạch (Dài: 250px, Cao: 80px)
            BitMatrix bitMatrix = barcodeWriter.encode(code, BarcodeFormat.CODE_128, 250, 80);

            // Ghi ma trận này trực tiếp vào luồng xuất (OutputStream) dưới dạng ảnh PNG
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);

        } catch (Exception e) {
            throw new ServletException("Lỗi khi sinh mã vạch bằng ZXing", e);
        }
    }
}
