package com.example.oracledemo.control;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oracledemo.common.ApiResponseObject;
import com.example.oracledemo.mapper.BookBorrowMapper;
import com.example.oracledemo.pojo.BookBorrow;
import com.example.oracledemo.pojo.BookInfo;
import com.example.oracledemo.pojo.Student;
import com.example.oracledemo.service.BookBorrowService;
import com.example.oracledemo.service.BookInfoService;
import com.example.oracledemo.service.StudentService;
import com.example.oracledemo.vo.BookBorrowVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentControl {

    @Autowired
    StudentService service;

    @Autowired
    BookBorrowService bookBorrowService;

    @Autowired
    StudentService studentService;

    @Autowired
    BookInfoService bookInfoService;


    @GetMapping("/getStudentBorrows")
    @ApiImplicitParams({
            @ApiImplicitParam(name="current",value = "页码", paramType = "query",dataType = "Integer",defaultValue = "1"),
            @ApiImplicitParam(name="pageSize",value = "数量", paramType = "query",dataType = "Integer",defaultValue = "10"),
    })
    public ApiResponseObject<Map<String,Object>> getStudentBorrows(@RequestParam(defaultValue = "1")int current, @RequestParam(defaultValue = "10")int pageSize, String bookName,@RequestParam(defaultValue = "0") long studentId, String categoryName) {
        Map<String, Object> borrows = service.getStudents(current, pageSize, bookName, studentId, categoryName);
        return ApiResponseObject.success(borrows);
    }

    @PostMapping("/removeByIds")
    public ApiResponseObject<String> remove(@RequestBody List<Long> ids){
        bookBorrowService.listByIds(ids).forEach(item ->{
            bookInfoService.updateById(BookInfo.builder().id((int) item.getBookInfoId())
                    .borrow(0).build());
        });
        boolean b = bookBorrowService.removeByIds(ids);

        return ApiResponseObject.success("删除成功");
    }

    @PostMapping("/insterOrUpdate")
    public ApiResponseObject<String> remove(@RequestBody BookBorrow bookBorrow){
        bookBorrow.setCreateTime(new Date());
        bookBorrowService.saveOrUpdate(bookBorrow);
        BookInfo bookInfoServiceById = bookInfoService.getById(bookBorrow.getBookInfoId());
        bookInfoServiceById.setBorrow(1);
        bookInfoService.updateById(bookInfoServiceById);
        return ApiResponseObject.success((bookBorrow.getBookInfoId()!=0?"修改":"新增")+"成功");
    }

    @GetMapping("/getStudents")
    public ApiResponseObject<Map<String,Object>> getStudent(String studentId,@RequestParam(defaultValue = "1")int current, @RequestParam(defaultValue = "10")int pageSize){
        Page<Student> page = new Page<>(current,pageSize);
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(studentId))
            queryWrapper.like(Student::getStudentId,studentId);
        studentService.page(page,queryWrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("list",page.getRecords());
        map.put("total",page.getTotal());
       return ApiResponseObject.success(map);
    }

    @PostMapping("/insterOrUpdateStudent")
    public ApiResponseObject<String> insterOrUpdateStudent(@RequestBody Student student){
        student.setCreateTime(new Date());
        studentService.saveOrUpdate(student);
        return ApiResponseObject.success(student.getBookCard()!=0?"修改":"新增"+"成功");
    }

    @PostMapping("/removeStudent")
    public ApiResponseObject<String> insterOrUpdateStudent(@RequestBody List<Long> ids){
        LambdaQueryWrapper<BookBorrow> bookBorrowLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bookBorrowLambdaQueryWrapper.in(BookBorrow::getBookCard,ids);
        bookBorrowService.list(bookBorrowLambdaQueryWrapper).forEach((item ->{
            bookInfoService.updateById(BookInfo.builder().id((int) item.getBookInfoId())
                    .borrow(0).build());
        }));
        bookBorrowService.remove(bookBorrowLambdaQueryWrapper);
        studentService.removeByIds(ids);
        return ApiResponseObject.success("删除成功");
    }


}
