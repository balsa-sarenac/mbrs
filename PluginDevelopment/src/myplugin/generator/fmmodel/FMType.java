package myplugin.generator.fmmodel;

public class FMType extends FMElement {	
	//Qualified package name, used for import declaration
	//Empty string for standard library types
	private String typePackage;


	public FMType(String name, String typePackage) {
		super(name);
		this.typePackage = typePackage;
	}
	
	public String getTypePackage() {
		return typePackage;
	}

	public void setTypePackage(String typePackage) {
		this.typePackage = typePackage;
	}

}
