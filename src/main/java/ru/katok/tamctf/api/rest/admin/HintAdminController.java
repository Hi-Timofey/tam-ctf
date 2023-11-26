package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.HintDto;
import ru.katok.tamctf.service.HintService;

import java.util.List;
@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class HintAdminController {

    private final HintService hintService;

    @ResponseBody
    @GetMapping(path = "hints", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<HintDto>> getAllHints() {
        return new GenericResponse<>(true, "ok", hintService.getAll());
    }
//TODO: обавить путь для создания хинта  и прописать логику в сервисе

/*   @GetMapping(path = "newhint", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<HintDto> createNewHint() {
        return new GenericResponse<>(true, "ok", hintService.createNewHint());
    }*/
}
