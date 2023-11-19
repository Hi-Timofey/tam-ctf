package ru.katok.tamctf.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Data
//@Configuration
@ConfigurationProperties(prefix = "ctfconfig")
public class PlatformConfig {

    private String ctfTitle;

    private String flagWrapper;

    //TODO: swap to DateTime
    private LocalDateTime gameStartTime;

    private LocalDateTime freezeStartTime; // could be null for no freeze

    private LocalDateTime gameEndTime;
}
