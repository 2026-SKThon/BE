package com.one.global.util;

import com.one.global.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {

    @GetMapping("/health")
    public BaseResponse<String> health() {
        log.info("[healthController] 성공");
        return BaseResponse.success("ok");
    }
}
