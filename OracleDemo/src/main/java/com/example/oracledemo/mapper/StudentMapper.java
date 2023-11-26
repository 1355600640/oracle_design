package com.example.oracledemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oracledemo.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
