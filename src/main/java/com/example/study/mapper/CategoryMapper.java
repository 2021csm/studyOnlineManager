package com.example.study.mapper;

import com.example.study.bean.Category;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface CategoryMapper extends Mapper<Category> {
}
