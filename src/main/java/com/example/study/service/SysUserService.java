package com.example.study.service;

import com.example.study.bean.Lab;
import com.example.study.bean.SysUser;
import com.example.study.mapper.SysUserMapper;
import com.example.study.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class SysUserService  extends ServiceTemplate<SysUser>{

    @Autowired
    private SysUserMapper userMapper;
    @PostConstruct
    public void init(){
        super.setMapper(this.userMapper);
    }

    public PageResult<SysUser> findPage(int pageNum, int size, Map<String,Object> searchMap){
        return   super.findPage(pageNum,size,searchMap,Lab.class,"id","name");
    }

    public SysUser selectByName(String username) {
        return userMapper.selectByName(username);
    }
}
