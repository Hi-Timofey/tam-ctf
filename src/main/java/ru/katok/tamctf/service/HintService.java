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
@Transactional
@Service("hintService")
public class HintService implements IHintService {
    private final HintRepository hintRepository;


    @Override
    public List<HintDto> getAll() {
        return hintRepository.findAll().stream()
                .map(MappingUtil::mapToHintDto).toList();
    }

    @Override
    public HintDto creatNewHint(HintDto newHint) {
        return null;
    }

}
