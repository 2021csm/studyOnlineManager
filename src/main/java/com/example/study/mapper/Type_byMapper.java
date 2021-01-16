package com.example.study.mapper;


import com.example.study.bean.Type_by;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface Type_byMapper  extends Mapper<Type_by> {
    @Select("SELECT value FROM type_by WHERE name = #{name}")
    public String findValueByName(String name);
    @Select("SELECT name FROM ${table}")
    public String[] findAllChosed(@Param("table") String table);

}
