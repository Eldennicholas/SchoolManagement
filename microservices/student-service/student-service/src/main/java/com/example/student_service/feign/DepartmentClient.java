package com.example.student_service.feign;

import com.example.student_service.Dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("DEPARTMENT-SERVICE")
public interface DepartmentClient {
    @GetMapping("departs/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Integer id);
}
