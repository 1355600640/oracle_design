package com.example.oracledemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.oracledemo.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends BaseMapper<Users> {
}
