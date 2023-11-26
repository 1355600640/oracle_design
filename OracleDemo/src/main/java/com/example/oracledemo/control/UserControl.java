package com.example.oracledemo.control;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.oracledemo.common.ApiResponseObject;
import com.example.oracledemo.common.HttpException;
import com.example.oracledemo.service.UserService;
import com.example.oracledemo.utils.JwtUtil;
import com.example.oracledemo.pojo.Users;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserControl {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponseObject<Object> login(@RequestBody Users users){
        if(StringUtils.isEmpty(users.getUsername())||StringUtils.isEmpty(users.getPassword())){
            return ApiResponseObject.error("格式错误");
        }
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername,users.getUsername());
        Users one = userService.getOne(wrapper);
        Map<String,Object> map = new HashMap<>();
        if(users.getPassword().equals(one.getPassword())){
            String token = JwtUtil.createJWT(String.valueOf(one.getId()));
            map.put("token",token);
            return ApiResponseObject.success(map);
        }
        return ApiResponseObject.error("账号或密码错误");
    }

    @GetMapping("/getUser")
    public ApiResponseObject<Object> getUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String id;
        if (token!= null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }else {
            throw new HttpException("token异常");
        }
        try{
            Claims claims = JwtUtil.parseJWT(token);
            id = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponseObject(405,"token非法");
        }
        Users user = userService.getById(id);
        return ApiResponseObject.success(user);
    }

    @GetMapping("test")
    public String test(){
        return "helloword";
    }
}
