package com.zion.school.repo.security;

import com.zion.school.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Optional<User> findById(Long id);
//	List<User> findByRoles(Integer id);

//	Optional<User> findByFirstNameAndLastName(String firstName,String lastName);

	User findByFirstNameAndLastName(String firstName,String lastName);
}
