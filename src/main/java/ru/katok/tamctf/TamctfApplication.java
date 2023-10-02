package ru.katok.tamctf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TamctfApplication {

    public static void main(String[] args) {
        SpringApplication.run(TamctfApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello world";
    }

}
