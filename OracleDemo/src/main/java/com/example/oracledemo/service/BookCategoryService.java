package com.example.oracledemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.oracledemo.pojo.BookCategory;

public interface BookCategoryService extends IService<BookCategory> {

    Page<BookCategory> getBookCategorys(int pageSize,int num);
}
