package com.example.demo.model;

public class Student {
    private int id; 
    private String name; 
    private String branch;

    public Student(int id, String name, String branch) { 
        this.id = id; 
        this.name = name; 
        this.branch = branch; 
    }

    // Getters [cite: 298, 301, 304]
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBranch() { return branch; }
}