package com.example.qmex.controller;

import com.example.qmex.Entity.User;
import com.example.qmex.Repository.UserRepository;
import com.example.qmex.Service.UserService;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;  // 使用构造函数注入
    @Autowired
    private  UserService userService;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Page<User> getUsers(@RequestParam(defaultValue = "0")int page,
                               @RequestParam(defaultValue = "5")int size,
                               @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size);

        if (search != null && !search.trim().isEmpty()) {
            return userRepository.findByNameContaining(search, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    @GetMapping("/login")
    public List<User> getUser(){
        return userRepository.findAll();
    }

    @PostMapping("/add")
    @Description("用户增加api")
    public String addUser(@RequestBody User newUser) {
        // 使用前端传递的 User 对象来保存数据
        userRepository.save(newUser);
        return "用户 " + newUser.getName() + " 插入成功";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "删除" + id + "成功";
        } else {
            return id + "不存在";
        }
    }
}
