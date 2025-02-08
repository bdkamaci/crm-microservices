package com.crm.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://api-gateway/auth")
public interface AuthClient {
    @GetMapping("/validate")
    boolean validateToken(@RequestHeader("Authorization") String token);
}
