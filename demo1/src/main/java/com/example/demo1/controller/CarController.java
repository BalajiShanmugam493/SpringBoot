package com.example.demo1.controller;

import com.example.demo1.dao.CarDao;
import com.example.demo1.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Car")
public class CarController {
    @Autowired
    CarDao carDao;
    @PostMapping("/addCar")
    public String addCar(@RequestBody List<Car> cars){
        carDao.saveAll(cars);
        return "cars added";
    }
    @GetMapping("/getCar")
    public List<Car> getCar(){
        return (List<Car>) carDao.findAll();
    }
    @DeleteMapping("/deleteCar")
    public String deleteCar(@RequestBody Car car){
        carDao.deleteById(car.getId());
        return "car deleted";
    }
    @GetMapping("/updateCarByType")
    @ResponseBody
    public String updateCarByType(@RequestParam Integer id, @RequestParam String type){
        List<Car> cars = carDao.findByType(type);
        if(cars != null){
            for(Car car:cars){
                System.out.println(car.getId()+" "+id+" "+type);
                type = type + "cars";
                car.setType(type);
                carDao.save(car);
            }
        }
        return "updated successfully";
    }
}
