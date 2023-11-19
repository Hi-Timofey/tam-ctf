package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.HintDto;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.HintRepository;
import ru.katok.tamctf.service.interfaces.IHintService;

import java.util.List;


@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Service("hintService")
public class HintService {
    private final HintRepository hintRepository;


    public List<HintDto> getAll() {
        return hintRepository.findAll().stream()
                .map(MappingUtil::mapToHintDto).toList();
    }

}
