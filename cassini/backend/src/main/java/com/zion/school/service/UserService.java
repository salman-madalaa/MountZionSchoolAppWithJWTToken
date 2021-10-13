package com.zion.school.service;

import com.zion.school.exceptions.SchoolException;
import com.zion.school.helper.EmailHelper;
import com.zion.school.model.security.ERole;
import com.zion.school.model.security.Role;
import com.zion.school.model.security.User;
import com.zion.school.model.security.response.MessageResponse;
import com.zion.school.repo.security.RoleRepository;
import com.zion.school.repo.security.UserRepository;
import jakarta.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Lenovo on 20-09-2021.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailHelper emailHelper;

    @Autowired
    PasswordEncoder encoder;

//    @Autowired
//    PasswordDecoder decoder;

    String result;

    public User createNewUser(User user){
        User existFirstNameAndLastName = userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        if(existFirstNameAndLastName != null){
            result = MessageFormat.format("FirstName " + existFirstNameAndLastName.getFirstName() +"And LastName :" + existFirstNameAndLastName.getLastName() + " is Already exist.",null);
            throw new SchoolException(result);
        }
        Optional<User> existUserName= userRepository.findByUsername(user.getUsername());
        if(existUserName.isPresent()){
            result = MessageFormat.format("UserName : " + existUserName.get().getUsername() + " is Already exist.",null);
            throw new SchoolException(result);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            result = MessageFormat.format("Email : " + existUserName.get().getEmail() + " is Already exist.",null);
            throw new SchoolException(result);
        }

        emailHelper.sendUserNameAndPasswordMail(user);
        user.setPassword(encoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }

    public List<User> getAll(){
        return  userRepository.findAll();
    }

    public User updateUser(Long id , User user) {

        User existFirstNameAndLastName = userRepository.findByFirstNameAndLastName(user.getFirstName(), user.getLastName());

        if(existFirstNameAndLastName != null && existFirstNameAndLastName.getId() != user.getId()){
            result = MessageFormat.format("FirstName " + existFirstNameAndLastName.getFirstName() +"And LastName :" + existFirstNameAndLastName.getLastName() + " is Already exist.",null);
            throw new SchoolException(result);
        }else {
            Optional<User> existUserName= userRepository.findByUsername(user.getUsername());
            if(existUserName.isPresent() && existUserName.get().getId() != user.getId()){
                result = MessageFormat.format("UserName : " + existUserName.get().getUsername() + " is Already exist.",null);
                throw new SchoolException(result);
            }else{
                Optional<User> user1= userRepository.findById(user.getId());
                if(!user1.get().getUsername().equals(user.getUsername()) || !user1.get().getPassword().equals(user.getPassword())){
                    emailHelper.sendUserNameAndPasswordMail(user);
                }
                user.setPassword(encoder.encode(user.getPassword()));
                User user2= userRepository.save(user);
                return  user2;
            }

        }



    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void initRoleAndUser(){

        Role adminRole = new Role();
        adminRole.setId(1);
        adminRole.setName(ERole.ROLE_ADMIN);
        adminRole.setDescription("Manages all aspects of School");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setId(2);
        userRole.setName(ERole.ROLE_USER);
        userRole.setDescription("Manages User aspects");
        roleRepository.save(userRole);

        User adminUser= new User();
        adminUser.setId((long) 1);
        adminUser.setFirstName("Administrator");
        adminUser.setUsername("MZS2015");
        adminUser.setPassword(passwordEncoder.encode("mzst@15"));
        adminUser.setEmail("mzst@gmail.com");
        Set adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);

        userRepository.save(adminUser);
    }
}
