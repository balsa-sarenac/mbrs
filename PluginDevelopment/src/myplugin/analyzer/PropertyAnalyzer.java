package myplugin.analyzer;

import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import myplugin.generator.fmmodel.CascadeType;
import myplugin.generator.fmmodel.FMLinkedProperty;
import myplugin.generator.fmmodel.FMPeristentProperty;
import myplugin.generator.fmmodel.FMProperty;
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

		TypeMapping typeMapping = getDataType(typeName);
		if (typeMapping != null) {
			typeName = typeMapping.getDestType();
			typePackage = typeMapping.getLibraryName();
		}
		FMType type = new FMType(typeName, typePackage);

		String visibility = property.getVisibility().toString();
		FMProperty fmProperty = new FMProperty(propertyName, type, visibility, lower, upper);

		// ucitati tagove za perzistente atribute
		Stereotype persistentPropertyStereotype = StereotypesHelper.getAppliedStereotypeByString(property,
				"PersistentProperty");
		if (persistentPropertyStereotype != null) {	
			fmProperty = setPersistentPropertyData(property, fmProperty, persistentPropertyStereotype);
		}

		// ucitati tagove za linked atribute
		Stereotype linkedPropertyStereotype = StereotypesHelper.getAppliedStereotypeByString(property,
				"LinkedProperty");
		if (linkedPropertyStereotype != null) {
			fmProperty = setLinkedPropertyData(property, fmProperty, linkedPropertyStereotype);
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

		// preuzimanje vrednosti tagova
		List<?> values = StereotypesHelper.getStereotypePropertyValue(property, stereotype, tagName);

		// ako tag ima vrednosti
		if (values.size() > 0) {
			switch (tagName) {
			case "columnName":
				persistantProperty.setColumnName((String) values.get(0));
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
	
	private static FMProperty setLinkedPropertyData(Property property, FMProperty fmProperty, Stereotype stereotype) {
		FMLinkedProperty linkedProperty = new FMLinkedProperty(fmProperty);
		List<Property> tags = stereotype.getOwnedAttribute();
		for (Property tag : tags) {
			createLinkedProperty(tag, property, fmProperty, stereotype, linkedProperty);
		}
		return linkedProperty;
	}
	
	private static void createLinkedProperty(Property tag, Property property, FMProperty fmProperty,
			Stereotype stereotype, FMLinkedProperty linkedProperty) {

		Property referencingProperty = property.getOpposite();
		int upper = referencingProperty.getUpper();
		int lower = referencingProperty.getLower();
		String name = referencingProperty.getName();

		String typeName = referencingProperty.getType().getName();
		String typePackage = "";

		TypeMapping typeMapping = getDataType(typeName);

		if (typeMapping != null) {
			typeName = typeMapping.getDestType();
			typePackage = typeMapping.getLibraryName();
		}
		FMType type = new FMType(typeName, typePackage);

		FMProperty p = new FMProperty(name, type, referencingProperty.getVisibility().toString(), lower, upper);
		linkedProperty.setOppositeEnd(new FMLinkedProperty(p));
		String tagName = tag.getName();

		// preuzimanje vrednosti taga
		List<?> values = StereotypesHelper.getStereotypePropertyValue(property, stereotype, tagName);

		// ako tag ima vrednosti
		if (values.size() > 0) {
			switch (tagName) {			
			case "columnName":
				linkedProperty.setColumnName((String) values.get(0));
				break;
			case "joinTable":
				linkedProperty.setJoinTable((String) values.get(0));
				break;
			case "fetchType":
				if (values.get(0) instanceof EnumerationLiteral) {
					EnumerationLiteral enumLiteral = (EnumerationLiteral) values.get(0);
					String enumString = enumLiteral.getName();
					FetchType fetchType = FetchType.valueOf(enumString);
					linkedProperty.setFetchType(fetchType);
				}
				break;
			case "cascadeType":
				if (values.get(0) instanceof EnumerationLiteral) {
					EnumerationLiteral enumLiteral = (EnumerationLiteral) values.get(0);
					String enumString = enumLiteral.getName();
					CascadeType cascadeType = CascadeType.valueOf(enumString);
					linkedProperty.setCascadeType(cascadeType);
				}
				break;
			}
		}
	}

	private static TypeMapping getDataType(String umlDataType) {
		List<TypeMapping> typeMappings = ProjectOptions.getProjectOptions().getTypeMappings();
		for (TypeMapping typeMapping : typeMappings) {
			if (typeMapping.getuMLType().equals(umlDataType)) {
				return typeMapping;
			}
		}
		return null;
	}
}
