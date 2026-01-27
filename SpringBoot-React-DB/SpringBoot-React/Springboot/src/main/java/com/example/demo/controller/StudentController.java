package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
@RequestMapping("/students") 
@CrossOrigin(origins = "http://localhost:3000") 
public class StudentController {
    private final StudentService service; 
    public StudentController(StudentService service) { 
        this.service = service; 
    }

    @GetMapping 
    public List<Student> getStudents() {
        return service.getAllStudents(); 
    }

    @PostMapping 
    public String addStudent(@RequestBody Student student) { 
        service.addStudent(student); 
        return "Student added successfully"; 
    }
}