package myplugin.analyzer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import myplugin.generator.fmmodel.CascadeType;
import myplugin.generator.fmmodel.FMPeristentProperty;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMReferencedProperty;
import myplugin.generator.fmmodel.FMType;
import myplugin.generator.fmmodel.FetchType;
import myplugin.generator.fmmodel.StrategyType;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.TypeMapping;

public class PropertyAnalyzer {
	public static FMProperty createProperty(Property property) throws AnalyzeException {
		String propertyName = property.getName();
		if (propertyName.trim() == null) {
			throw new AnalyzeException("Properties must have names!");
		}

		int lower = property.getLower();
		int upper = property.getUpper();

		String typeName = property.getType().getName();
		String typePackage = "";
		Boolean baseType = false;
		TypeMapping typeMapping = getDataType(typeName);
		if (typeMapping != null) {
			typeName = typeMapping.getDestType();
			typePackage = typeMapping.getLibraryName();
			baseType = true;
		}
		FMType type = new FMType(typeName, typePackage, baseType);

		String visibility = property.getVisibility().toString();
		FMProperty fmProperty = new FMProperty(propertyName, type, visibility, lower, upper);

		Stereotype persistentPropertyStereotype = StereotypesHelper.getAppliedStereotypeByString(property,
				"PersistentProperty");
		if (persistentPropertyStereotype != null) {	
			fmProperty = setPersistentPropertyData(property, fmProperty, persistentPropertyStereotype);
		}

		List<String> stereotypes = Arrays.asList("OneToMany", "OneToOne", "ManyToMany", "ManyToOne");
		for (String st : stereotypes) {
			Stereotype referencedPropertyStereotype = StereotypesHelper.getAppliedStereotypeByString(property,
					st);
			if (referencedPropertyStereotype != null) {
				fmProperty = setReferencedPropertyData(property, fmProperty, referencedPropertyStereotype);
			}
		}

		return fmProperty;
	}

	private static FMProperty setPersistentPropertyData(Property property, FMProperty fmProperty,
			Stereotype stereotype) {
		FMPeristentProperty persistantProperty = new FMPeristentProperty(fmProperty);
		List<Property> tags = stereotype.getOwnedAttribute();
		for (Property tag : tags) {
			createPersistentProperty(tag, property, fmProperty, stereotype, persistantProperty);
		}

		return persistantProperty;
	}

	private static void createPersistentProperty(Property tag, Property property, FMProperty fmProperty,
			Stereotype stereotype, FMPeristentProperty persistantProperty) {
		String tagName = tag.getName();

		List<?> values = StereotypesHelper.getStereotypePropertyValue(property, stereotype, tagName);

		if (values.size() > 0) {
			switch (tagName) {
			case "columnName":
				if (values.get(0) instanceof String) {
					String columnName = (String) values.get(0);
					persistantProperty.setColumnName(columnName);
				}
				break;
			case "isKey":
				if (values.get(0) instanceof Boolean) {
					Boolean isKey = (Boolean) values.get(0);
					persistantProperty.setIsKey(isKey);
				}
				break;
			case "isUnique":
				if (values.get(0) instanceof Boolean) {
					Boolean isUnique = (Boolean) values.get(0);
					persistantProperty.setIsUnique(isUnique);
				}
				break;
			case "length":
				if (values.get(0) instanceof Integer) {
					Integer length = (Integer) values.get(0);
					persistantProperty.setLength(length);
				}
				break;
			case "precision":
				if (values.get(0) instanceof Integer) {
					Integer precision = (Integer) values.get(0);
					persistantProperty.setPrecision(precision);
				}
				break;
			case "strategy":
				if (values.get(0) instanceof EnumerationLiteral) {
					EnumerationLiteral enumLiteral = (EnumerationLiteral) values.get(0);
					String enumString = enumLiteral.getName();
					StrategyType strategy = StrategyType.valueOf(enumString);
					persistantProperty.setStrategy(strategy);
				}
				break;
			}
		}
	}
	
	private static FMProperty setReferencedPropertyData(Property property, FMProperty fmProperty, Stereotype stereotype) {
		FMReferencedProperty referencedProperty = new FMReferencedProperty(fmProperty);
		List<Property> tags = stereotype.getOwnedAttribute();
		for (Property tag : tags) {
			createReferencedProperty(tag, property, fmProperty, stereotype, referencedProperty);
		}
		
		// get inherited attributes of stereotype
		Collection<? extends Property> inheritedTags = (Collection<? extends Property>) stereotype.getInheritedMember();
		for (Property inheritedTag : inheritedTags) {
			createReferencedProperty(inheritedTag, property, fmProperty, stereotype, referencedProperty);
		}
		return referencedProperty;
	}
	
	private static void createReferencedProperty(Property tag, Property property, FMProperty fmProperty,
			Stereotype stereotype, FMReferencedProperty linkedProperty) {

		Property referencingProperty = property.getOpposite();
		int upper = referencingProperty.getUpper();
		int lower = referencingProperty.getLower();
		String name = referencingProperty.getName();

		String typeName = referencingProperty.getType().getName();
		String typePackage = "";
		Boolean baseType = false;
		TypeMapping typeMapping = getDataType(typeName);

		if (typeMapping != null) {
			typeName = typeMapping.getDestType();
			typePackage = typeMapping.getLibraryName();
			baseType = true;
		}
		FMType type = new FMType(typeName, typePackage, baseType);

		FMProperty p = new FMProperty(name, type, referencingProperty.getVisibility().toString(), lower, upper);
		linkedProperty.setOppositeEnd(new FMReferencedProperty(p));
		String tagName = tag.getName();

		List<?> values = StereotypesHelper.getStereotypePropertyValue(property, stereotype, tagName);

		if (values.size() > 0) {
			switch (tagName) {			
			case "columnName":
				String columnName= (String) values.get(0);
				linkedProperty.setColumnName(columnName);
				break;
			case "joinTable":
				String joinTable= (String) values.get(0);
				linkedProperty.setJoinTable(joinTable);
				break;
			case "mappedBy":
				String mappedBy= (String) values.get(0);
				linkedProperty.setMappedBy(mappedBy);
				break;
			case "fetch":
				EnumerationLiteral enumLiteral = (EnumerationLiteral) values.get(0);
				String enumString = enumLiteral.getName();
				FetchType fetchType = FetchType.valueOf(enumString);
				linkedProperty.setFetchType(fetchType);
				break;
			case "cascade":
				enumLiteral = (EnumerationLiteral) values.get(0);
				enumString = enumLiteral.getName();
				CascadeType cascadeType = CascadeType.valueOf(enumString);
				linkedProperty.setCascadeType(cascadeType);
				break;
			}
		}
	}

	public static TypeMapping getDataType(String umlDataType) {
		List<TypeMapping> typeMappings = ProjectOptions.getProjectOptions().getTypeMappings();
		for (TypeMapping typeMapping : typeMappings) {
			if (typeMapping.getuMLType().equals(umlDataType)) {
				return typeMapping;
			}
		}
		return null;
	}
}
