package com.example.oracledemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.oracledemo.pojo.BookInfo;

public interface BookInfoService extends IService<BookInfo> {

    /**
     * 获取书本列表
     * @param pageSize 页码
     * @param num 数量
     * @param name 查找名字
     * @return
     */
    Page<BookInfo> getBooks(int pageSize, int num, String name,int sort,int sortType);
}
