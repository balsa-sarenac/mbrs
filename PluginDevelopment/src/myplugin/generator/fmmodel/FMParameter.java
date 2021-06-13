package myplugin.generator.fmmodel;

public class FMParameter extends FMElement {

	private String type;
	private ParametarTypeEnum parameterType;
	private Integer lower;
	private Integer upper;

	public FMParameter(String name, String type, ParametarTypeEnum parameterType, Integer lower, Integer upper) {
		super(name);
		this.type = type;
		this.parameterType = parameterType;
		this.lower = lower;
		this.upper = upper;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public ParametarTypeEnum getParameterType() {
		return parameterType;
	}

	public void setParameterType(ParametarTypeEnum parameterType) {
		this.parameterType = parameterType;
	}

}
