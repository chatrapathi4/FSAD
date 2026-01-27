package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository 
public class StudentRepository {
    private final List<Student> students = new ArrayList<>(); 

    public StudentRepository() {
        students.add(new Student(1, "Aravind", "CSE")); 
        students.add(new Student(2, "Ravi", "ECE")); 
    }

    public List<Student> findAll() { return students; } 

    public void save(Student student) { students.add(student); } 
}