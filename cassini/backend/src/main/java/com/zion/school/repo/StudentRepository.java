package com.zion.school.repo;

import com.zion.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Lenovo on 01-03-2021.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByClassToJoin(String className);

  //------------------------------Rte Students--------------------------
    List<Student> findByRteStudentTrueAndClassToJoinOrderByRegistrationIdAsc(String className);

    List<Student> findByRteStudentTrueOrderByRegistrationIdAsc();

//------------------------------normal Students--------------------------
	List<Student> findByClassToJoinAndRteStudentFalseOrderByRegistrationIdAsc(String className);

	List<Student> findByRteStudentFalseOrderByRegistrationIdAsc();

    String deleteByRegistrationId(String registrationId);

    Optional<Student> findById(Long id);

    Optional<Student> findByRegistrationId(String registrationId);

    void deleteById(long id);

    List<Student> findAllByOrderByRegistrationIdAsc();
}


