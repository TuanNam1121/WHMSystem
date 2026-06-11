/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class ImportHistoryDTO {
    private int receiptId;
    private int purchaseRequestId;
    private String supplier;
    private String importBy;
    private int items;
    private String total; 
    private String status;
    private LocalDateTime completedAt;
}
