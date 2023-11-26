package ru.katok.tamctf.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.dto.FileDto;
import ru.katok.tamctf.domain.util.MappingUtil;
import ru.katok.tamctf.repository.FileRepository;
import ru.katok.tamctf.repository.TaskRepository;
import ru.katok.tamctf.service.interfaces.IFileService;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service("fileService")
public class FileService implements IFileService {

    private final FileRepository  fileRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<FileDto> getAll() {
        return fileRepository.findAll().stream()
                .map(MappingUtil::mapToFileDto).toList();
    }
    @Override
    public FileDto loadNewFile(FileDto newFile, String taskName)
    {

        return newFile;
    }

/*    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }*/
/*    }
}*/

}
