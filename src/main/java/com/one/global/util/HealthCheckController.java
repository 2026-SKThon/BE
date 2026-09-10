package com.one.global.util;

import com.one.global.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public BaseResponse<String> health() {
        return BaseResponse.success("ok");
    }
}
