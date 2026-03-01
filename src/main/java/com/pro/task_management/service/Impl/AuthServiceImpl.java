package com.pro.task_management.service.Impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.pro.task_management.dto.request.AuthRequestDTO;
import com.pro.task_management.dto.request.ChangePasswordRequestDTO;
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
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if(userRepository.existsByUsername(requestDTO.getUsername()))
            throw new AppException(HttpStatus.CONFLICT,"User existed");
        // Thêm check email
        if(userRepository.existsByEmail(requestDTO.getEmail()))
            throw new AppException(HttpStatus.CONFLICT,"Email already existed");

        User user = userMapper.toEntity(requestDTO);
        user.setDeleted(false);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not existed"));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new AppException(HttpStatus.CONFLICT, "Password is not correct");

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
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("role", user.getRole())
                .claim("userId", user.getId())
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
    public void changePassword(ChangePasswordRequestDTO requestDTO) {
        var user = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not existed"));

        // Kiểm tra mật khẩu cũ
        if(!passwordEncoder.matches(requestDTO.getOldPassword(), user.getPassword())) {
            throw new AppException(HttpStatus.CONFLICT, "Old password is not correct");
        }

        // Kiểm tra mật khẩu mới phải khác mật khẩu cũ
        if(passwordEncoder.matches(requestDTO.getNewPassword(), user.getPassword())) {
            throw new AppException(HttpStatus.CONFLICT, "New password must be different from old password");
        }

        // Mã hóa mật khẩu mới và lưu vào database
        user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public void logout() {
        // Clear cookies
    }
}
