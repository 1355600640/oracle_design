package com.example.oracledemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.oracledemo.pojo.Student;
import com.example.oracledemo.vo.BookBorrowVo;

import java.util.List;
import java.util.Map;

public interface StudentService extends IService<Student> {

    Map<String,Object> getStudents(int current, int pageSize, String bookName, long studentId, String categoryName);
}
