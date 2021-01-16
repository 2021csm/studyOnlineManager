package com.example.study.controller;

import com.example.study.bean.Subject_info;
import com.example.study.bean.Video;
import com.example.study.service.VideoService;
import com.example.study.util.PageResult;
import com.example.study.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VideoControl {
    @Autowired
   private  VideoService service;
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody Video video){

        service.addVideo(video);
        return  new Result();
    }

    @PostMapping("/videoList")
    public PageResult<Video> VideoList(int page, int size, @RequestBody Map<String,Object> searchMap){
        return service.findPage(page,size,searchMap);
    }
    @PostMapping("/videoUpdate")
    public Result videoUpdate(@RequestBody  Video video) {

        service.updateVideo(video);
        return new Result();
    }
    @GetMapping("/videoSelectById")
    public Video selectById(String id){

        return service.findById(id);
    }

    @GetMapping("/videoDeleteById")
    public Result deleteById(String id){

        service.deleteVideo(id);
        return new Result();
    }

}
