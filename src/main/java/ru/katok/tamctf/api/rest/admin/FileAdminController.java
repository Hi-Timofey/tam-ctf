package ru.katok.tamctf.api.rest.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.katok.tamctf.api.util.GenericResponse;
import ru.katok.tamctf.domain.dto.FileDto;
import ru.katok.tamctf.service.FileService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class FileAdminController {

    private final FileService fileService;

    @ResponseBody
    @GetMapping(path = "files", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<FileDto>> getAllFiles() {
        return new GenericResponse<>(true, "ok", fileService.getAll());
    }

/*    @ResponseBody
    @PostMapping("files-add")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        fileService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }*/
}
