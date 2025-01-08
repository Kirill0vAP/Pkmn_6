package ru.mirea.kirillovAP.pkmn.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.SignatureException;
import ru.mirea.kirillovAP.pkmn.dtos.User;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String JWT_SECRET = System.getenv("JWT_SECRET");
    private static final String JWT_DEBUG_KEY = "iubniut453t453vdsrg54yh4653fvxdc";
    private static final long EXPIRATION_TIME = 86400000;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserDetails verifyTokenAndGetUser(String token) {
        try {
            Claims claims;
            if(JWT_SECRET == null) {
                claims = Jwts.parserBuilder()
                        .setSigningKey(JWT_DEBUG_KEY.getBytes())
                        .build().parseClaimsJws(token).getBody();
            } else {
                claims = Jwts.parserBuilder()
                        .setSigningKey(JWT_SECRET.getBytes())
                        .build().parseClaimsJws(token).getBody();
            }
            return jdbcUserDetailsManager.loadUserByUsername(claims.getSubject());
        } catch(SignatureException e) {
            throw new RuntimeException("Неверный JWT", e);
        }
    }

    public String createToken(String username, List<GrantedAuthority> authorities) {
        String authoritiesString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        SecretKey key;
        if(JWT_SECRET == null) {
            key = Keys.hmacShaKeyFor(JWT_DEBUG_KEY.getBytes());

        } else {
            key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        }
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authoritiesString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public void registerUser(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        jdbcUserDetailsManager.createUser(new User(username,
                hashedPassword, true, authorities));
    }
}
