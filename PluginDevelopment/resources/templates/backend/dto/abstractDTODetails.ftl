// generated on ${.now?date} at ${.now?time} based on ${.current_template_name}
package demo.generated.dto.abs;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

<#list importedPackages as import>
<#if import.baseType && import.typePackage != "java.lang">
import ${import.typePackage}.${import.name};
<#elseif !import.baseType && import.typePackage != package>
import demo.generated.dto.abs.${import.name};
</#if>
</#list>

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Abstract${name}DetailsDTO extends Abstract${name}DTO {
<#list referencedProps as property> 
	<#if property.upper == 1 >
	protected Abstract${property.type.name}DTO ${property.name};
	<#else>
	protected Set<Abstract${property.type.name}DTO> ${property.name};
	</#if>
</#list>
}