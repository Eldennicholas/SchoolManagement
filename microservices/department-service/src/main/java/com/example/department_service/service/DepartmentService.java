package com.example.department_service.service;

import com.example.department_service.exception.DepartmentNotFoundException;
import com.example.department_service.model.Department;
import com.example.department_service.repository.DepartmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    // Spring will automatically inject the dependency via this constructor
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> dept = departmentRepository.findById(id);
        if(dept.isEmpty()){
            throw new DepartmentNotFoundException("Department not found with id "+id);
        }
        return dept.get();
        // NO EXCEPTION --> service -->controller -->client
        // EXCEPTION -->service x->controller coz controller shall not handle exception.
        // EXCEPTION -->service -->DepartmentNotFoundException -->RestExceptionHandler
    }

    public Department AddDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Integer id,Department department){
        Department dept = getDepartmentById(id);
        dept.setDepartmentName(department.getDepartmentName());
        dept.setHOD(department.getHOD());
        dept.setLocation(department.getLocation());
        dept.setIsNbaAccredited(department.getIsNbaAccredited());
        return departmentRepository.save(dept);

    }

    public void deleteDepartmentById(Integer id) {
        departmentRepository.deleteById(id);
    }
}
