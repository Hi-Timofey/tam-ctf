package ru.katok.tamctf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TamctfApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(TamctfApplication.class, args);
    }

    @GetMapping(path = "/healthcheck")
    public String getHello() {
        logger.info("Got GET on HEALTHCHECK, sending feedback...");
        return "get: healthy service cool mood!";
    }

    @PostMapping(path = "/healthcheck")
    public String postHello() {
        logger.info("Got POST on HEALTHCHECK, sending feedback...");
        return "post: healthy service cool mood!";
    }
    @GetMapping(path = "/registration")
    public String getIndex()
    {
        logger.info("Got GET on INDEX, sending feedback...");
        return "registration";
    }

}
