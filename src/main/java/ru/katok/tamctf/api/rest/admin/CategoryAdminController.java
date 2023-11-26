package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.service.GameService;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class CategoryAdminController {

    private final GameService gameService;
    @PostMapping(path = "/create-category", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<CategoryDto> createCategory (@RequestBody CategoryDto newCategory) {
        CategoryDto category = gameService.createNewCategory(newCategory);
        return new GenericResponse<>(true, "ok", category);
    }
    @DeleteMapping(path = "tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteCategory(@PathVariable String name) {
        this.gameService.deleteCategory(name);
        return new GenericResponse<>(true, "ok");
    }

}