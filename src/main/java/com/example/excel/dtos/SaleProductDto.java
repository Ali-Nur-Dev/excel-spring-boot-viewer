package com.example.excel.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class SaleProductDto {
    private int id;
    private String name;
    private int price;
    private int quantity;
    private int finalPrice;
    private String date;
}
