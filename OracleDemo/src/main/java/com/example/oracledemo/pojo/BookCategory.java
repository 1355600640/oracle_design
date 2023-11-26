package com.example.oracledemo.pojo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@KeySequence(value="CATEGORY_ID",dbType = DbType.ORACLE)
public class BookCategory {
    @TableId(type = IdType.INPUT)
    private  int id;
    private String name;
    private int sort;
    private Date createTime;
}
