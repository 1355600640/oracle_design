package com.example.oracledemo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oracledemo.mapper.BookBorrowMapper;
import com.example.oracledemo.mapper.StudentMapper;
import com.example.oracledemo.pojo.BookBorrow;
import com.example.oracledemo.pojo.BookInfo;
import com.example.oracledemo.pojo.Student;
import com.example.oracledemo.service.BookInfoService;
import com.example.oracledemo.service.StudentService;
import com.example.oracledemo.vo.BookBorrowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    BookBorrowMapper bookBorrowMapper;

    @Autowired
    BookInfoService bookInfoService;

    @Override
    public Map<String,Object> getStudents(int current, int pageSize, String bookName, long studentId, String categoryName) {
        Page<Map<String,Object>> bookBorrowPage = new Page<>(current,pageSize);
        List<BookBorrowVo> bookBorrow = bookBorrowMapper.getBookBorrow(bookName, studentId, categoryName, bookBorrowPage).stream().map(item ->{
            LambdaQueryWrapper<BookBorrow> bookBorrowLambdaQueryWrapper = new LambdaQueryWrapper<>();
                bookBorrowLambdaQueryWrapper.eq(BookBorrow::getBookInfoId,item.getBookId());
            BookBorrow bookBorrow1 = bookBorrowMapper.selectOne(bookBorrowLambdaQueryWrapper);
            item.setId(bookBorrow1.getId());
            return item;
        }).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("list",bookBorrow);
        map.put("total",bookBorrowPage.getTotal());

        return map;
    }
}
