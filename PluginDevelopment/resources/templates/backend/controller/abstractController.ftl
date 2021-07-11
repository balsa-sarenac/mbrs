package demo.generated.controller.abs;

import demo.generated.service.${name}Service;
import demo.generated.dto.${name}DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public abstract class Abstract${name}Controller {

    @Autowired
    private ${name}Service service;

    @GetMapping
    public ResponseEntity<List<${name}DTO>> getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return service.getAll(page, size);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<${name}DTO> getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
