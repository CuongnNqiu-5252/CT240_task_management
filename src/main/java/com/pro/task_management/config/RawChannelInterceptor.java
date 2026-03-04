package com.pro.task_management.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RawChannelInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info("Hello");

        if (headerAccessor != null && StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            String authHeader = headerAccessor.getFirstNativeHeader("Authorization");
            try {
                log.warn("Auth header: {}", authHeader);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {

                    String token = authHeader.substring(7);

                    Jwt jwt = jwtDecoder.decode(token);

                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);

                    headerAccessor.setUser(authentication);
                }
            } catch (Exception e) {
                log.error("Invalid token", e);
                throw new MessageDeliveryException("Invalid token");
            }
        }

        return message;
    }
}
