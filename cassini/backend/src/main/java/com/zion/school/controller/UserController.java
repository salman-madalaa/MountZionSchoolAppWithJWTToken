package com.zion.school.controller;

import com.zion.school.core.BaseController;
import com.zion.school.model.security.User;
import com.zion.school.service.UserService;
import jakarta.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lenovo on 20-09-2021.
 */
@RestController
@RequestMapping(value = "user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String databaseType;

    @PostConstruct
    public void initRoleAndUsers(){
        if(databaseType.equals("create")){
            userService.initRoleAndUser();
        }
    }

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public User createUser(@RequestBody User user){
        return  userService.createNewUser(user);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<User> getAll(){
        return  userService.getAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public User updateUser(@PathVariable("id") Long id,@RequestBody User user) {
        return  userService.updateUser(id, user);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void updateUser(@PathVariable("id") Long id){
       userService.deleteUser(id);
    }

}
