package com.example.oracledemo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.oracledemo.mapper.BookInfoMapper;
import com.example.oracledemo.service.BookInfoService;
import com.example.oracledemo.pojo.BookInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {

    @Override
    public Page<BookInfo> getBooks(int pageSize, int num, String name,int sort,int sortType) {
        LambdaQueryWrapper<BookInfo> bookInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(sort==0) bookInfoLambdaQueryWrapper.orderByDesc(BookInfo::getUpdateTime);
        else bookInfoLambdaQueryWrapper.orderByAsc(BookInfo::getUpdateTime);
        Page<BookInfo> page = new Page<>(pageSize,num);
        if(!StringUtils.isEmpty(name)){
            bookInfoLambdaQueryWrapper.like(sortType==0||sortType==2,BookInfo::getBookname,name)
                    .or(sortType == 0)
                    .like(sortType==0||sortType==1,BookInfo::getCategoryName,name);
        }
        this.page(page, bookInfoLambdaQueryWrapper);
        return page;
    }
}
