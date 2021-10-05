package com.zion.school.repo;

import com.zion.school.model.SiblingInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Lenovo on 06-08-2021.
 */
@Repository
public interface SiblingInformationRepository extends JpaRepository<SiblingInformation,Integer> {
}
