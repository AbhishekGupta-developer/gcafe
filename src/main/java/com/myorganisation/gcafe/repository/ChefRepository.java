package com.myorganisation.gcafe.repository;

import com.myorganisation.gcafe.enums.Cuisine;
import com.myorganisation.gcafe.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {


    //Experimental insertion (highly not recommended)
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO chef (name, experience, cuisine) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertData(String name, Double experience, String cuisine);

    default void insertData(String name, Double experience, Cuisine cuisine) {
        insertData(name, experience, cuisine.name());
    }
}
