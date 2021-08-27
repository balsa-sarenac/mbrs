// generated on ${.now?date} at ${.now?time} based on ${.current_template_name}
package demo.generated.service.abs;

import demo.generated.dto.${name}DetailsDTO;
import demo.exception.NotFoundException;
import demo.generated.model.${name};
import demo.generated.repository.${name}Repository;
<#assign referencedTypes = []>
<#list referencedProps as prop>
	<#if ! referencedTypes?seq_contains(prop.type.name)>
    	<#assign referencedTypes = referencedTypes + [prop.type.name] />
	</#if>
</#list>
<#list referencedTypes as type>
import demo.generated.repository.${type}Repository;
import demo.generated.dto.abs.Abstract${type}DTO;
import demo.generated.model.${type};
</#list>
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Abstract${name}Service {

    @Autowired
    private ${name}Repository repository;
    
    @Autowired
    private ModelMapper modelMapper;
	<#list referencedTypes as type>
	
	@Autowired
	private ${type}Repository ${type?lower_case}Repository;	
	</#list>
	public ResponseEntity<List<${name}DetailsDTO>> getAll() {
        List<${name}> ${name?uncap_first}s = repository.findAll();

        List<${name}DetailsDTO> ${name?uncap_first}DetailsDTOS = new ArrayList<>();
        for (${name} ${name?uncap_first}: ${name?uncap_first}s) {
            ${name}DetailsDTO ${name?uncap_first}DetailsDTO = modelMapper.map(${name?uncap_first}, ${name}DetailsDTO.class);
            ${name?uncap_first}DetailsDTOS.add(${name?uncap_first}DetailsDTO);
        }

        return new ResponseEntity<>(${name?uncap_first}DetailsDTOS, HttpStatus.OK);
    }
    
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
    
    public ResponseEntity<${name}DetailsDTO> create(${name}DetailsDTO ${name?uncap_first}DetailsDto) {
        ${name} ${name?uncap_first} = modelMapper.map(${name?uncap_first}DetailsDto, ${name}.class);
        
        <#list referencedProps as prop>
        	<#if prop.upper == 1 >
        if(${name?uncap_first}DetailsDto.get${prop.name?cap_first}()!=null){
			${name?uncap_first}.set${prop.name?cap_first}(${prop.type.name?lower_case}Repository.findById(${name?uncap_first}DetailsDto.get${prop.name?cap_first}().getId()).orElseThrow(() -> new NotFoundException("${prop.type.name} with given id doesn't exist.")));
		}
			<#elseif prop.upper == -1 >
		if(${name?uncap_first}DetailsDto.get${prop.name?cap_first}()!=null){
			${name?uncap_first}.set${prop.name?cap_first}(${prop.type.name?lower_case}Repository.findAllById(${name?uncap_first}DetailsDto.get${prop.name?cap_first}().stream().map(Abstract${prop.type.name?cap_first}DTO::getId).collect(Collectors.toSet())).stream().collect(Collectors.toSet()));
			for(${prop.type.name?cap_first} ${prop.type.name?uncap_first} : ${name?uncap_first}.get${prop.name?cap_first}()){
                ${prop.type.name?uncap_first}.set${name?cap_first}(${name?uncap_first});
            }
		}
			 <#else>   
		    	<#list 1..prop.upper as i>
		if(${name?uncap_first}DetailsDto.get${prop.name?cap_first}()!=null){
			${name?uncap_first}.set${property.name?cap_first}${i}(${prop.name?lower_case}Repository.findById(${name?uncap_first}DetailsDTO.get${prop.name?cap_first}${i}.getId()).orElseThrow(() -> new NotFoundException("${prop.type.name} with given id doesn't exist.")));
		}
				</#list>  
		    </#if>     
        </#list>
        
        ${name?uncap_first} = repository.save(${name?uncap_first});

        ${name}DetailsDTO ${name?uncap_first}DetailsDTO = modelMapper.map(${name?uncap_first}, ${name}DetailsDTO.class);       
        return new ResponseEntity<>(${name?uncap_first}DetailsDTO, HttpStatus.OK);
    }
    
    public ResponseEntity<${name}DetailsDTO> update(Long id, ${name}DetailsDTO ${name?uncap_first}DetailsDto) {
    	${name} ${name?uncap_first} = repository.findById(id).orElseThrow(() -> new NotFoundException("${name} with given id doesn't exist."));
        ${name?uncap_first} = modelMapper.map(${name?uncap_first}DetailsDto, ${name}.class);
        ${name?uncap_first}.set${keyName?capitalize}(id);
        
        <#list referencedProps as prop>
        	<#if prop.upper == 1 >
        if(${name?uncap_first}DetailsDto.get${prop.name?cap_first}()!=null){
			${name?uncap_first}.set${prop.name?cap_first}(${prop.type.name?lower_case}Repository.findById(${name?uncap_first}DetailsDto.get${prop.name?cap_first}().getId()).orElseThrow(() -> new NotFoundException("${prop.type.name} with given id doesn't exist.")));
		}
			<#elseif prop.upper == -1 >
		if(${name?uncap_first}DetailsDto.get${prop.name?cap_first}()!=null){
			${name?uncap_first}.set${prop.name?cap_first}(${prop.type.name?lower_case}Repository.findAllById(${name?uncap_first}DetailsDto.get${prop.name?cap_first}().stream().map(Abstract${prop.type.name?cap_first}DTO::getId).collect(Collectors.toSet())).stream().collect(Collectors.toSet()));
			for(${prop.type.name?cap_first} ${prop.type.name?uncap_first} : ${name?uncap_first}.get${prop.name?cap_first}()){
                ${prop.type.name?uncap_first}.set${name?cap_first}(${name?uncap_first});
            }
		}
			 <#else>   
		    	<#list 1..prop.upper as i>
		if(${name?uncap_first}DetailsDto.get${prop.name?cap_first}()!=null){
			${name?uncap_first}.set${property.name?cap_first}${i}(${prop.name?lower_case}Repository.findById(${name?uncap_first}DetailsDTO.get${prop.name?cap_first}${i}.getId()).orElseThrow(() -> new NotFoundException("${prop.type.name} with given id doesn't exist.")));
		}
				</#list>  
		    </#if>     
        </#list>
        
        ${name?uncap_first} = repository.save(${name?uncap_first});

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
