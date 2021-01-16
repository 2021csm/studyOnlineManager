package com.example.study.service;

import com.example.study.bean.Lab;
import com.example.study.bean.SysRole;
import com.example.study.bean.SysUserRole;
import com.example.study.bean.User;
import com.example.study.mapper.SysUserRoleMapper;
import com.example.study.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class SysUserRoleService extends  ServiceTemplate<SysUserRole> {
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @PostConstruct
    public void init(){
        super.setMapper(this.userRoleMapper);
    }

    public PageResult<SysUserRole> findPage(int pageNum, int size, Map<String,Object> searchMap){
        return   super.findPage(pageNum,size,searchMap, Lab.class,"user_id");
    }

    public List<SysUserRole> findList(Map<String,Object> searchMap){
        Example example=new Example(SysUserRole.class);
        Example.Criteria criteria= example.createCriteria();
        if(searchMap!=null){
            if(searchMap.get("user_id")!=null&& !"".equals(searchMap.get("user_id"))){
                criteria.andEqualTo("userId",searchMap.get("user_id"));
            }
//            if(searchMap.get("email")!=null&& !"".equals(searchMap.get("email"))){
//                criteria.andLike("email","%"+(String)searchMap.get("email")+"%");
//            }
        }
        return   userRoleMapper.selectByExample(example);

    }

}
