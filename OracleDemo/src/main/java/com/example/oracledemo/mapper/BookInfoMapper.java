package com.example.oracledemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oracledemo.pojo.BookInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {
}
