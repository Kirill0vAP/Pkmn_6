package ru.mirea.kirillovAP.pkmn.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kirillovAP.pkmn.dtos.User;
import ru.mirea.kirillovAP.pkmn.services.JwtService;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;

    @PostMapping("/success")
    public String successPage(HttpServletResponse response,
                              Authentication authentication) {
        String token = jwtService.createToken(authentication.getName(), authentication.getAuthorities()
                .stream().collect(Collectors.toUnmodifiableList()));
        response.addCookie(new Cookie("JWT", Base64.getEncoder().encodeToString(token.getBytes())));
        return "Login successful";
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        if(authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return "Not authenticated";
        }
    }

    @PostMapping("/registration")
    public String registration(@RequestBody User user) {
        if(user.getPassword() != null && user.getUsername() != null) {
            jwtService.registerUser(user.getUsername(), user.getPassword());
            return "201. Registration successful.";
        } else {
            return "400";
        }
    }
}
