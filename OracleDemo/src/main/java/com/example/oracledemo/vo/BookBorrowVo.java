package com.example.oracledemo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.oracledemo.pojo.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oracle.sql.DATE;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookBorrowVo {
    private long id;
    private long bookId;
    private String bookName;
    private String categoryName;
    private Student student;
}
