package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class HelloController {

  @GetMapping("/hello")
  public Map<String, String> sayHello() {
    Map<String, String> response = new HashMap<>();
    response.put("message", "Hii this is Chatrapathi, This is Spring Boot!");
    return response;
  }
}