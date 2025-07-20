package com.desario.gcarrijo.controller;

import com.desario.gcarrijo.entity.User;
import com.desario.gcarrijo.entity.dto.UserRegisterDTO;
import com.desario.gcarrijo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.desario.gcarrijo.entity.dto.UserLoginDTO;
import com.desario.gcarrijo.entity.dto.JwtResponseDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterDTO dto) {
        User user = userService.register(dto, User.Role.USER);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/adm")
    public ResponseEntity<User> registerAdmin(@RequestBody UserRegisterDTO dto) {
        User user = userService.register(dto, User.Role.ADMIN);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO dto) {
        try {
            return ResponseEntity.ok(userService.authenticate(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}