package com.example.study.service;

import com.example.study.bean.User;
import com.example.study.dao.UserDao;
import com.example.study.mapper.UserMapper;
import com.example.study.util.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void addUser(User user){
        userMapper.insert(user);
    }
    public void updateUser(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }
    public void selectUser(User user){
        userMapper.selectOne(user);
    }
    public List<User> showUsers(){

       return  userMapper.selectAll();
    }
    public PageResult<User> showUsers(int pageNum,int size){
        PageHelper.startPage(pageNum,size);
        Page<User> page=(Page<User>)userMapper.selectAll();
        return new PageResult<User>(page.getTotal(),page.getResult());
    }
    public PageResult<User> findPage(Map<String,Object> searchMap,int pageNum,int size){
        PageHelper.startPage(pageNum,size);
        Example example=new Example(User.class);
        Example.Criteria criteria= example.createCriteria();
        if(searchMap!=null){
//            if(searchMap.get("name")!=null&& !"".equals(searchMap.get("name"))){
//                criteria.andEqualTo("name",(String)searchMap.get("name"));
//            }
            if(searchMap.get("email")!=null&& !"".equals(searchMap.get("email"))){
                criteria.andLike("email","%"+(String)searchMap.get("email")+"%");
            }
            if(searchMap.get("name")!=null&& !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+(String)searchMap.get("name")+"%");
            }
            if(searchMap.get("type")!=null&& !"".equals(searchMap.get("type"))){
                criteria.andEqualTo("type",(String)searchMap.get("type"));
            }
        }
       Page<User> page= (Page<User>)userMapper.selectByExample(example);
        return   new PageResult<User>(page.getTotal(),page.getResult());
    }
    public List<User> findList(Map<String,Object> searchMap){
        Example example=new Example(User.class);
        Example.Criteria criteria= example.createCriteria();
        if(searchMap!=null){
            if(searchMap.get("name")!=null&& !"".equals(searchMap.get("name"))){
                criteria.andEqualTo("name",(String)searchMap.get("name"));
            }
            if(searchMap.get("email")!=null&& !"".equals(searchMap.get("email"))){
                criteria.andLike("email","%"+(String)searchMap.get("email")+"%");
            }
        }
        return   userMapper.selectByExample(example);

    }
    public User findByName(String name){
        return  userMapper.selectByPrimaryKey(name);
    }
    public  void deleteUser(String name){
        userMapper.deleteByPrimaryKey(name);
    }
    public void add(User user){
        userMapper.insert(user);
    }
}
