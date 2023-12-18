package io.github.GerdanyJr.Authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticated")
public class ProtectedController {
    
    @GetMapping
    public String authenticated() {
        return "Parabéns, você está autenticado!";
    }
}
