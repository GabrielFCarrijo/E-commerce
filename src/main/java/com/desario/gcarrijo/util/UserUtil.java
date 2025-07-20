package com.desario.gcarrijo.util;

import com.desario.gcarrijo.entity.User;
import com.desario.gcarrijo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    @Autowired
    private UserRepository userRepository;

    public User getUser () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((User) authentication.getPrincipal()).getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

}
