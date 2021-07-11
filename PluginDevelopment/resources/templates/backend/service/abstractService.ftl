package demo.generated.service.abs;

import demo.generated.dto.${name}DTO;
import demo.generated.exception.NotFoundException;
import demo.generated.model.${name};
import demo.generated.repository.${name}Repository;
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

    public ResponseEntity<List<${name}DTO>> getAll(int page, int size) {
        Page<${name}> ${name?uncap_first}s = repository.findAll(PageRequest.of(page, size));

        List<${name}DTO> ${name?uncap_first}DTOS = new ArrayList<>();
        for (${name} ${name?uncap_first}: ${name?uncap_first}s.getContent()) {
            ${name}DTO ${name?uncap_first}DTO = new ${name}DTO();
            <#list properties as prop>
            ${name?uncap_first}DTO.set${prop.name?cap_first}(${name?uncap_first}.get${prop.name?cap_first}());
			</#list>
			<#list persistentProps as prop>
            ${name?uncap_first}DTO.set${prop.name?cap_first}(${name?uncap_first}.get${prop.name?cap_first}());
			</#list>
            ${name?uncap_first}DTOS.add(${name?uncap_first}DTO);
        }

        return new ResponseEntity<>(${name?uncap_first}DTOS, HttpStatus.OK);
    }

    public ResponseEntity<${name}DTO> getById(Long id) {
        ${name} ${name?uncap_first} = repository.findById(id).orElseThrow(() -> new NotFoundException("${name} with given id doesn't exist."));

        ${name}DTO ${name?uncap_first}DTO = new ${name}DTO();
        <#list properties as prop>
        ${name?uncap_first}DTO.set${prop.name?cap_first}(${name?uncap_first}.get${prop.name?cap_first}());
		</#list>
		<#list persistentProps as prop>
        ${name?uncap_first}DTO.set${prop.name?cap_first}(${name?uncap_first}.get${prop.name?cap_first}());
		</#list>

        return new ResponseEntity<>(${name?uncap_first}DTO, HttpStatus.OK);
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
