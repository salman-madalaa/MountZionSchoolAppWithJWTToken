package com.zion.school.repo;

import com.zion.school.model.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Lenovo on 20-08-2021.
 */
@Repository
public interface StudentImageRepo extends JpaRepository<StudentImage,Integer> {
    Optional<StudentImage> findByName(String name);

    Optional<StudentImage> findById(long id);

    String deleteById(long id);

}
