package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FMClass extends FMType {	

	private String visibility;
	private String tbName;	

	//Class properties
	private List<FMProperty> FMProperties = new ArrayList<FMProperty>();
	
	//list of packages (for import declarations) 
	private List<FMType> importedPackages = new ArrayList<FMType>();
	
	//methods
	private List<FMMethod> FMMethods = new ArrayList<FMMethod>();

	/** @ToDo: add list of methods */
	
	
	public FMClass(String name, String classPackage, String visibility) {
		super(name, classPackage, false);
		this.visibility = visibility;
	}	
	
	public List<FMMethod> getMethods(){
		return FMMethods;
	}
	
	public Iterator<FMMethod> getMethodIterator(){
		return FMMethods.iterator();
	}
	
	public void addMethod(FMMethod method) {
		FMMethods.add(method);
	}
	
	public List<FMProperty> getProperties(){
		return FMProperties;
	}
	
	public Iterator<FMProperty> getPropertyIterator(){
		return FMProperties.iterator();
	}
	
	public void addProperty(FMProperty property){
		FMProperties.add(property);		
	}
	
	public int getPropertyCount(){
		return FMProperties.size();
	}
	
	public List<FMType> getImportedPackages(){
		return importedPackages;
	}

	public Iterator<FMType> getImportedIterator(){
		return importedPackages.iterator();
	}
	
	public void addImportedPackage(FMType importedPackage){
		importedPackages.add(importedPackage);		
	}
	
	public int getImportedCount(){
		return FMProperties.size();
	}
	
	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}	
	
	

	
	
}
