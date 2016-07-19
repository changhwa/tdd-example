package io.pretense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@EnableConfigurationProperties
@ActiveProfiles(profiles = "test")
public class DemoApplicationTests {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
