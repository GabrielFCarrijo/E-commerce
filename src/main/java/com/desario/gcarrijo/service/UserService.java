package com.desario.gcarrijo.service;

import com.desario.gcarrijo.config.exceptions.UserNotFoundException;
import com.desario.gcarrijo.entity.User;
import com.desario.gcarrijo.entity.dto.JwtResponseDTO;
import com.desario.gcarrijo.entity.dto.UserLoginDTO;
import com.desario.gcarrijo.entity.dto.UserRegisterDTO;
import com.desario.gcarrijo.repository.UserRepository;
import com.desario.gcarrijo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public User register(UserRegisterDTO dto, User.Role accessRole) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserNotFoundException("Email already registered");
        }

        User user = User.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .role(accessRole)
                .build();

        return userRepository.save(user);
    }

    public JwtResponseDTO authenticate(UserLoginDTO dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("Invalid username or password");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(dto.getSenha(), user.getSenha())) {
            throw new UserNotFoundException("Invalid username or password");
        }
        return generateJwtToken(user, user.getRole());
    }

    private JwtResponseDTO generateJwtToken (User user,User.Role role) {
        String token = jwtUtil.generateToken(user.getEmail(), String.valueOf(role));
        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(token);
        response.setTipo("Bearer");
        response.setNome(user.getNome());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        return response;
    }
} 