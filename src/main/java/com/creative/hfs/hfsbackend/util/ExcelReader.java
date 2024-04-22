package com.creative.hfs.hfsbackend.util;

import com.creative.hfs.hfsbackend.model.entity.EmployeeRecord;
import com.creative.hfs.hfsbackend.model.enums.Department;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {
    public List<EmployeeRecord> readExcel(String filePath) throws IOException {
        List<EmployeeRecord> employeeRecords = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            EmployeeRecord employeeRecord = new EmployeeRecord();
            employeeRecord.setEmployeeId((int) row.getCell(0).getNumericCellValue()); // Assuming employeeId is a numeric value
            employeeRecord.setFirstName(getCellStringValue(row.getCell(1))); // Assuming first name is in the second column (index 1)
            employeeRecord.setLastName(getCellStringValue(row.getCell(2))); // Assuming last name is in the third column (index 2)
            employeeRecord.setDesignation(getCellStringValue(row.getCell(3))); // Assuming designation is in the fourth column (index 3)
            employeeRecord.setBusinessUnitName(getCellStringValue(row.getCell(4)));
            // Assuming business unit name is in the fifth column (index 4)
            employeeRecord.setGrade(getCellStringValue(row.getCell(5))); // Assuming grade is in the sixth column (index 5)
            employeeRecord.setStatus(getCellStringValue(row.getCell(6))); // Assuming status is in the seventh column (index 6)

            // Set department ID based on business unit name
            setDepartmentIdBasedOnBusinessUnit(employeeRecord);

            employeeRecords.add(employeeRecord);
        }

        workbook.close();
        inputStream.close();

        return employeeRecords;
    }

    private void setDepartmentIdBasedOnBusinessUnit(EmployeeRecord employeeRecord) {
        String businessUnitName = employeeRecord.getBusinessUnitName();

        if (businessUnitName != null) {
            // Mapping department names to enum constants
            Map<String, Department> departmentMap = new HashMap<>();
            departmentMap.put("Digital Product Engineering (CAE)", Department.DIGITAL_PRODUCT_ENGINEERING_CAE);
            departmentMap.put("Intelligent Connected Products", Department.INTELLIGENT_CONNECTED_PRODUCTS);
            departmentMap.put("Digital Plant And Process Engineering", Department.DIGITAL_PLANT_AND_PROCESS_ENGINEERING);
            departmentMap.put("Digital Product Engineering (H&I)", Department.DIGITAL_PRODUCT_ENGINEERING_HI);
            departmentMap.put("Digital Product Engineering (ACE)", Department.DIGITAL_PRODUCT_ENGINEERING_ACE);
            departmentMap.put("Digital Discrete Manufacturing Engineering", Department.DIGITAL_DISCRETE_MANUFACTURING_ENGINEERING);
            departmentMap.put("In-Client Services", Department.IN_CLIENT_SERVICES);
            departmentMap.put("Corporate", Department.CORPORATE);
            departmentMap.put("Quality Assurance and Business Excellence", Department.QUALITY_ASSURANCE_AND_BUSINESS_EXCELLENCE);
            departmentMap.put("Talent Acquisition", Department.TALENT_ACQUISITION);
            departmentMap.put("Finance & Accounts", Department.FINANCE_AND_ACCOUNTS);
            departmentMap.put("Infrastructure Management Group-Admin", Department.INFRASTRUCTURE_MANAGEMENT_GROUP_ADMIN);
            departmentMap.put("Digital Technologies", Department.DIGITAL_TECHNOLOGIES);
            departmentMap.put("Work Force Enablement", Department.WORK_FORCE_ENABLEMENT);
            departmentMap.put("Human Resources", Department.HUMAN_RESOURCES);
            departmentMap.put("Market Research and Sales Enablement", Department.MARKET_RESEARCH_AND_SALES_ENABLEMENT);
            departmentMap.put("Marketing", Department.MARKETING);
            departmentMap.put("Business Development", Department.BUSINESS_DEVELOPMENT);

            // Look up the department name in the map
            Department department = departmentMap.get(businessUnitName);
            if (department != null) {
                employeeRecord.setDepartmentId(department.getId());
                return;
            }
        }

        // If no match is found or business unit name is null, set department ID to a default value or handle accordingly
        employeeRecord.setDepartmentId(6);
    }




    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Check if the numeric value is a date
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // You may need to format the date according to your requirement
                } else {
                    return NumberToTextConverter.toText(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                // Evaluate the formula cell and return its value
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);
                return cellValue.formatAsString();
            default:
                return null;
        }
    }

}
