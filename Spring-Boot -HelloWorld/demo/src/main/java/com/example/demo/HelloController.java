package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class HelloController {
@GetMapping("/hello")
public String sayHello() {
return "Hello, This is Spring Boot! from Chatrapathi 2410080019";
}
}