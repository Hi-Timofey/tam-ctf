package ru.katok.tamctf.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PlatformConfig {

    private String ctfTitle = "TAM-CTF Event";

    private String flagWrapper = "CTF";

    //TODO: swap to DateTime
    private LocalDateTime gameStartTime;

    private LocalDateTime freezeStartTime; // could be null for no freeze

    private LocalDateTime gameEndTime;
}
