package com.example.study.service;

import com.example.study.bean.Category;
import com.example.study.mapper.CategoryMapper;
import com.example.study.util.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper mapper;
    public PageResult<Category> findPage(int pageNum, int size, Map<String,Object> searchMap) {
        PageHelper.startPage(pageNum,size);
        Example example=new Example(Category.class);
        Example.Criteria criteria= example.createCriteria();
        if(searchMap!=null){
            if(searchMap.get("id")!=null&& !"".equals(searchMap.get("id"))){
                criteria.andEqualTo("id",(String)searchMap.get("id"));
            }
            if(searchMap.get("name")!=null&& !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+(String)searchMap.get("name")+"%");
            }
        }
        Page<Category> page= (Page<Category>)mapper.selectByExample(example);
        return   new PageResult<Category>(page.getTotal(),page.getResult());
    }
    public void updateCategory(Category category){
        mapper.updateByPrimaryKeySelective(category);
    }
    public Category findById(String id){
        return  mapper.selectByPrimaryKey(id);
    }

    public void addCategory(Category category){

        mapper.insert(category);

    }
    public  void deleteCategory(String id){
        mapper.deleteByPrimaryKey(id);
    }

}
