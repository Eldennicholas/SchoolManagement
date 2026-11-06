package com.example.student_service.Dto;

import lombok.Data;

@Data
public class StudentResponseDto {
    private Integer studentId;
    private String studentName;
    private String email;
    private DepartmentDto departmentDto;
}
