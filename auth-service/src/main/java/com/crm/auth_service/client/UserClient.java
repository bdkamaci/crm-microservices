package com.crm.auth_service.client;

import com.crm.auth_service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://user-service")
public interface UserClient {
    @GetMapping("/users/{email}")
    User getUserByEmail(@PathVariable String email);
}
