package myplugin.generator.options;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//this class can be saved and loaded as xml
//not singleton
public class SerializableProjectOptions implements Serializable {
	private static final long serialVersionUID = 1L;
	private String path;
	private List<TypeMapping> typeMappings = new ArrayList<TypeMapping>();
	private Map<String, GeneratorOptions> generatorOptions = new HashMap<String, GeneratorOptions>();
	private List<StaticResource> staticResources = new ArrayList<StaticResource>();

	public SerializableProjectOptions(ProjectOptions projectOptions) {
		typeMappings = projectOptions.getTypeMappings();
		generatorOptions = projectOptions.getGeneratorOptions();
		staticResources = projectOptions.getStaticResources();
	}

	public SerializableProjectOptions() {
		super();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<TypeMapping> getTypeMappings() {
		return typeMappings;
	}

	public void setTypeMappings(List<TypeMapping> typeMappings) {
		this.typeMappings = typeMappings;
	}

	public Map<String, GeneratorOptions> getGeneratorOptions() {
		return generatorOptions;
	}

	public void setGeneratorOptions(Map<String, GeneratorOptions> generatorOptions) {
		this.generatorOptions = generatorOptions;
	}

	public List<StaticResource> getStaticResources() {
		return staticResources;
	}

	public void setStaticResources(List<StaticResource> staticResources) {
		this.staticResources = staticResources;
	}
}
