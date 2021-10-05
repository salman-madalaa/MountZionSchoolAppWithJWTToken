package com.zion.school.service;

import com.zion.school.model.SiblingInformation;
import com.zion.school.repo.SiblingInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Lenovo on 06-08-2021.
 */
@Service
public class SiblingInformationService {

    @Autowired
    private SiblingInformationRepository siblingInformationRepository;
    @Autowired
    private MessageSource messageSources;

    @Transactional
    public SiblingInformation create(SiblingInformation siblingInformation){
        SiblingInformation student1 = siblingInformationRepository.save(siblingInformation);
        return student1;
    }
    @Transactional
    public SiblingInformation update(SiblingInformation siblingInformation){
        return siblingInformationRepository.save(siblingInformation);
    }
    @Transactional
    public void delete(Integer id){
        siblingInformationRepository.deleteById(id);
    }

    @Transactional
    public List<SiblingInformation> getAll(){
        return siblingInformationRepository.findAll();
    }
    @Transactional
    public SiblingInformation get(Integer id){
        SiblingInformation student=siblingInformationRepository.getOne(id);
        return  student;
    }

}
