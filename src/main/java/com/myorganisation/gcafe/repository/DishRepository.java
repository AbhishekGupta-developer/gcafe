package com.myorganisation.gcafe.repository;

import com.myorganisation.gcafe.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    //Custom finder method
    List<Dish> findByNameContaining(String name);

    List<Dish> findByNameContainingAndPriceBetween(String q, Double min, Double max);
}
