package myplugin.generator.fmmodel;

import java.util.List;

public abstract class FMForm {

	private String name;
	private List<FMUIComponent> components;

	public FMForm() {
	}

	public FMForm(String name, List<FMUIComponent> components) {
		this.name = name;
		this.components = components;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FMUIComponent> getComponents() {
		return components;
	}

	public void setComponents(List<FMUIComponent> components) {
		this.components = components;
	}

}
