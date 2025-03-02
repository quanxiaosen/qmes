package com.example.qmex.controller;

import com.example.qmex.Entity.Department;
import com.example.qmex.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // 添加部门
    @PostMapping("/add")
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        if (department.getCode() == null || department.getCode().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("部门编码不能为空");
        }
        Department savedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok(savedDepartment);
    }

    // 获取所有部门
    @GetMapping("/list")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseEntity.ok(departments);
    }
}
