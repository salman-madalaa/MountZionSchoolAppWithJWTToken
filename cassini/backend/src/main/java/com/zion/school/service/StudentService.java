package com.zion.school.service;


import com.zion.school.exceptions.SchoolException;
import com.zion.school.helper.ImageHelper;
import com.zion.school.model.Student;
import com.zion.school.model.StudentImage;
import com.zion.school.repo.StudentImageRepo;
import com.zion.school.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lenovo on 01-03-2021.
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MessageSource messageSources;

    @Autowired
    private StudentImageRepo studentImageRepo;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public Student create(Student student) {
        Student student1;
        String result;
        Optional<Student> existStudent = studentRepository.findByRegistrationId(student.getRegistrationId());
        if (existStudent.isPresent()) {
            result = MessageFormat.format("Student RegistrationId " + existStudent.get().getRegistrationId() + " Already exist.", existStudent.get().getRegistrationId());
            throw new SchoolException(result);
        }
        student1 = studentRepository.save(student);
        return student1;
    }

    @Transactional
    public Student update(Long id,Student student) {
        return  studentRepository.save(student);
//        String message=" ";
//        Optional<Student> existStudent = studentRepository.findById(student.getId());
//        if (existStudent.isPresent()) {
//            return  studentRepository.save(student);
////            message = MessageFormat.format("Student RegistrationId " + existStudent.get().getRegistrationId() + " Already exist.", existStudent.get().getRegistrationId());
////            throw new SchoolException(message);
//        }else {
//            message = MessageFormat.format("Student RegistrationId " + existStudent.get().getRegistrationId() + " Already exist.", existStudent.get().getRegistrationId());
//            throw new SchoolException(message);
//        }

    }

    @Transactional
    public void delete(long id) {
        Optional<StudentImage> stuImage = studentImageRepo.findById(id);
        if (stuImage.isPresent()) {
            studentImageRepo.deleteById(id);
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public List<Student> getAll() {
        return studentRepository.findAllByOrderByRegistrationIdAsc();
    }

    @Transactional
    public Student get(Long registrationId) {
        Optional<Student> student = studentRepository.findById(registrationId);
        return student.get();
    }

//    public Page<Student> getAllStudentsByPage(Pageable pageable){
//        Page<Student> students=studentRepository.findAll(pageable);
//        return  students;
//    }


    //--------------------Normal Students--------------------------------------

    public Integer getTotalNormalStudentsCount() {
        return studentRepository.findByRteStudentFalseOrderByRegistrationIdAsc().size();
    }

    public List<Student> getTotalNormalStudents() {
        List<Student> students = studentRepository.findByRteStudentFalseOrderByRegistrationIdAsc();
        for (Student student : students) {
            Optional<StudentImage> studentImage = studentImageRepo.findById(student.getId());
            if (studentImage.isPresent()) {
                StudentImage studentImage1 = studentImage.get();
                StudentImage img = new StudentImage(studentImage1.getId(), studentImage1.getName(), studentImage1.getType(),
                        ImageHelper.decompressBytes(studentImage1.getPicByte()));

                student.setStudentImage(img);
            } else {
                student.setStudentImage(null);
            }
        }

        return students;
    }

    public Integer getClassStudentsCount(String className) {
        return studentRepository.findByClassToJoinAndRteStudentFalseOrderByRegistrationIdAsc(className).size();
    }

    public List<Student> getIndividualNormalClassStudents(String className) {
        List<Student> students = studentRepository.findByClassToJoinAndRteStudentFalseOrderByRegistrationIdAsc(className);
        for (Student student : students) {
            Optional<StudentImage> studentImage = studentImageRepo.findById(student.getId());
            if (studentImage.isPresent()) {
                StudentImage studentImage1 = studentImage.get();
                StudentImage img = new StudentImage(studentImage1.getId(), studentImage1.getName(), studentImage1.getType(),
                        ImageHelper.decompressBytes(studentImage1.getPicByte()));

                student.setStudentImage(img);
            } else {
                student.setStudentImage(null);
            }
        }

        return students;
    }


    //--------------------RTE--------------------------------------

    public Integer getClassRteStudentsCount(String className) {
        return studentRepository.findByRteStudentTrueAndClassToJoinOrderByRegistrationIdAsc(className).size();
    }


    public List<Student> getIndividualRteClassStudents(String className) {

        List<Student> students = studentRepository.findByRteStudentTrueAndClassToJoinOrderByRegistrationIdAsc(className);
        for (Student student : students) {
            Optional<StudentImage> studentImage = studentImageRepo.findById(student.getId());
            if (studentImage.isPresent()) {
                StudentImage studentImage1 = studentImage.get();
                StudentImage img = new StudentImage(studentImage1.getId(), studentImage1.getName(), studentImage1.getType(),
                        ImageHelper.decompressBytes(studentImage1.getPicByte()));

                student.setStudentImage(img);
            } else {
                student.setStudentImage(null);
            }
        }

        return students;


    }

    public List<Student> getTotalRteStudents() {
        List<Student> students = studentRepository.findByRteStudentTrueOrderByRegistrationIdAsc();
        for (Student student : students) {
            Optional<StudentImage> studentImage = studentImageRepo.findById(student.getId());
            if (studentImage.isPresent()) {
                StudentImage studentImage1 = studentImage.get();
                StudentImage img = new StudentImage(studentImage1.getId(), studentImage1.getName(), studentImage1.getType(),
                        ImageHelper.decompressBytes(studentImage1.getPicByte()));

                student.setStudentImage(img);
            } else {
                student.setStudentImage(null);
            }
        }

        return students;

    }

    public Integer getTotalRteStudentsCount() {
        return studentRepository.findByRteStudentTrueOrderByRegistrationIdAsc().size();
    }
}
