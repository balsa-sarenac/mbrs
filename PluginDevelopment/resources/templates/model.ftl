package ${class.typePackage};

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

<#list persistentProperties as prop>
<#if prop.isKey>
	@Id
<#else>
	@Column(name = "${prop.name?lower_case}", <#if (prop.length)??>length = ${prop.length}, </#if><#if (prop.precision)??> precision = ${prop.precision}, </#if>nullable = <#if prop.lower == 0>false<#else>true</#if>, unique = <#if prop.unique>true<#else>false</#if>)
</#if>>
	${prop.visibility} ${prop.type.name} ${prop.name};
</#list>
}
