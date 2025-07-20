package com.desario.gcarrijo.entity.dto;

import lombok.Data;

@Data
public class JwtResponseDTO {
    private String token;
    private String tipo;
    private String nome;
    private String email;
    private String role;
} 