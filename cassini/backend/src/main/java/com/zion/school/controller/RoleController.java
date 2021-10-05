package com.zion.school.controller;

import com.zion.school.core.BaseController;
import com.zion.school.model.security.Role;
import com.zion.school.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Lenovo on 20-09-2021.
 */
@RestController
@RequestMapping(value = "roles")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public Role createRole(@RequestBody Role role){
        return  roleService.createNewRole(role);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Role> getAll(){
        return  roleService.getAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Role updateRole(@PathVariable("id") Long id,@RequestBody Role role){
        return  roleService.updateRole(id, role);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void updateRole(@PathVariable("id") Long id){
        roleService.deleteRole(id);
    }
}
