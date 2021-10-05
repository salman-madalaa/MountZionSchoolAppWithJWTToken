package com.zion.school.controller;

import com.zion.school.core.BaseController;
import com.zion.school.model.SiblingInformation;
import com.zion.school.service.SiblingInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Lenovo on 06-08-2021.
 */
@RestController
@RequestMapping(value = "siblingInformation")
public class SiblingInformationController extends BaseController {

    @Autowired
    private SiblingInformationService siblingInformationService;

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
    public SiblingInformation create(@RequestBody SiblingInformation siblingInformation) {
        return siblingInformationService.create(siblingInformation);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<SiblingInformation> getAllSiblingInformations() {
        return siblingInformationService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        siblingInformationService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SiblingInformation getSiblingInformation(@PathVariable("id") Integer id) {
        return siblingInformationService.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public SiblingInformation update(@PathVariable("id") Integer id, @RequestBody SiblingInformation student) {
        return siblingInformationService.update(student);
    }
    
}
