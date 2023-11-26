package ru.katok.tamctf.service.interfaces;


import ru.katok.tamctf.domain.dto.HintDto;

import java.util.List;

public interface IHintService {


    List<HintDto> getAll();
//TODO: разкоментить при написании функции нового хинта

/*    HintDto createNewHint(HintDto newHint);*/
}
