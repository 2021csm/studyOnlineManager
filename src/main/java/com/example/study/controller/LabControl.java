package com.example.study.controller;

import com.example.study.bean.Lab;
import com.example.study.service.ESService;
import com.example.study.service.LabService;
import com.example.study.service.MQSender;
import com.example.study.service.VerifyService;
import com.example.study.util.PageResult;
import com.example.study.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LabControl {
    @Autowired
    private LabService service;
    @Autowired
    private ESService esService;
    @Autowired
    private MQSender mqService;
    @Autowired
    private VerifyService verifyService;

    @GetMapping("/estest")
    public void test(@RequestParam Map<String,String> searchMap){
         //webutil转码
//        Map<String,String> search=new HashMap<>();
//        search.put("keywords","计算机");
//        search.put("category","免费");
//       return esService.search(search);
        esService.loadDatetoEs();
      //  ESService.testAdd();

    }
    @GetMapping("/mqtest")
    public void MQtest(){
      //  mqService.testTemplate();
       // mqService.testSendMessage();
        mqService.testSendMessageByExchange();
    }
    @GetMapping("/vftest")
    public void Vftest(){
        verifyService.phoneCode("17745153684");
    }
    @PostMapping("/addLab")
    public Result addLab(@RequestBody Lab lab){

        service.add(lab);
        return  new Result();
    }

    @PostMapping("/LabList")
    public PageResult<Lab> labList(int page, int size, @RequestBody Map<String,Object> searchMap){
        return service.findPage(page,size,searchMap);
    }
    @PostMapping("/labUpdate")
    public Result labUpdate(@RequestBody  Lab lab) {

        service.update(lab);
        return new Result();
    }
    @GetMapping("/labSelectById")
    public Lab selectById(String id){

        return service.findById(id);
    }

    @GetMapping("/labDeleteById")
    public Result deleteById(String id){

        service.delete(id);
        return new Result();
    }

}
