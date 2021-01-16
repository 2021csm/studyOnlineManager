package com.example.study.controller;

import com.example.study.bean.User;
import com.example.study.service.UserService;
import com.example.study.util.PageResult;
import com.example.study.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserControl {
    @Autowired
    private UserService userService;
    @GetMapping("/showUsers")
    public List<User> showUsers(){

        List<User> users=userService.showUsers();

        return users;
    }
    @PostMapping("/findList")
    public List<User> findList(){

        Map<String,Object> m=new HashMap<>();
        m.put("name","csm");
        m.put("email","2793219495@qq.com");

        List<User> users=userService.findList(m);
        return users;
    }
    //@RequestBody Map<String,Object> searchMap
    @PostMapping("/findPageC")
    public PageResult <User> findPage(int page,int size,@RequestBody Map<String,Object> searchMap){

//        Map<String,Object> searchMap=new HashMap<>();
//
//        searchMap.put("email","2793219495@qq.com");

           return userService.findPage(searchMap,page,size);

    }

    @GetMapping("/findPage")
    public PageResult<User> findAll(int page,int size){


        PageResult<User> pageResult=userService.showUsers(page,size);
        return pageResult;
    }
    @GetMapping("/userSelect")
    public User selectUsers(String name){
        System.out.println("select a use");
        return userService.findByName(name);
    }
    @GetMapping("/userAdd")
    public String addUser(User user) {
       userService.addUser(user);
        return "users";
    }
    @PostMapping("/add")
    public Result add(@RequestBody User user) {

        userService.add(user);
        return new Result();
    }
    @PostMapping("/userUpdate")
    public  Result updateUser(@RequestBody  User user) {
        userService.updateUser(user);
        return new Result();
    }
    @GetMapping("/userDelete")
    public Result deleteUser(String name) {
        userService.deleteUser(name);
        return new Result();
    }
}
