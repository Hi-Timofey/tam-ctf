package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping(path = "/hints", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<HintDto>> getAllHints() {
        return new GenericResponse<>(true, "ok", hintService.getAll());
    }

    @PostMapping(path = "/hints", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody GenericResponse<HintDto> createHint(@RequestBody HintDto newHint) {
        HintDto hint = hintService.createNewHint(newHint);
        return new GenericResponse<>(true, "Hint has been created", hint);
    }
    @DeleteMapping(path = "/hints/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteHint(@PathVariable Long id) {
        hintService.deleteHintById(id);
        return new GenericResponse<>(true, "Hint has been deleted");
    }
}
