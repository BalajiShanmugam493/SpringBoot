package com.example.demo1.dao;

import com.example.demo1.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarDao extends CrudRepository<Car, Integer>{
    List<Car> findByType(String type);
}
