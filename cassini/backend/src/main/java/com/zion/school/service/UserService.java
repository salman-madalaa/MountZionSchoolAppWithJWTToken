package com.zion.school.service;

import com.zion.school.model.security.ERole;
import com.zion.school.model.security.Role;
import com.zion.school.model.security.User;
import com.zion.school.repo.security.RoleRepository;
import com.zion.school.repo.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lenovo on 20-09-2021.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(User user){
      return  userRepository.save(user);
    }

    public List<User> getAll(){
        return  userRepository.findAll();
    }

    public User updateUser(Long id , User user){
        return  userRepository.save(user);
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
