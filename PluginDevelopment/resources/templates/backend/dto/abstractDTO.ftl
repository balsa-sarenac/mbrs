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
public class Abstract${name}DTO {
<#list properties as property>
	<#if property.upper == 1 >
	protected ${property.type.name} ${property.name};
	<#else>
	protected Set<${property.type.name}> ${property.name};
	</#if>
</#list>
<#list persistentProps as property>
	<#if property.upper == 1 >
	protected ${property.type.name} ${property.name};
	<#else>
	protected Set<${property.type.name}> ${property.name};	  
	</#if>
</#list>
}