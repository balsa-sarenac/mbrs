package demo.generated.model;

import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

<#list importedPackages as import>
<#if import.baseType && import.typePackage != "java.lang">
import ${import.typePackage}.${import.name};
<#elseif !import.baseType && import.typePackage != package>
import demo.generated.model.${import.name};
</#if>
</#list>

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity <#if class.tbName??>
@Table(name="${class.tbName}")
</#if>
${class.visibility} class ${class.name} {
<#list properties as property>
	<#if property.upper == 1 >
	${property.visibility} ${property.type.name} ${property.name};
    <#elseif property.upper == -1 > 
	${property.visibility} Set<${property.type.name}> ${property.name};
    <#else>   
    	<#list 1..property.upper as i>
	${property.visibility} ${property.type.name} ${property.name}${i};
		</#list>  
    </#if>     
</#list>

<#list persistentProps as prop>
<#if prop.isKey>
	@Id
<#else>
	@Column(name = "${prop.name?lower_case}", <#if (prop.length)??>length = ${prop.length}, </#if><#if (prop.precision)??> precision = ${prop.precision}, </#if>nullable = <#if prop.lower == 0>false<#else>true</#if>, unique = <#if prop.isUnique>true<#else>false</#if>)
</#if>
<#if prop.strategy == "AUTO" || prop.strategy  == "IDENTITY" >
	@GeneratedValue(strategy = GenerationType.${prop.strategy})
<#elseif prop.strategy == "SEQUENCE">
	@GeneratedValue(strategy = GenerationType.${prop.strategy}, generator = "${name?lower_case}_generator")
	@SequenceGenerator(name = "${name?lower_case}_generator", sequenceName = "${name?lower_case}_seq")		
</#if>
	${prop.visibility} ${prop.type.name} ${prop.name};
</#list>

<#list referencedProps as property>
	<#if property.upper == -1 && property.oppositeEnd.upper == -1>@ManyToMany<#elseif property.upper == -1 && property.oppositeEnd.upper == 1>@OneToMany<#elseif property.upper == 1 && property.oppositeEnd.upper == -1>@ManyToOne<#else>@OneToOne</#if><#rt>
	<#lt><#if (property.fetchType)?? || (property.cascadeType)?? || (property.mappedBy)??>(<#rt>
		<#if (property.cascadeType)??>
			<#lt>cascade = CascadeType.${property.cascadeType}<#rt>
		</#if>
		<#if (property.fetchType)??>
			<#lt><#if (property.cascadeType)??>, </#if>fetch = FetchType.${property.fetchType}<#rt>
		</#if>
		<#lt>)</#if>
	<#if (property.joinTable)??>@JoinTable</#if>
	<#if property.upper == 1>
	<#if (property.columnName)??>@JoinColumn(referencedColumnName="${property.columnName}")</#if>
	${property.visibility} ${property.type.name} ${property.name};
	<#else>
	${property.visibility} Set<${property.type.name}> ${property.name};
	</#if>${'\n'}
	</#list>	

}
