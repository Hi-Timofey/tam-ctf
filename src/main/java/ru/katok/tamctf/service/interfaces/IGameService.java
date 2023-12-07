package ru.katok.tamctf.service.interfaces;

import ru.katok.tamctf.config.PlatformConfig;
import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.domain.dto.TaskDto;
import ru.katok.tamctf.service.dto.Score;

import java.util.List;

public interface IGameService {
    PlatformConfig retrieveGameConfig();

    List<TaskDto> getAllTasks();

    List<Score> getScoreboard();
    boolean submitFlag(String flag);

    CategoryDto createNewCategory(CategoryDto newCategory);
    void deleteCategory(String name);

    List<CategoryDto>getAllCategories();
}
