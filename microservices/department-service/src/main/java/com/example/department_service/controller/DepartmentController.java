package com.example.department_service.controller;

import com.example.department_service.model.Department;
import com.example.department_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departs")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private Environment environment;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(){
     return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id){
        System.out.println(environment.getProperty("local.server.port"));//to find which instance is responding
        return new ResponseEntity<>(departmentService.getDepartmentById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department){
        return new ResponseEntity<>(departmentService.AddDepartment(department),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department department){
        return new ResponseEntity<>(departmentService.updateDepartment(id,department), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartmentById(@PathVariable Integer id){
        departmentService.deleteDepartmentById(id);
    }
}
