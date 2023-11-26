package com.example.oracledemo.control;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.oracledemo.mapper.StudentMapper;
import com.example.oracledemo.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class demoControl {

    @Autowired
    StudentMapper mapper;

    @GetMapping("/test")
    public Object test(){
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        List<Student> students = mapper.selectList(wrapper);
        return students;
    }
}
