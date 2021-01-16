package com.example.study.mapper;

import com.example.study.bean.Subject;
import com.example.study.bean.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface SysUserMapper extends Mapper<SysUser> {
    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);

}
