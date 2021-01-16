package com.example.study.controller;

import com.example.study.bean.Subject;
import com.example.study.service.ESService;
import com.example.study.service.OSSService;
import com.example.study.service.SubjectService;
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
public class subjectControl {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private OSSService oosService;

    @PostMapping("/addSubject")
    public  Result addSubject(@RequestBody Subject subject){
        System.out.println(subject);
        subjectService.addSubject(subject);
        return  new Result();
    }


    @GetMapping("/showSubjectAll")
public List<Subject> showSubjectAll(){
    return subjectService.showSubjects();
}
    @PostMapping("/subjectList")
    public PageResult<Subject> subjectList(int page, int size,@RequestBody Map<String,Object> searchMap){
        return subjectService.findPage(page,size,searchMap);
    }
    @PostMapping("/subjectUpdate")
    public Result subjectUpdate(@RequestBody  Subject subject) {

        subjectService.updateSubject(subject);
        return new Result();
    }
    @GetMapping("/subjectSelectById")
    public Subject selectById(String id){

        return subjectService.findById(id);
    }
    @PostMapping("/ImageOSSAliBaBa")
    public String uploadImageOnAliBaBa(MultipartFile file){
        String imageUrl=" ";
        try {

            imageUrl=  oosService.uploadImageOnAliBaBa(file.getOriginalFilename(),file.getInputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    @PostMapping("/uploadImageOfSubject")
    public String uploadImageNative(MultipartFile file, HttpServletRequest request)  {

        String path=request.getSession().getServletContext().getRealPath("images");
        String filePath=path+"/"+file.getOriginalFilename();
        System.out.println(filePath);
        File doFile=new File(filePath);
        if (!doFile.getParentFile().exists()){
            doFile.mkdirs();
       }
        try {
            file.transferTo(doFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
      return  "http://localhost:8082/"+"images/"+file.getOriginalFilename();
    }
}
