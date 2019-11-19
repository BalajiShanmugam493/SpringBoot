package com.example.demoredis.controller;

import com.example.demoredis.dao.ItemDao;
import com.example.demoredis.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
public class ItemController {
    @Autowired
    ItemDao itemRepo;

    @RequestMapping("/getAllItems")
    @ResponseBody
    public ResponseEntity<Map<Integer,Item>> getAllItems(){
        Map<Integer, Item> items =  itemRepo.getAllItems();
        return new ResponseEntity<Map<Integer,Item>>(items, HttpStatus.OK);
    }

    @GetMapping("/item/{itemId}")
    @Cacheable(value="users", key="#itemId")
    @ResponseBody
    public Item getItem(@PathVariable int itemId) throws InterruptedException {
        Thread.sleep(10000);
        Item item = itemRepo.getItem(itemId);
        return item;
    }

    @PostMapping(value = "/addItem",consumes = {"application/json"},produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Item> addItem(@RequestBody Item item,UriComponentsBuilder builder){
        itemRepo.addItem(item);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addItem/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<Item>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/updateItem")
    @ResponseBody
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        if(item != null){
            itemRepo.updateItem(item);
        }
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @CacheEvict(value="users", key="#id")
    @ResponseBody
    public ResponseEntity<Void> deleteItem(@PathVariable int id){
        itemRepo.deleteItem(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}