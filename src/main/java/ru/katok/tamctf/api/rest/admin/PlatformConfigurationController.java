package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.service.GameService;

import java.io.IOException;
import java.net.URISyntaxException;

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

    @ResponseBody
    @GetMapping(value = "dumpscore", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadBackUp() throws IOException, URISyntaxException {
        String file = gameService.dumpScores();
        byte[] customerJsonBytes = file.getBytes();
        return ResponseEntity
                .ok()
                .contentLength(customerJsonBytes.length)
                .header("Content-Disposition", "attachment; filename=\"scores.json\"")
                .contentType(
                        MediaType.parseMediaType("application/octet-stream"))
                .body(file);
    }
}
