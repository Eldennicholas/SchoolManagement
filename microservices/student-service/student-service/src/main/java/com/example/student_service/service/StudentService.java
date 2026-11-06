package com.example.student_service.service;

import com.example.student_service.Dto.DepartmentDto;
import com.example.student_service.Dto.StudentResponseDto;
import com.example.student_service.exception.StudentNotFoundException;
import com.example.student_service.feign.DepartmentClient;
import com.example.student_service.model.Student;
import com.example.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private DepartmentClient departmentClient;

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student){
        // validate the departmentId coz they might enter non-existing departmentId
        Integer dept_id = student.getDepartmentId();
        departmentClient.getDepartmentById(dept_id); //FEIGN EXCEPTION
        return studentRepository.save(student);
    }

    public Student getStudentById(Integer id) {
       Optional<Student> stud = studentRepository.findById(id);
       if(stud.isEmpty()){
           throw new StudentNotFoundException("not student found with "+id);
       }
       return stud.get();
    }

    public StudentResponseDto getStudentsDetails(Integer id) {
        Student student = getStudentById(id);
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setStudentId(student.getId());
        responseDto.setStudentName(student.getName());
        responseDto.setEmail(student.getEmail());
        Integer depart_id = student.getDepartmentId();
        //  I need to call the departmentById here which already a method in departmentService
        //  we use open feign for this.
        DepartmentDto departmentDto = departmentClient.getDepartmentById(depart_id).getBody();
        responseDto.setDepartmentDto(departmentDto);
        return responseDto;

    }
    /**
     * O.F is a declarative REST client for java applications.
     * Java interface is used to declare other(department) service methods.
     * It simplifies the process of making Http requests to other services.
     *   WORK FLOW  client(req)-> studentService uses O.F call dept_service.
     *   BENEFITS - 1.seamless integration, built-in support for load balancing.
     */

}
