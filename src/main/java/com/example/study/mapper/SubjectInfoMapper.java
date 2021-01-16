package com.example.study.mapper;

import com.example.study.bean.Subject;
import com.example.study.bean.Subject_info;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SubjectInfoMapper extends Mapper<Subject_info> {
    @Select("SELECT subject_info_name,value_list FROM subject_info WHERE name = #{subject_id}")
    public List<Subject_info> findInfoList(String subject_id);
}
