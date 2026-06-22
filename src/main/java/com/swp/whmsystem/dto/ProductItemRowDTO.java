/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.swp.whmsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Admin
 */
@Getter @Setter 
@AllArgsConstructor @NoArgsConstructor @ToString
public class ProductItemRowDTO {
    private int productId;
    private String productName;
    private String serial;
    private String unit;
    private int importedPrice;
}
