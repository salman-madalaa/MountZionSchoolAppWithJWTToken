package com.zion.school.service;

import com.zion.school.model.security.Role;
import com.zion.school.model.security.User;
import com.zion.school.model.security.UserRoles;
import com.zion.school.repo.security.RoleRepository;
import com.zion.school.repo.security.RoleRepository;
import com.zion.school.repo.security.UserRepository;
import com.zion.school.repo.security.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Lenovo on 20-09-2021.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    public Role createNewRole(Role role){
        return  roleRepository.save(role);
    }

    public List<Role> getAll(){
        List<Role> roles= roleRepository.findAll();

        for (Role role : roles){
            Set<User> users1 = new HashSet<>();
            List<UserRoles> userRoles = userRolesRepository.findByRoleId(role.getId());
            for(UserRoles userRole : userRoles) {
                Optional<User> user = userRepository.findById(userRole.getUserId());
                users1.add(user.get());
            }
            role.setUsers(users1);
        }
        return  roles;
    }

    public Role updateRole(Long id , Role role){
        return  roleRepository.save(role);
    }
    public void deleteRole(Long id){
        roleRepository.deleteById(id);
    }
}
