package com.cg.controller;

import com.cg.pojo.dto.UserDto;
import com.cg.pojo.vo.LoginVo;
import com.cg.pojo.vo.PasswordVo;
import com.cg.result.Result;
import com.cg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("register")
    public Result saveUser(@RequestBody LoginVo loginVo) {
        return userService.saveUser(loginVo);
    }

    @PostMapping("login")
    public Result login(String username, String password) {
        return userService.login(username, password);
    }

    @GetMapping
    public Result getUser() {
        return userService.getUser();
    }
    @PostMapping
    public Result updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }
    @GetMapping("list")
    public Result listUser(Integer pageNum, Integer pageSize, String nickname) {
        return userService.listUser(pageNum, pageSize, nickname);
    }

    /**
     * 禁用用户和启用用户时调用
     * @param id 用户ID
     * @param status 对应的状态字段
     * @return
     */

    @PutMapping("{id}")
    public Result forbidUser(@PathVariable Long id, Integer status) {
        return userService.forbidUser(id, status);
    }

    @DeleteMapping("{id}")
    public Result deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("pwd")
    public Result updatePassword(@RequestBody PasswordVo passwordVo) {
        return userService.updatePassword(passwordVo);
    }
}
