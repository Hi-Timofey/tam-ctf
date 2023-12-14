package ru.katok.tamctf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.katok.tamctf.config.PlatformConfig;

@EnableConfigurationProperties(PlatformConfig.class)
@SpringBootApplication
public class TamctfApplication {
    public static void main(String[] args) {
        SpringApplication.run(TamctfApplication.class, args);
    }

}
