package ru.katok.tamctf.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.entity.Team;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.TeamNotFoundException;
import ru.katok.tamctf.service.errors.TelegramBotException;

import java.util.logging.Logger;
@Service("telegramService")
@Getter
@Setter
public class TelegramService {
    private static Logger log;
    private static String telegramToken;
    private static String chatId;

    private PlatformConfig platformConfig;
    TelegramService() {
        final Logger log = Logger.getLogger(TelegramService.class.getName());
        final String telegramToken = System.getenv("BOT_TOKEN");
        final String chatId = System.getenv("BOT_TARGET_CHAT_ID");
        if((telegramToken == null || chatId == null) && platformConfig.isTelegramBotIsEnabled())
            throw new RuntimeException("Telegram token or chat ID is null");
        //TODO: онмтруктор для заполнения полей и обработки NULL
        // Если NULL  RuntimeExeption и  приложение не поднимается
        // Так же обавить вариативность запуска бота в птаформ конфиг в виде bool переменной
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean sendMessage(String text) {
        TelegramService TelegramService = new TelegramService();
        if (platformConfig.isTelegramBotIsEnabled()){
            String messageUrl = "https://api.telegram.org/bot" + telegramToken + "/sendMessage?chat_id=" + chatId + "&text=" + text;
            ResponseEntity<String> response
                    = restTemplate.getForEntity(messageUrl, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        }
        else{
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
        log.info("Notification not triggered: task %s is not active".formatted(task.getName()));
        return false;
    }

}