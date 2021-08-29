package myplugin.generator.fmmodel;

public class FMType extends FMElement {	
	//Qualified package name, used for import declaration
	//Empty string for standard library types
	private String typePackage;
	private Boolean baseType;


	public FMType(String name, String typePackage, Boolean baseType) {
		super(name);
		this.typePackage = typePackage;
		this.baseType = baseType;
	}
	
	public String getTypePackage() {
		return typePackage;
	}

	public void setTypePackage(String typePackage) {
		this.typePackage = typePackage;
	}

	public Boolean getBaseType() {
		return baseType;
	}

	public void setBaseType(Boolean baseType) {
		this.baseType = baseType;
	}

}
