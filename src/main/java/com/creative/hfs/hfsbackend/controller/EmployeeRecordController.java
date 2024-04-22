package com.creative.hfs.hfsbackend.controller;



import com.creative.hfs.hfsbackend.exceptions.DuplicateEntryException;
import com.creative.hfs.hfsbackend.model.dto.EmployeeRecordDTO;
import com.creative.hfs.hfsbackend.service.EmployeeRecordService;
import com.creative.hfs.hfsbackend.service.EmployeeService;
import com.creative.hfs.hfsbackend.util.ExcelDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class EmployeeRecordController {


    @Autowired
    private EmployeeRecordService employeeRecordService;

    @Autowired
    private ExcelDataService excelDataService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public EmployeeRecordDTO getEmployeeRecordById(@PathVariable Integer employeeId) {
        return employeeRecordService.getEmployeeRecordById(employeeId);
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload an Excel file.");
        }

        try {
            // Save the uploaded file temporarily
            Path tempFilePath = Files.createTempFile("temp", ".xlsx");
            Files.write(tempFilePath, file.getBytes());

            // Call the service to validate the Excel file format
            if (!isValidExcelFile(tempFilePath)) {
                // If the Excel file format is invalid, return a bad request response
                Files.deleteIfExists(tempFilePath); // Delete the temporary file
                return ResponseEntity.badRequest().body("Invalid Excel format: Headers do not match expected format.");
            }


//            if (!isValidExcelFileWithEmployeeIdValidation(tempFilePath)){
//                Files.deleteIfExists(tempFilePath); // Delete the temporary file
//                return ResponseEntity.badRequest().body("Duplicate User Found");
//            }

            // Call the service to process the Excel data
            excelDataService.processExcelData(tempFilePath.toString());

            // Delete the temporary file
            Files.deleteIfExists(tempFilePath);

            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully.");
        } catch (DuplicateEntryException ex) {
            // If a conflict occurs, return a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing the file: " + e.getMessage());
        }
    }




    @GetMapping("/employees-list")
    public ResponseEntity<List<EmployeeRecordDTO>> getAllEmployeeRecords() {
        List<EmployeeRecordDTO> employeeRecords = employeeRecordService.getAllEmployeeRecords();
        return new ResponseEntity<>(employeeRecords, HttpStatus.OK);
    }



    private boolean isValidExcelFile(Path filePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(String.valueOf(filePath));
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            // Assuming data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0); // Assuming header is in the first row

            if (headerRow == null ||
                    !headerRow.getCell(0).getStringCellValue().equals("employeeId") ||
                    !headerRow.getCell(1).getStringCellValue().equals("firstName") ||
                    !headerRow.getCell(2).getStringCellValue().equals("lastName") ||
                    !headerRow.getCell(3).getStringCellValue().equals("designation") ||
                    !headerRow.getCell(4).getStringCellValue().equals("businessUnitName") ||
                    !headerRow.getCell(5).getStringCellValue().equals("grade") ||
                    !headerRow.getCell(6).getStringCellValue().equals("status")

            ) {

                return false; // Invalid format

            }

            return true; // Valid format
        }

    }

//    private boolean isValidExcelFileWithEmployeeIdValidation(Path filePath) throws IOException {
//        if (!isValidExcelFile(filePath)) {
//            return false;
//        }
//
//        try (FileInputStream inputStream = new FileInputStream(String.valueOf(filePath));
//             Workbook workbook = WorkbookFactory.create(inputStream)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) {
//                    continue; // Skip header row
//                }
//
//                Integer employeeId = (int) row.getCell(0).getNumericCellValue();
//                if (employeeService.findEmployeeDtoById(employeeId).isPresent()) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }

}
