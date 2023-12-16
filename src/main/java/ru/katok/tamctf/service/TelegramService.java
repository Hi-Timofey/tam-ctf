package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.TeamNotFoundException;
import ru.katok.tamctf.service.errors.TelegramBotException;

import java.util.logging.Logger;
@Service("telegramService")
public class TelegramService {
    private static final Logger log = Logger.getLogger(TelegramService.class.getName());
    private static final String telegramToken = System.getenv("BOT_TOKEN");
    private static final String chatId = System.getenv("BOT_TARGET_CHAT_ID");

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean sendMessage(String text) {
        try {
            String messageUrl = "https://api.telegram.org/bot" + telegramToken + "/sendMessage?chat_id=" + chatId + "&text=" + text;
            ResponseEntity<String> response
                    = restTemplate.getForEntity(messageUrl, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        }catch (TelegramBotException e) {
            return false;
        }
    }
    public boolean registerFirstBlood(String Team, String Task){
        return sendMessage("Task_created");
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
    public boolean registerTask(String task, String category){
       return sendMessage("New task: %s in category: %s".formatted(task,category));
    }
    public boolean newTaskTelegramNotification(Task task){
        if(task.isActive()){
            boolean ok;
            log.info("New task tg notification triggered on: %s".formatted(task.getName()));
            ok = registerTask(task.getName(), task.getCategory().getName());
            log.info("TASK notification status : %s".formatted(ok));
            return ok;
        }
        return false;
    }

}