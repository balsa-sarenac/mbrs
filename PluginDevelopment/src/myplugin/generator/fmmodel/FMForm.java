package myplugin.generator.fmmodel;

public abstract class FMForm {

	private String name;

	public FMForm() {
	}

	public FMForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
