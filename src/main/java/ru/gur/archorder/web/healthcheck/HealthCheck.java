package ru.gur.archorder.web.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gur.archorder.web.healthcheck.response.Health;

@RestController
public class HealthCheck {

    @GetMapping("/health")
    public Health health() {
        return Health.builder()
                .status(Health.HealthStatus.OK)
                .build();
    }
}
