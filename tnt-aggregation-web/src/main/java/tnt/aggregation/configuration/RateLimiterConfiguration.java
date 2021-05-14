package tnt.aggregation.configuration;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

public class RateLimiterConfiguration {

    @Bean
    public RateLimiter rateLimiter(){
        return  RateLimiter.create(0.1d, Duration.ofSeconds(30));
    }
}
