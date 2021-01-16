package com.example.study.service;

import com.example.study.bean.Source;
import com.example.study.mapper.SourceMapper;
import com.example.study.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
@Service
public class SourceService extends ServiceTemplate<Source> {
    @Autowired
    private SourceMapper mapper;

    @PostConstruct
    public void init(){
        super.setMapper(this.mapper);
    }

    public PageResult<Source> findPage(int pageNum, int size, Map<String, Object> searchMap) {
        return super.findPage(pageNum, size, searchMap, Source.class, "source_id","source_name");
    }
}
