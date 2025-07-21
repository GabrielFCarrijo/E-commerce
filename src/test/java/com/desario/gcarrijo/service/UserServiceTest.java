package com.desario.gcarrijo.service;

import com.desario.gcarrijo.config.exceptions.UserNotFoundException;
import com.desario.gcarrijo.entity.User;
import com.desario.gcarrijo.entity.dto.JwtResponseDTO;
import com.desario.gcarrijo.entity.dto.UserLoginDTO;
import com.desario.gcarrijo.entity.dto.UserRegisterDTO;
import com.desario.gcarrijo.repository.UserRepository;
import com.desario.gcarrijo.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setNome("User Teste");
        dto.setEmail("user@test.com");
        dto.setSenha("123456");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getSenha())).thenReturn("senhaCriptografada");

        User savedUser = new User();
        savedUser.setNome(dto.getNome());
        savedUser.setEmail(dto.getEmail());
        savedUser.setSenha("senhaCriptografada");
        savedUser.setRole(User.Role.USER);

        when(userRepository.save(any())).thenReturn(savedUser);

        User result = userService.register(dto, User.Role.USER);

        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals("senhaCriptografada", result.getSenha());
        assertEquals(User.Role.USER, result.getRole());
    }

    @Test
    void testRegisterEmailAlreadyExists() {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setNome("User Teste");
        dto.setEmail("email@test.com");
        dto.setSenha("123456");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(UserNotFoundException.class, () -> userService.register(dto, User.Role.USER));
    }

    @Test
    void testAuthenticateSuccess() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("email@test.com");
        dto.setSenha("senha123");

        User user = new User();
        user.setNome("User Teste");
        user.setEmail(dto.getEmail());
        user.setSenha("senhaCriptografada");
        user.setRole(User.Role.USER);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getSenha(), user.getSenha())).thenReturn(true);
        when(jwtUtil.generateToken(user.getEmail(), user.getRole().name())).thenReturn("jwt_token");

        JwtResponseDTO response = userService.authenticate(dto);

        assertNotNull(response);
        assertEquals("Bearer", response.getTipo());
        assertEquals("jwt_token", response.getToken());
        assertEquals("User Teste", response.getNome());
        assertEquals("email@test.com", response.getEmail());
        assertEquals("USER", response.getRole());
    }

    @Test
    void testAuthenticateInvalidEmail() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("naoexiste@test.com");
        dto.setSenha("123456");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.authenticate(dto));
    }

    @Test
    void testAuthenticateInvalidPassword() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setEmail("email@test.com");
        dto.setSenha("senhaErrada");

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setSenha("senhaCriptografada");
        user.setRole(User.Role.USER);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getSenha(), user.getSenha())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.authenticate(dto));
    }
}