package com.zion.school.service;


import com.zion.school.exceptions.SchoolException;
import com.zion.school.helper.ExcelHelper;
import com.zion.school.message.ResponseMessage;
import com.zion.school.model.Student;
import com.zion.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ExcelService {
    @Autowired
    StudentRepository studentRepository;


    public ResponseEntity<ResponseMessage> save(MultipartFile file) {
        String message = "";

        try {
            List<Student> students = ExcelHelper.excelToStudents(file.getInputStream());

//            studentRepository.saveAll(students);

//            for(Student student:students){
//                Optional<Student> existStudent= studentRepository.findByRegistrationId(student.getRegistrationId());
//                if(existStudent.isPresent()){
//                    message = MessageFormat.format("Student RegistrationId " + existStudent.get().getRegistrationId() + " Already exist.", existStudent.get().getRegistrationId());
//                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(message));
//                }
//                else {
//                    studentRepository.save(student);
//                }
//            }




          //------------------save import data based duplicate RegistrationIds  on conditions code -----------------------
            ArrayList<String> registrationIds=new ArrayList<String>();
            for(Student student:students) {
                 registrationIds.add(student.getRegistrationId());
            }
            Set<String> duplicateRegIds = registrationIds.stream().filter(i -> Collections.frequency(registrationIds, i) > 1).collect(Collectors.toSet());
            if(duplicateRegIds.isEmpty()){

                ArrayList<String> isExistStudents=new ArrayList<String>();
                for(String registrationId:registrationIds) {
                    Optional<Student> existStudent= studentRepository.findByRegistrationId(registrationId);
                    if(existStudent.isPresent()){
                        isExistStudents.add(existStudent.get().getRegistrationId());
                    }
                }

                if(isExistStudents.isEmpty()){
                    studentRepository.saveAll(students);
                }else{
                    message = MessageFormat.format("Some given RegistrationId's Already exist in database.",isExistStudents);
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(message));
                }


            }else{
                message = MessageFormat.format("duplicate RegistrationId's are present in your import file",duplicateRegIds);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(message));
            }

        } catch (Exception e) {
            message =  file.getOriginalFilename() + "file is not in a required formate";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

        message = "Imported the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    public ByteArrayInputStream load() {
        List<Student> students = studentRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.studentsToExcel(students);
        return in;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public ByteArrayInputStream loadStudents(Boolean isRteStudent, String classname) {
        List<Student> students;
        if (isRteStudent) {
            if (classname.equals("all")) {
                students = studentRepository.findByRteStudentTrueOrderByRegistrationIdAsc();
            } else {
                students = studentRepository.findByRteStudentTrueAndClassToJoinOrderByRegistrationIdAsc(classname);
            }
        } else {
            if (classname.equals("all")) {
                students = studentRepository.findByRteStudentFalseOrderByRegistrationIdAsc();
            } else {
                students = studentRepository.findByClassToJoinAndRteStudentFalseOrderByRegistrationIdAsc(classname);
            }
        }

        ByteArrayInputStream in = ExcelHelper.studentsToExcel(students);
        return in;
    }


}
