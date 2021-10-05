package com.zion.school.helper;


import com.zion.school.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"RegistrationId", "FirstName", "LastName", "FatherName", "MotherName", "MobileNumber", "PresentAddress",
            "PermanentAddress", "SamagraId", "DateOfAdmission", "ClassToJoin", "Gender", "DateOfBirth", "MarksOfIdentification",
            "Religion", "Caste", "CastId", "AadharNumber", "BankAccountNumber", "IfscCode", "ChildHandicapped", "FatherMotherExpired",
            "Siblings", "RteStudent","sibInfo"};
    static String SHEET = "Students";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static ByteArrayInputStream studentsToExcel(List<Student> students) {

        try  {
            XSSFWorkbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            XSSFSheet sheet = workbook.createSheet(SHEET);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Header
            XSSFRow headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (Student student : students) {
                XSSFRow row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(student.getRegistrationId());
                row.createCell(1).setCellValue(student.getFirstName());
                row.createCell(2).setCellValue(student.getLastName());
                row.createCell(3).setCellValue(student.getFatherName());

                row.createCell(4).setCellValue(student.getMotherName());
                row.createCell(5).setCellValue(student.getMobileNumber().doubleValue());
                row.createCell(6).setCellValue(student.getPresentAddress());
                row.createCell(7).setCellValue(student.getPermanentAddress());

                row.createCell(8).setCellValue(student.getSamagraId());
                row.createCell(9).setCellValue((Date) student.getDateOfAdmission());
                row.createCell(10).setCellValue(student.getClassToJoin());
                row.createCell(11).setCellValue(student.getGender());

                row.createCell(12).setCellValue((Date) student.getDateOfBirth());
                row.createCell(13).setCellValue(student.getMarksOfIdentification());
                row.createCell(14).setCellValue(student.getReligion());
                row.createCell(15).setCellValue(student.getCaste());

                row.createCell(16).setCellValue(student.getCastId());
                row.createCell(17).setCellValue((Long) student.getAadharNumber());
                row.createCell(18).setCellValue((Long) student.getBankAccountNumber());
                row.createCell(19).setCellValue(student.getIfscCode());

                row.createCell(20).setCellValue(student.getChildHandicapped().booleanValue());
                row.createCell(21).setCellValue(student.getFatherMotherExpired().booleanValue());
                row.createCell(22).setCellValue(student.getSiblings().booleanValue());
                row.createCell(23).setCellValue(student.getRteStudent().booleanValue());

                row.createCell(24).setCellValue(String.valueOf(student.getSiblingInformation().toArray()));
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
//            FileOutputStream fos = new FileOutputStream("../resources/static/");
//            workbook.write(fos);
//            workbook.close();
//            fos.close();
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<Student> excelToStudents(InputStream is) {
        try {

            List<Student> students = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(is);

            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int index = 0; index < sheet.getPhysicalNumberOfRows(); index++) {
                if (index > 0) {
                    Student student = new Student();

                    XSSFRow row = sheet.getRow(index);
                    String id = (String) row.getCell(0).getStringCellValue();
                    Date dateofAdmission = row.getCell(9).getDateCellValue();

                    student.setRegistrationId(id);
                    student.setFirstName(row.getCell(1).getStringCellValue());
                    student.setLastName(row.getCell(2).getStringCellValue());
                    student.setFatherName(row.getCell(3).getStringCellValue());

                    student.setMotherName(row.getCell(4).getStringCellValue());
                    student.setMobileNumber((long) row.getCell(5).getNumericCellValue());
                    student.setPresentAddress(row.getCell(6).getStringCellValue());

                    student.setPermanentAddress(row.getCell(7).getStringCellValue());
                    student.setSamagraId(row.getCell(8).getStringCellValue());
                    student.setDateOfAdmission(row.getCell(9).getDateCellValue());
                   // student.setDateOfAdmission(new Date(dateofAdmission.getDate()));

                    student.setClassToJoin(row.getCell(10).getStringCellValue());
                    student.setGender(row.getCell(11).getStringCellValue());
                    student.setDateOfBirth(row.getCell(12).getDateCellValue());

                    student.setMarksOfIdentification(row.getCell(13).getStringCellValue());
                    student.setReligion(row.getCell(14).getStringCellValue());
                    student.setCaste(row.getCell(15).getStringCellValue());

                    student.setCastId(row.getCell(16).getStringCellValue());
                    student.setAadharNumber((long) row.getCell(17).getNumericCellValue());
                    student.setBankAccountNumber((long) row.getCell(18).getNumericCellValue());

                    student.setIfscCode(row.getCell(19).getStringCellValue());
                    student.setChildHandicapped(row.getCell(20).getBooleanCellValue());
                    student.setFatherMotherExpired(row.getCell(21).getBooleanCellValue());

                    student.setSiblings(row.getCell(22).getBooleanCellValue());
                    student.setRteStudent(row.getCell(23).getBooleanCellValue());


                    students.add(student);
                }
            }

            return students;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
