package com.crm.api_gateway;

import com.crm.api_gateway.security.JwtAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Spy
    private ServerWebExchange exchange;

    private String secretKey = "supersecretkey1234567890supersecretkey1234567890";

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setJwtSecret(secretKey);
    }

    private String generateValidToken() {
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject("test-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    void shouldAllowValidToken() {
        String token = generateValidToken();
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        when(request.getHeaders()).thenReturn(headers);
        ServerWebExchange mockExchange = mock(ServerWebExchange.class);
        when(mockExchange.getRequest()).thenReturn(request);
        when(mockExchange.getResponse()).thenReturn(response);

        Mono<Void> result = jwtAuthenticationFilter.filter(mockExchange, exchange -> Mono.empty());

        assertDoesNotThrow(() -> result.block());
    }

    @Test
    void shouldRejectInvalidToken() {
        String invalidToken = "invalid.token.value";
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken);

        when(request.getHeaders()).thenReturn(headers);
        ServerWebExchange mockExchange = mock(ServerWebExchange.class);
        when(mockExchange.getRequest()).thenReturn(request);
        when(mockExchange.getResponse()).thenReturn(response);

        Mono<Void> result = jwtAuthenticationFilter.filter(mockExchange, exchange -> Mono.empty());

        assertThrows(Exception.class, () -> result.block());
    }

    @Test
    void shouldRejectMissingToken() {
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);

        HttpHeaders headers = new HttpHeaders();
        when(request.getHeaders()).thenReturn(headers);

        ServerWebExchange mockExchange = mock(ServerWebExchange.class);
        when(mockExchange.getRequest()).thenReturn(request);
        when(mockExchange.getResponse()).thenReturn(response);

        Mono<Void> result = jwtAuthenticationFilter.filter(mockExchange, exchange -> Mono.empty());

        assertThrows(Exception.class, () -> result.block());
    }
}