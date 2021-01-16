package com.example.study.service;
import com.example.study.bean.Subject_info;
import com.example.study.mapper.SubjectInfoMapper;
import com.example.study.util.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
@Service
public class SubjectInfoService {
    @Autowired
    private SubjectInfoMapper mapper;

    public PageResult<Subject_info> findPage(int pageNum, int size, Map<String,Object> searchMap) {
        PageHelper.startPage(pageNum,size);
        Example example=new Example(Subject_info.class);
        Example.Criteria criteria= example.createCriteria();
        if(searchMap!=null){
            if(searchMap.get("subject_info_id")!=null&& !"".equals(searchMap.get("subject_info_id"))){
                criteria.andEqualTo("subject_info_id",(String)searchMap.get("subject_info_id"));
            }
            if(searchMap.get("subject_id")!=null&& !"".equals(searchMap.get("subject_id"))){
                criteria.andEqualTo("subject_id",(String)searchMap.get("subject_id"));
            }
            if(searchMap.get("subject_info_name")!=null&& !"".equals(searchMap.get("subject_info_name"))){
                criteria.andLike("subject_info_name","%"+(String)searchMap.get("subject_info_name")+"%");
            }
        }
        Page<Subject_info> page= (Page<Subject_info>)mapper.selectByExample(example);
        return   new PageResult<Subject_info>(page.getTotal(),page.getResult());
    }
    public void updateSubjectInfo(Subject_info subject){
        mapper.updateByPrimaryKeySelective(subject);
    }
    public Subject_info findById(String id){
        return  mapper.selectByPrimaryKey(id);
    }
    //需要修改，和测试
    public List<Subject_info> findInfoList(String subject_id){
      return   mapper.findInfoList(subject_id);
    }

    public void addSubjectInfo(Subject_info subjectInfo){
        System.out.println("service");
        System.out.println(subjectInfo);
        mapper.insert(subjectInfo);

    }
    public  void deleteSubjectInfo(String id){
        mapper.deleteByPrimaryKey(id);
    }
}
