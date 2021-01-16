package com.example.study.service;

import com.example.study.bean.Subject;
import com.example.study.bean.User;
import com.example.study.mapper.SubjectMapper;
import com.example.study.util.PageResult;
import com.example.study.util.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SubjectService {
    @Autowired
    private SubjectMapper subjectMapper;
    public List<Subject> showSubjects(){

        return  subjectMapper.selectAll();
    }

    public PageResult<Subject> findPage(int pageNum, int size,Map<String,Object> searchMap) {
        PageHelper.startPage(pageNum,size);
        Example example=new Example(Subject.class);
        Example.Criteria criteria= example.createCriteria();
        if(searchMap!=null){
            if(searchMap.get("id")!=null&& !"".equals(searchMap.get("id"))){
                criteria.andEqualTo("id",(String)searchMap.get("id"));
            }
            if(searchMap.get("subject_name")!=null&& !"".equals(searchMap.get("subject_name"))){
                criteria.andLike("subject_name","%"+(String)searchMap.get("subject_name")+"%");
            }
        }
        Page<Subject> page= (Page<Subject>)subjectMapper.selectByExample(example);
        return   new PageResult<Subject>(page.getTotal(),page.getResult());
    }
    public void updateSubject(Subject subject){
        subjectMapper.updateByPrimaryKeySelective(subject);
    }
    public Subject findById(String id){
        return  subjectMapper.selectByPrimaryKey(id);
    }

    public void addSubject(Subject subject){
       subjectMapper.insert(subject);
    }
}
