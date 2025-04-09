package com.finace.management.client;

import com.finace.management.config.BrevoClientConfig;
import com.finace.management.dto.request.BrevoEmailReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "brevoClient",
        url = "https://api.brevo.com/v3",
        configuration = BrevoClientConfig.class
)
public interface BrevoClient {

    @PostMapping("/smtp/email")
    ResponseEntity<String> sendEmail(@RequestBody BrevoEmailReqDto emailRequest);
}
