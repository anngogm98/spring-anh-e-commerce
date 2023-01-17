package com.example.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/product"))
public class Health {

    @GetMapping("/check")
    public String checkHealth() {
        return "Alive";
    }
}
