package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.HintDto;
import ru.katok.tamctf.domain.entity.Hint;
import ru.katok.tamctf.domain.entity.Task;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.HintRepository;
import ru.katok.tamctf.service.interfaces.IHintService;

import java.util.List;


@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("hintService")
public class HintService implements IHintService{
    private final HintRepository hintRepository;


    public List<HintDto> getAll() {
        return hintRepository.findAll().stream()
                .map(MappingUtil::mapToHintDto).toList();
    }
    public HintDto createNewHint(HintDto newHint){
        Hint hint = Hint.builder()
                .text(newHint.getText())
                .task(newHint.getTask())
                .build();
        return MappingUtil.mapToHintDto(hintRepository.save(hint));
    }

    public void deleteHintById(Long id){
        hintRepository.delete(hintRepository.getById(id));
    }
}
