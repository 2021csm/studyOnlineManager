package com.example.study.controller;

import com.example.study.bean.Subject;
import com.example.study.bean.Subject_info;
import com.example.study.bean.Type_by;
import com.example.study.mapper.SubjectInfoMapper;
import com.example.study.service.SubjectInfoService;
import com.example.study.service.TypeService;
import com.example.study.util.PageResult;
import com.example.study.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SubjectInfoControl {
    @Autowired
    private SubjectInfoService service;
    @Autowired
    private TypeService typeService;
    @PostMapping("/addSubjectInfo")
    public Result addSubjectInfo(@RequestBody Subject_info subjectInfo){
        service.addSubjectInfo(subjectInfo);
        return  new Result();
    }

    @PostMapping("/findAllValue")
    public String[] findAllValue(String name){
        String table=typeService.findValueByName(name);
       return typeService.findAllChosed(table);
    }
    @GetMapping("/testType")
    public String testType(String name){
        return typeService.findValueByName(name);

    }
    @PostMapping("/findAllType")
    public List<Type_by> findAllType(){
       return typeService.findAll();
    }
    @PostMapping("/SubjectInfoList")
    public PageResult<Subject_info> subjectInfoList(int page, int size, @RequestBody Map<String,Object> searchMap){
        return service.findPage(page,size,searchMap);
    }
    @PostMapping("/SubjectInfoUpdate")
    public Result subjectInfoUpdate(@RequestBody  Subject_info subjectInfo) {

        service.updateSubjectInfo(subjectInfo);
        return new Result();
    }
    @GetMapping("/subjectInfoSelectById")
    public Subject_info selectById(String id){

        return service.findById(id);
    }

    @GetMapping("/subjectInfoDeleteById")
    public Result deleteById(String id){

         service.deleteSubjectInfo(id);
        return new Result();
    }

}
