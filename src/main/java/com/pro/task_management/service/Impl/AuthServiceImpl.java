package com.pro.task_management.service.Impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.pro.task_management.dto.request.AuthRequestDTO;
import com.pro.task_management.dto.request.UserRequestDTO;
import com.pro.task_management.dto.response.AuthResponseDTO;
import com.pro.task_management.dto.response.UserResponseDTO;
import com.pro.task_management.entity.User;
import com.pro.task_management.enums.Role;
import com.pro.task_management.exception.AppException;
import com.pro.task_management.mapper.UserMapper;
import com.pro.task_management.repository.UserRepository;
import com.pro.task_management.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if(userRepository.existsByUsername(requestDTO.getUsername()))
            throw new AppException(HttpStatus.CONFLICT,"User existed");

        User user = userMapper.toEntity(requestDTO);
        user.setDeleted(false);
        user.setRole(Role.USER);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"User not existed"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new AppException(HttpStatus.UNAUTHORIZED,"Unauthenticated");

        String token = null;
        try {
            token = generateToken(user);
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        }

        UserResponseDTO userResponse = userMapper.toDTO(user);

        return AuthResponseDTO.builder()
                .token(token)
                .userData(userResponse)
                .build();
    }

    private String generateToken(User user) throws KeyLengthException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("trucllo.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .claim("role", user.getRole())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logout() {
        // Clear cookies
    }
}
