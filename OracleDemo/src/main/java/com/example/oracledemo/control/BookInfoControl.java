package com.example.oracledemo.control;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oracledemo.common.ApiResponseObject;
import com.example.oracledemo.pojo.Student;
import com.example.oracledemo.service.BookInfoService;
import com.example.oracledemo.pojo.BookInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookInfoControl {

    @Autowired
    BookInfoService bookInfoService;


    @GetMapping("/getBooks")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",defaultValue = "1"),
            @ApiImplicitParam(name="num",value = "数量",defaultValue = "10"),
            @ApiImplicitParam(name="sort",value = "0 降序，1 升序",defaultValue = "0")
    })
    public ApiResponseObject<Map<String,Object>> getBooks(int page,int num,String name,int sort,int sortType){
        Page<BookInfo> pageSession = bookInfoService.getBooks(page, num, name,sort,sortType);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("books",pageSession.getRecords());
        map.put("total",pageSession.getTotal());
        return ApiResponseObject.success(map);
    }

    @PostMapping("/updateBook")
    public ApiResponseObject<String> updateBook(@RequestBody  BookInfo bookInfo){
        bookInfo.setUpdateTime(new Date());
        boolean b = bookInfoService.updateById(bookInfo);
        if(!b){
            return ApiResponseObject.error("更改数据失败");
        }
        return ApiResponseObject.success("更改数据成功");
    }

    @PostMapping("/insterBook")
    public ApiResponseObject<String> insterBook(@RequestBody BookInfo bookInfo){
        bookInfo.setCreateTime(new Date());
        boolean b = bookInfoService.save(bookInfo);
        if(!b){
            return ApiResponseObject.error("新增数据失败");
        }
        return ApiResponseObject.success("新增数据成功");
    }

    @PostMapping("/removeBooks")
    public ApiResponseObject<String> removeBook(@RequestBody List<Integer> ids){
        boolean b = bookInfoService.removeByIds(ids);
        if(!b){
            return ApiResponseObject.error("删除数据失败");
        }
        return ApiResponseObject.success("删除数据成功");
    }

    @GetMapping("/getBookInfos")
    public ApiResponseObject<List<BookInfo>> getBookInfos(String bookName){
        Page<BookInfo> page = new Page<>(1,10);
        LambdaQueryWrapper<BookInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(BookInfo::getBookname,bookName)
                        .eq(BookInfo::getBorrow,0);
        bookInfoService.page(page,queryWrapper);
        return ApiResponseObject.success(page.getRecords());
    }
}
