package com.example.study.controller;

import com.example.study.bean.Category;
import com.example.study.bean.Lab;
import com.example.study.service.CategoryService;
import com.example.study.service.LabService;
import com.example.study.util.PageResult;
import com.example.study.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CategoryControl {
    @Autowired
    private CategoryService service;
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody Category cgy){

        service.addCategory(cgy);
        return  new Result();
    }

    @PostMapping("/categoryList")
    public PageResult<Category> labList(int page, int size, @RequestBody Map<String,Object> searchMap){
        return service.findPage(page,size,searchMap);
    }
    @PostMapping("/categoryUpdate")
    public Result videoUpdate(@RequestBody Category cgy) {

        service.updateCategory(cgy);
        return new Result();
    }
    @GetMapping("/categorySelectById")
    public Category selectById(String id){

        return service.findById(id);
    }

    @GetMapping("/categoryDeleteById")
    public Result deleteById(String id){

        service.deleteCategory(id);
        return new Result();
    }

}
