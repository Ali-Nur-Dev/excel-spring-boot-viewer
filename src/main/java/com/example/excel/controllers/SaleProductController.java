package com.example.excel.controllers;

import com.example.excel.services.SaleProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class SaleProductController {
    private final SaleProductService saleProductService;

    @GetMapping("/")
    public String saleProduct(Model model) {
        model.addAttribute("products", saleProductService.getSaleProducts());
        return "sale-product";
    }

    @PostMapping("/upload")
    public String uploadSaleProductsAsExcelFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            saleProductService.saveEachSaleProductFromExcelFileInArrayListAsSaleProductDto(file);
            return "redirect:/";
        } else {
            return "Your file is empty";
        }
    }

    @GetMapping("/chart-view")
    public String chartView(Model model) {
        model.addAttribute("statistics", saleProductService.getAllSaleProductsByMonth());
        return "chart-view";
    }
}
