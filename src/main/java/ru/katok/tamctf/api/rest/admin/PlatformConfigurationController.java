package ru.katok.tamctf.api.rest.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.TeamDto;
import ru.katok.tamctf.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class PlatformConfigurationController {

    private final GameService gameService;
    @ResponseBody
    @GetMapping(path = "config", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<PlatformConfig> getPlatformConfig() {
        return new GenericResponse<>(true, "ok", gameService.retrieveGameConfig());
    }

    @ResponseBody
    @PostMapping(path = "config", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<PlatformConfig> setPlatformConfig(@RequestBody PlatformConfig platformConfig) {
        gameService.setPlatformConfig(platformConfig);
        return new GenericResponse<>(true, "ok", gameService.retrieveGameConfig());
    }
}
