package ru.katok.tamctf.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class PlatformConfig {

    private String ctfTitle;

    private String flagWrapper;

    //TODO: swap to DateTime
    private LocalDateTime gameStartTime;

    private LocalDateTime freezeStartTime; // could be null for no freeze

    private LocalDateTime gameEndTime;
}
