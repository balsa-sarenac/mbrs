package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;

public class FMMethod extends FMElement {

	private String visibility;
	private List<FMParameter> parameters = new ArrayList<FMParameter>();

	public FMMethod(String name, String visibility) {
		super(name);
		this.visibility = visibility;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public List<FMParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<FMParameter> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(FMParameter parameter) {
		parameters.add(parameter);
	}

}
