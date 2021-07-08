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
	<#lt><#if (property.fetch)?? || (property.cascade)?? || (property.mappedBy)?? || (property.optional)?? || (property.orphanRemoval)??>(<#rt>
		<#if (property.cascade)??>
			<#lt>cascade = CascadeType.${property.cascade}<#rt>
		</#if>
		<#if (property.fetch)??>
			<#lt><#if (property.cascade)??>, </#if>fetch = FetchType.${property.fetch}<#rt>
		</#if>
		<#if (property.mappedBy)??>
			<#lt><#if (property.cascade)?? || (property.fetch)??>, </#if>mappedBy = "${property.mappedBy}"<#rt>
		</#if>
		<#if (property.optional)??>
			<#lt><#if (property.cascade)?? || (property.fetch)?? || (property.mappedBy)??>, </#if>optional = ${property.optional?c}<#rt>
		</#if>
		<#if (property.orphanRemoval)??>
			<#lt><#if (property.cascade)?? || (property.fetch)?? || (property.mappedBy)?? || (property.optional)??>, </#if>orphanRemoval = ${property.orphanRemoval?c}<#rt>
		</#if>
		<#lt>)</#if>	
	<#if property.upper == 1>
	${property.visibility} ${property.type.name} ${property.name};
	<#else>
	${property.visibility} Set<${property.type.name}> ${property.name} = new HashSet<${property.type.name}>();	
	</#if>${'\n'}
	</#list>	
}
