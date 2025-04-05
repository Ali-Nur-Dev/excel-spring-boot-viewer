package com.example.excel.services;

import com.example.excel.dtos.SaleProductDto;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class SaleProductService {
    private ArrayList<SaleProductDto> products = new ArrayList<>();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ArrayList<SaleProductDto> getSaleProducts() {
        return products;
    }

    public void saveEachSaleProductFromExcelFileInArrayListAsSaleProductDto(MultipartFile file) {
        try {
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = wb.getSheetAt(0);
            for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                SaleProductDto saleProductDto = new SaleProductDto();
                for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        switch (cell.getCellType()) {
                            case NUMERIC -> {
                                switch (cellNum) {
                                    case 0 -> saleProductDto.setId((int) cell.getNumericCellValue());
                                    case 2 -> saleProductDto.setPrice((int) cell.getNumericCellValue());
                                    case 3 -> saleProductDto.setQuantity((int) cell.getNumericCellValue());
                                    case 4 -> saleProductDto.setFinalPrice((int) cell.getNumericCellValue());
                                    case 5 -> {
                                        saleProductDto.setDate(simpleDateFormat.format(cell.getDateCellValue()));
                                    }
                                }
                            }
                            case STRING -> {
                                if (cellNum == 1) saleProductDto.setName(cell.getStringCellValue());
                            }
                        }
                    } else {
                        continue;
                    }
                }
                if (saleProductDto.getName() != null) {
                    products.add(saleProductDto);
                }
            }
            ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public LinkedHashMap<String, Integer> getAllSaleProductsByMonth() {
        LinkedHashMap<String, Integer> salesByMonths = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            salesByMonths.put(Month.of(i).toString(), 0);
        }
        for (SaleProductDto product : getSaleProducts()) {
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(simpleDateFormat.parse(product.getDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            int month = cal.get(Calendar.MONTH) + 1; // Добавляем +1, т.к. MONTH начинается с 0
            String monthName = Month.of(month).toString();
            salesByMonths.put(monthName, salesByMonths.get(monthName) + product.getQuantity());
        }
        return salesByMonths;
    }
}
