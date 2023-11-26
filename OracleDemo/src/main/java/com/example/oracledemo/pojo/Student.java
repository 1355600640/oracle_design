package com.example.oracledemo.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@KeySequence(value="CATEGORY_ID",dbType = DbType.ORACLE)
public class Student {
    @TableId(type = IdType.INPUT)
    private long bookCard;;
    private String username;
    private String classname;
    private long studentId;
    private String sex;
    private Date createTime;
}
