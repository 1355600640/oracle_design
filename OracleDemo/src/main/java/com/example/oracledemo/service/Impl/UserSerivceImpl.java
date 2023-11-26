package com.example.oracledemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oracledemo.mapper.UsersMapper;
import com.example.oracledemo.service.UserService;
import com.example.oracledemo.pojo.Users;
import org.springframework.stereotype.Service;


@Service
public class UserSerivceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {
}
