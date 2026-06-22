/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ImportRequestDetailDTO {
    private int purchaseRequestId;
    private Timestamp createdAt;
    private String createdBy;
    private String supplierName;
    private int supplierId;
}
