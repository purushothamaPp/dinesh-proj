package com.creative.hfs.hfsbackend.util;

import com.creative.hfs.hfsbackend.model.entity.EmployeeRecord;
import com.creative.hfs.hfsbackend.repository.EmployeeRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExcelDataService {

    @Autowired
    private EmployeeRecordRepository employeeRecordRepository;

    @Value("${excel.batch.size}")
    private int batchSize;

    public void processExcelData(String excelFilePath) {
        try {
            ExcelReader excelReader = new ExcelReader();
            List<EmployeeRecord> employeeRecords = excelReader.readExcel(excelFilePath);

            for (int i = 0; i < employeeRecords.size(); i += batchSize) {
                List<EmployeeRecord> batch = employeeRecords.subList(i, Math.min(i + batchSize, employeeRecords.size()));
                employeeRecordRepository.saveAll(batch);
                employeeRecordRepository.flush();
            }
        } catch (Exception e) {
            log.error("Error processing excel data: " + excelFilePath, e);
        }
    }




}
