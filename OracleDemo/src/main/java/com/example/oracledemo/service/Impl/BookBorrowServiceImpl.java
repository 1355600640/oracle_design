package com.example.oracledemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oracledemo.mapper.BookBorrowMapper;
import com.example.oracledemo.pojo.BookBorrow;
import com.example.oracledemo.service.BookBorrowService;
import org.springframework.stereotype.Service;

@Service
public class BookBorrowServiceImpl extends ServiceImpl<BookBorrowMapper, BookBorrow> implements BookBorrowService {
}
