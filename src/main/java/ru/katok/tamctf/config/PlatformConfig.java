package ru.katok.tamctf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDateTime;

@Data
//@Configuration
@ConfigurationProperties(prefix = "ctfconfig")
public class PlatformConfig {

    private String ctfTitle;

    private String flagWrapper;

    private LocalDateTime gameStartTime;

    private LocalDateTime freezeStartTime; // could be null for no freeze

    private LocalDateTime gameEndTime;


    /**
     * Function will return RE based on flagwrapper
     *
     * @return RE based on flagwrapper
     * Example: flagWrapper = "MCTF"
     * RE: "[Mm][Cc][Tt][Ff]{.+}"
     */
    public String getFlagRe() {
        StringBuilder sb = new StringBuilder();
        for (char c : flagWrapper.toCharArray()) {
            sb.append("[").append(Character.toLowerCase(c)).append(Character.toUpperCase(c)).append("]");
        }
        sb.append("{(?<flag>.+)}");
        return sb.toString();
    }
}