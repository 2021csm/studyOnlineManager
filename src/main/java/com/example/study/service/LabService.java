package com.example.study.service;

import com.example.study.bean.Category;
import com.example.study.bean.Lab;
import com.example.study.mapper.LabMapper;
import com.example.study.util.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class LabService extends ServiceTemplate<Lab> {
    @Autowired
    private LabMapper labMapper;

    @PostConstruct
   public void init(){
      super.setMapper(this.labMapper);
   }
    public PageResult<Lab> findPage(int pageNum, int size, Map<String,Object> searchMap){
      return   super.findPage(pageNum,size,searchMap,Lab.class,"id","name");
    }

}
