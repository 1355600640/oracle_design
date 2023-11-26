package com.example.oracledemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oracledemo.pojo.BookBorrow;
import com.example.oracledemo.vo.BookBorrowVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookBorrowMapper extends BaseMapper<BookBorrow> {

    @MapKey("id")
    List<BookBorrowVo> getBookBorrow(@Param("bookName") String bookName, @Param("studentId") long studentId,
                      @Param("categoryName") String categoryName, Page Page);
}
