package com.example.study.service;

import com.example.study.bean.Lab;
import com.example.study.bean.Type_by;
import com.example.study.mapper.Type_byMapper;
import com.example.study.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class TypeService  extends ServiceTemplate<Type_by>{
    @Autowired
    private Type_byMapper mapper;
    @PostConstruct
    public void init(){
        super.setMapper(this.mapper);
    }

    public String findValueByName(String name){
       return mapper.findValueByName(name);
    }
    public String[] findAllChosed(String table){

       return mapper.findAllChosed(table);
    }
    public List<Type_by> findAll(){
        return mapper.selectAll();
    }

}
