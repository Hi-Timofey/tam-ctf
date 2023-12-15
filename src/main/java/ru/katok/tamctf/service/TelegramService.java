package ru.katok.tamctf.service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.TeamNotFoundException;

import java.util.logging.Logger;

public class TelegramService {
    private static final Logger log = Logger.getLogger(TelegramService.class.getName());
    private static final String telegramToken = "6928169899:AAESYQLwNhxPpLZRpbF7CgzyRC5-XEo8PiQ";
    private static final String chatId = "-1002018884307";

    private final RestTemplate restTemplate;

    public TelegramService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean sendMessage(String text) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("chat_id", chatId);
        params.add("parse_mode", "MarkdownV2");
        params.add("text", text);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://api.telegram.org/bot" + telegramToken + "/sendMessage",
                    HttpMethod.GET,
                    entity,
                    String.class);

            if (response.getStatusCode().is4xxClientError()) {
                log.severe("sendMessage(): " + text + "; " + response.getBody());
                return false;
            }

            return true;
        } catch (Exception e) {
            log.severe("sendMessage(): " + e.getMessage());
            return false;
        }
    }
    public boolean registerFirstBlood(String Team, String Task){
        return sendMessage("🩸 *FIRST BLOOD* 🩸\n*Team:* %s\n*Task:* %s".formatted(Team,Task));
    }
    public boolean newFirstBloodNotification(UserEntity user, Task Task){
        log.info("New firstblood tg notification triggered on: %s".formatted(Task.getName()));
        Team team;
        boolean ok;
        if (user.getTeam()==null)
            throw new TeamNotFoundException();
        team = user.getTeam();
        ok = registerFirstBlood(team.getName(), Task.getName());
        log.info("FIRSTBLOOD notification status : %s".formatted(ok));
        return ok;
    }
}