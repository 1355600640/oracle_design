package com.example.oracledemo.pojo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@KeySequence(value="SEQ_ID",dbType = DbType.ORACLE)
public class Users {
    @TableId(type = IdType.INPUT)
    private long id;
    private String username;
    private String password;
    private String email;
    private Date createdAt;
    private String pic;
    private String nickname;
}
