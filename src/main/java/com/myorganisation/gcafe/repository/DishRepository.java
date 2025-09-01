package com.myorganisation.gcafe.repository;

import com.myorganisation.gcafe.enums.DishCategory;
import com.myorganisation.gcafe.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    //Custom finder method
    List<Dish> findByNameContaining(String name);

    List<Dish> findByNameContainingAndPriceBetween(String q, Double min, Double max);

    List<Dish> findByNameContainingAndPriceBetweenAndCategory(String q, Double min, Double max, DishCategory category);

    //Custom JPQL (Java Persistence Query Language)
    @Query("SELECT d FROM Dish d WHERE d.id = :identity")
    Dish getDishById(@Param("identity") Long id);
}
