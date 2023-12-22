package ru.katok.tamctf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.HintDto;
import ru.katok.tamctf.domain.entity.Hint;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.error.HintNotFoundException;
import ru.katok.tamctf.domain.error.TaskNotFoundException;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.HintRepository;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.service.interfaces.IHintService;

import java.util.List;
import java.util.Optional;


@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("hintService")
public class HintService implements IHintService {
    private final HintRepository hintRepository;
    private final TaskRepository taskRepository;

    private final ObjectMapper objectMapper;
    private final TelegramService telegramService;

    public List<HintDto> getAll() {
        return hintRepository.findAll().stream()
                .map(MappingUtil::mapToHintDto).toList();
    }

    public HintDto createNewHint(HintDto newHint) {
        Long taskId = newHint.getTaskId();
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Can not find task with id %d".formatted(taskId)));

        Hint hint = Hint.builder()
                .text(newHint.getText())
                .task(task)
                .build();
        telegramService.newHintTelegramNotification(hint);
        return MappingUtil.mapToHintDto(hintRepository.save(hint));
    }

    public Hint applyPatchToHint(JsonPatch patch, Hint hint) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(hint, JsonNode.class));
        return objectMapper.treeToValue(patched, Hint.class);
    }


    public HintDto editHintsById(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<Hint> hintOptional = hintRepository.findById(id);
        Hint hint = hintOptional.get();
        Hint patchedHint = hintRepository.save(applyPatchToHint(patch, hint));
        return MappingUtil.mapToHintDto(patchedHint);
    }

    public void deleteHintById(Long id) {
        Hint hint = hintRepository.findById(id).orElseThrow(() -> new HintNotFoundException("No hint with an id %d".formatted(id)));
        hintRepository.delete(hint);
    }
}
