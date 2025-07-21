package com.desario.gcarrijo.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserLoginDTO {
    private String email;
    private String senha;
} 