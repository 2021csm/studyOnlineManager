package com.example.study.service;

import com.example.study.bean.Lab;
import com.example.study.bean.SysRole;
import com.example.study.bean.SysUser;
import com.example.study.mapper.SysRoleMapper;
import com.example.study.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class SysRoleService extends  ServiceTemplate<SysRole>{
    @Autowired
    private SysRoleMapper roleMapper;

    @PostConstruct
    public void init(){
        super.setMapper(this.roleMapper);
    }

    public PageResult<SysRole> findPage(int pageNum, int size, Map<String,Object> searchMap){
        return   super.findPage(pageNum,size,searchMap, Lab.class,"id","name");
    }


    public SysRole findById(int id) {
       return roleMapper.selectByPrimaryKey(id);
    }
}
