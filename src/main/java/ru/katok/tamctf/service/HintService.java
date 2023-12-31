package ru.katok.tamctf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.HintDto;
import ru.katok.tamctf.domain.entity.Hint;
import ru.katok.tamctf.domain.entity.Task;
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
public class HintService implements IHintService{
    private final HintRepository hintRepository;
    private final TaskRepository taskRepository;

    public List<HintDto> getAll() {
        return hintRepository.findAll().stream()
                .map(MappingUtil::mapToHintDto).toList();
    }
    public HintDto createNewHint(HintDto newHint){
        Long taskId = newHint.getTaskId();
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Can not find task with id %d".formatted(taskId)));

        Hint hint = Hint.builder()
                .text(newHint.getText())
                .task(task)
                .build();
        return MappingUtil.mapToHintDto(hintRepository.save(hint));
    }

    public void deleteHintById(Long id){
        Hint hint = hintRepository.findById(id).orElseThrow(() -> new RuntimeException("No hint with id %d".formatted(id)));
        hintRepository.delete(hint);
    }
}
