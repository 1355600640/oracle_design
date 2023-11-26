package com.example.oracledemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oracledemo.pojo.BookCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookCategoryMapper extends BaseMapper<BookCategory> {
}
