package ru.katok.tamctf.api.rest.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.CategoryDto;
import ru.katok.tamctf.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class CategoryAdminController {

    private final GameService gameService;

    @ResponseBody
    @GetMapping(path = "categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<CategoryDto>> getAllCategory() {
        return new GenericResponse<>(true, "ok", gameService.getAllCategories());
    }

    @PostMapping(path = "categories", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse<CategoryDto> createCategory(@RequestBody CategoryDto newCategory) {
        CategoryDto category = gameService.createNewCategory(newCategory);
        return new GenericResponse<>(true, "Category has been created", category);
    }

    @PatchMapping(path = "categorise/{name}",
            consumes = "application/json-patch+json",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody GenericResponse<CategoryDto> editCategory(@RequestBody JsonPatch patch, @PathVariable String name) throws JsonPatchException, JsonProcessingException {
        CategoryDto categoryDto = gameService.editCategoryByName(name, patch);
        return new GenericResponse<>(true, "UPDATED", categoryDto);
    }

    @DeleteMapping(path = "categories/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GenericResponse deleteCategory(@PathVariable String name) {
        this.gameService.deleteCategory(name);
        return new GenericResponse<>(true, "Category has been deleted");
    }

}