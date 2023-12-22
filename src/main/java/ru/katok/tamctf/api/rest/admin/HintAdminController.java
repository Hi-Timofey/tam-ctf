package ru.katok.tamctf.api.rest.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

    @PostMapping(path = "/hints", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<HintDto> createHint(@RequestBody HintDto newHint) {
        HintDto hint = hintService.createNewHint(newHint);
        return new GenericResponse<>(true, "Hint has been created", hint);
    }

    @PatchMapping(path = "hints/{id}",
            consumes = "application/json-patch+json",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody GenericResponse<HintDto> editHintsById(
            @RequestBody JsonPatch patch, @PathVariable Long id)
            throws JsonPatchException, JsonProcessingException {
        HintDto hint = hintService.editHintsById(id, patch);
        return new GenericResponse<>(true, "UPDATED", hint);
    }

    @DeleteMapping(path = "/hints/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteHint(@PathVariable Long id) {
        hintService.deleteHintById(id);
        return new GenericResponse<>(true, "Hint has been deleted");
    }
}
