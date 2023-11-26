package com.example.oracledemo.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oracledemo.mapper.BookCategoryMapper;
import com.example.oracledemo.service.BookCategoryService;
import com.example.oracledemo.pojo.BookCategory;
import org.springframework.stereotype.Service;

@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {
    @Override
    public Page<BookCategory> getBookCategorys(int pageSize, int num) {
        Page<BookCategory> page = new Page<>(pageSize,num);
        this.page(page);
        return page;
    }
}
