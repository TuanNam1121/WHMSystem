/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class FileUtils {

    private static final String FOLDER_PATH = "assets/img/product";

    public static String saveFile(Part filePart, HttpServletRequest request) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

        // Save to /target (load immediately after upload, missing after redeploy)
        // Paths.get auto control "\" or "/" --> Ok with all OS
        Path targetBase = Paths.get(request.getServletContext().getRealPath(""));
        Path targetDir = targetBase.resolve(FOLDER_PATH);

        if (Files.notExists(targetDir)) Files.createDirectories(targetDir);

        Path targetFilePath = targetDir.resolve(fileName);
        filePart.write(targetFilePath.toString());

        // Save to /src (Not load immediately after upload, keeping after redeploy)
        try {
            Path projectRoot = targetBase.getParent().getParent();
            Path sourceDir = projectRoot.resolve(Paths.get("src", "main", "webapp", FOLDER_PATH));

            if (Files.notExists(sourceDir)) Files.createDirectories(sourceDir);

            Path sourceFilePath = sourceDir.resolve(fileName);
            Files.copy(targetFilePath, sourceFilePath, StandardCopyOption.REPLACE_EXISTING); // Can throw IOException

        } catch (Exception e) {
            System.err.println("Fail to save to src: " + e.getMessage());
        }
        // Return for web display (user "/")
        return "/" + FOLDER_PATH + "/" + fileName;
    }
}
