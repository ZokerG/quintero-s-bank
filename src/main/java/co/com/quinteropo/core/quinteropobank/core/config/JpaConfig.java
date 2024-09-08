package co.com.quinteropo.core.quinteropobank.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "co.com.quinteropo.core.quinteropobank.domain.repository")
@EntityScan(basePackages = "co.com.quinteropo.core.quinteropobank.domain.model")
public class JpaConfig {
}
