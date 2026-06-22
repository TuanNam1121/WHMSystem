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
import lombok.ToString;

/**
 *
 * @author Admin
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PurchaseRequestDTO {
    int purchaseRequestId;
    String supplier;
    String note;
    String status;
    Timestamp createdAt;
    int totalItem;
}
