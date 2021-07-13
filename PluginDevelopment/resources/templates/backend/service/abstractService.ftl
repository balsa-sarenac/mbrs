package demo.generated.service.abs;

import demo.generated.dto.${name}DetailsDTO;
import demo.exception.NotFoundException;
import demo.generated.model.${name};
import demo.generated.repository.${name}Repository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class Abstract${name}Service {

    @Autowired
    private ${name}Repository repository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<List<${name}DetailsDTO>> getAll(int page, int size) {
        Page<${name}> ${name?uncap_first}s = repository.findAll(PageRequest.of(page, size));

        List<${name}DetailsDTO> ${name?uncap_first}DetailsDTOS = new ArrayList<>();
        for (${name} ${name?uncap_first}: ${name?uncap_first}s.getContent()) {
            ${name}DetailsDTO ${name?uncap_first}DetailsDTO = modelMapper.map(${name?uncap_first}, ${name}DetailsDTO.class);
            ${name?uncap_first}DetailsDTOS.add(${name?uncap_first}DetailsDTO);
        }

        return new ResponseEntity<>(${name?uncap_first}DetailsDTOS, HttpStatus.OK);
    }

    public ResponseEntity<${name}DetailsDTO> getById(Long id) {
        ${name} ${name?uncap_first} = repository.findById(id).orElseThrow(() -> new NotFoundException("${name} with given id doesn't exist."));

        ${name}DetailsDTO ${name?uncap_first}DetailsDTO = modelMapper.map(${name?uncap_first}, ${name}DetailsDTO.class);

        return new ResponseEntity<>(${name?uncap_first}DetailsDTO, HttpStatus.OK);
    }
    
    public ResponseEntity<?> delete(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
