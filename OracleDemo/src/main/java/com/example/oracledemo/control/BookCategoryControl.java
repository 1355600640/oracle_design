package com.example.oracledemo.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oracledemo.common.ApiResponseObject;
import com.example.oracledemo.service.BookCategoryService;
import com.example.oracledemo.pojo.BookCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class BookCategoryControl {

    @Autowired
    BookCategoryService service;

    @GetMapping("/getCategorys")
    public ApiResponseObject<Map<String,Object>> getCategoryList(){
        Page<BookCategory> pageSession = service.getBookCategorys(1,999);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list",pageSession.getRecords());
        map.put("total",pageSession.getTotal());
        return ApiResponseObject.success(map);
    }

    @PostMapping("/updateCategory")
    public ApiResponseObject<String> updateBook(@RequestBody BookCategory bookInfo){
        boolean b = service.updateById(bookInfo);
        if(!b){
            return ApiResponseObject.error("更改数据失败");
        }
        return ApiResponseObject.success("更改数据成功");
    }

    @PostMapping("/insterCategory")
    public ApiResponseObject<String> insterBook(@RequestBody BookCategory bookInfo){
        bookInfo.setCreateTime(new Date());
        boolean b = service.save(bookInfo);
        if(!b){
            return ApiResponseObject.error("新增数据失败");
        }
        return ApiResponseObject.success("新增数据成功");
    }

    @PostMapping("/removeCategory")
    public ApiResponseObject<String> removeBook(@RequestBody List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(!b){
            return ApiResponseObject.error("删除数据失败");
        }
        return ApiResponseObject.success("删除数据成功");
    }
}
