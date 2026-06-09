package com.swp.whmsystem.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.io.ByteArrayOutputStream;

public class BarcodeGenerator {

    public static byte[] generateBarcodeImage(String text) {
        try {
            Code128Writer barcodeWriter = new Code128Writer();
            // Tạo ma trận sọc với chiều rộng 300px, chiều cao 100px
            BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.CODE_128, 300, 100);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray(); // Trả về mảng byte của file ảnh PNG
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
