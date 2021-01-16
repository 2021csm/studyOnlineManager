package com.example.study.service;

import com.example.study.bean.Category;
import com.example.study.mapper.CategoryMapper;
import com.example.study.util.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

public  class ServiceTemplate<T> {
     private Mapper<T> mapper;

    public void setMapper(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    /**
     * 初始化模板mapper
     */

    public PageResult<T> findPage(int pageNum, int size, Map<String,Object> searchMap, Class cla, String ...arg) {
       //设置分页
        PageHelper.startPage(pageNum,size);
        //创建example
        Example example=new Example(cla);
         Example.Criteria criteria= example.createCriteria();
         int i=1;
         if(searchMap!=null){
             if(searchMap.get(arg[0])!=null&& !"".equals(searchMap.get(arg[0]))){
                 criteria.andEqualTo(arg[0],(String)searchMap.get(arg[0]));
             }
             while(i<arg.length) {
                 if (searchMap.get(arg[i]) != null && !"".equals(searchMap.get(arg[i]))) {
                     criteria.andLike(arg[i], "%" + (String) searchMap.get(arg[i]) + "%");
                 }
                 i++;
             }
         }
         //得到分页结果
        Page<T> page= (Page<T>)mapper.selectByExample(example);
        return   new PageResult<T>(page.getTotal(),page.getResult());
    }


    public void update(T o){
        mapper.updateByPrimaryKeySelective(o);
    }
    public T findById(String id){
        return  mapper.selectByPrimaryKey(id);
    }

    public void add(T o){
        mapper.insert(o);

    }
    public  void delete(String id){
        mapper.deleteByPrimaryKey(id);
    }

}
