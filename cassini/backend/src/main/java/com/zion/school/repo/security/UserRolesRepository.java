package com.zion.school.repo.security;

import com.zion.school.model.security.User;
import com.zion.school.model.security.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lenovo on 04-10-2021.
 */
@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Long>{

    List<UserRoles> findByRoleId(Integer roleId);
}
