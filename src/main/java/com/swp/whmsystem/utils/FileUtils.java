/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.utils;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class FileUtils {

    public static String saveFile(Part part, String uploadRealPath) {
        String fileName = UUID.randomUUID().toString() + "-" + part.getSubmittedFileName();

        Path uploadDir = Paths.get(uploadRealPath);
        if (Files.notExists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
                uploadDir.resolve(fileName); // an toàn hơn + "/"; tránh bị lỗi slash / \
                part.write(uploadDir.toString());
                return "/uploadImages/" + fileName;
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return null;
    }
}
