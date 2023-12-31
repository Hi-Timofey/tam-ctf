package ru.katok.tamctf.service.interfaces;


import ru.katok.tamctf.domain.dto.HintDto;

import java.util.List;

public interface IHintService {


    List<HintDto> getAll();

    HintDto createNewHint(HintDto newHint);

    void deleteHintById(Long id);
}
