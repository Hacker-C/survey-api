package com.cg.controller;

import com.cg.pojo.User;
import com.cg.result.Result;
import com.cg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getUserList() {
        return userService.list();
    }

    @PostMapping("register")
    public Result saveUser(String username, String password) {
        return userService.saveUser(username, password);
    }

    @PostMapping("login")
    public Result login(String username, String password) {
        return userService.login(username, password);
    }
}
