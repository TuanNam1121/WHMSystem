package com.swp.whmsystem.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTransactionDTO {
    private int id;
    private String code;
    private String type;
    private Timestamp date;
    private String createdBy;
}
