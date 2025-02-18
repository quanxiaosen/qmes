package com.example.qmex.controller;

import com.example.qmex.Entity.User;
import com.example.qmex.Repository.UserRepository;
import jdk.jfr.Description;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;  // 使用构造函数注入

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/test-db")
    public String testDbConnection() {
        if (userRepository.count() > 0) {
            return String.valueOf(userRepository.count());
        } else {
            return "数据库连接成功，但没有数据。";
        }
    }

    @GetMapping("/users")
    public List<User> GetAllUsers() {
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
