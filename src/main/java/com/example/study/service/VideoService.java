package com.example.study.service;

import com.example.study.bean.Subject_info;
import com.example.study.bean.Video;
import com.example.study.mapper.VideoMapper;
import com.example.study.util.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

@Service
public class VideoService {
    @Autowired
    private VideoMapper mapper;
    public PageResult<Video> findPage(int pageNum, int size, Map<String,Object> searchMap) {
        PageHelper.startPage(pageNum,size);
        Example example=new Example(Video.class);
        Example.Criteria criteria= example.createCriteria();
        if(searchMap!=null){
            if(searchMap.get("subject_id")!=null&& !"".equals(searchMap.get("subject_id"))){
                criteria.andEqualTo("subject_id",(String)searchMap.get("subject_id"));
            }
            if(searchMap.get("video_id")!=null&& !"".equals(searchMap.get("video_id"))){
                criteria.andEqualTo("video_id",(String)searchMap.get("video_id"));
            }

            if(searchMap.get("video_name")!=null&& !"".equals(searchMap.get("video_name"))){
                criteria.andLike("video_name","%"+(String)searchMap.get("video_name")+"%");
            }
        }
        Page<Video> page= (Page<Video>)mapper.selectByExample(example);
        return   new PageResult<Video>(page.getTotal(),page.getResult());
    }
    public void updateVideo(Video video){
        mapper.updateByPrimaryKeySelective(video);
    }
    public Video findById(String id){
        return  mapper.selectByPrimaryKey(id);
    }

    public void addVideo(Video video){


        mapper.insert(video);

    }
    public  void deleteVideo(String id){
        mapper.deleteByPrimaryKey(id);
    }
}
