package com.example.student_service.Dto;

import lombok.Data;

@Data
public class DepartmentDto {//Data transfer Object
    private Integer departmentId;
    private String departmentName;
    private String HOD;
    private String location;
    private Boolean isNbaAccredited;
}
