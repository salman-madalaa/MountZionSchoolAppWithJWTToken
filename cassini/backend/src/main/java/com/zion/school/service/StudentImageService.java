package com.zion.school.service;

import com.zion.school.model.StudentImage;
import com.zion.school.repo.StudentImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Lenovo on 20-08-2021.
 */
@Service
public class StudentImageService {

    @Autowired
    private StudentImageRepo studentImageRepo;
    
    @Transactional
    public StudentImage uploadImage(StudentImage studentImage){
        StudentImage student1 = studentImageRepo.save(studentImage);
        return student1;
    }
    @Transactional
    public StudentImage updateImage(StudentImage studentImage){
        return studentImageRepo.save(studentImage);
    }
    @Transactional
    public void deleteImage(Integer id){
        studentImageRepo.deleteById(id);
    }



}
