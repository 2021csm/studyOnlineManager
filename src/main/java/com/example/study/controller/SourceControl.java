package com.example.study.controller;
import com.example.study.bean.Source;
import com.example.study.service.OSSService;
import com.example.study.service.SourceService;
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
import java.util.Map;
@RestController
public class SourceControl {


    @Autowired
    private SourceService service;
    @Autowired
    private OSSService ossService;
    @PostMapping("/addSource")
    public Result addSource(@RequestBody Source source){

        service.add(source);
        return  new Result();
    }

    @PostMapping("/sourceList")
    public PageResult<Source> ourceList(int page, int size, @RequestBody Map<String,Object> searchMap){
        return service.findPage(page,size,searchMap);
    }
    @PostMapping("/sourceUpdate")
    public Result labUpdate(@RequestBody  Source source) {

        service.update(source);
        return new Result();
    }
    @GetMapping("/sourceSelectById")
    public Source selectById(String id){

        return service.findById(id);
    }

    @GetMapping("/sourceDeleteById")
    public Result deleteById(String id){

        service.delete(id);
        return new Result();
    }
    @PostMapping("/ImageOSSAliBaBaSource")
    public String uploadImageOnAliBaBa(MultipartFile file){
        String imageUrl=" ";
        try {

            imageUrl=  ossService.uploadImageOnAliBaBa(file.getOriginalFilename(),file.getInputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    @PostMapping("/uploadImageOfSource")
    public String uploadImageNative(MultipartFile file, HttpServletRequest request)  {

        String path=request.getSession().getServletContext().getRealPath("images");
        String filePath=path+"/"+file.getOriginalFilename();
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
