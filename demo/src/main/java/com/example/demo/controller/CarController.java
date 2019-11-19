package com.example.demo.controller;

import com.example.demo.dao.CarDao;
import com.example.demo.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Car")
public class CarController {
    @Autowired
    CarDao carDao;
    @PostMapping("/addCar")
    public String addCar(@RequestBody Car cars){
        carDao.save(cars);
        return "cars added";
    }
    @GetMapping("/getCar")
    public List<Car> getCar(){
        return (List<Car>) carDao.findAll();
    }
}
