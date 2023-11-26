package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.domain.dto.FileDto;

import java.util.List;

public interface IFileService {

    List<FileDto> getAll();

    FileDto loadNewFile(FileDto newFile, String taskName);

/*    void store(MultipartFile file);*/

}
