package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * FMModel: Singleton class. This is intermediate data structure that keeps
 * metadata extracted from MagicDraw model. Data structure should be optimized
 * for code generation using freemarker
 */

public class FMModel {

	private List<FMClass> classes = new ArrayList<FMClass>();
	private List<FMEnumeration> enumerations = new ArrayList<FMEnumeration>();
	private List<FMForm> forms = new ArrayList<FMForm>();
	private FMApplication application = new FMApplication();

	// ....
	/** @ToDo: Add lists of other elements, if needed */
	private FMModel() {

	}

	private static FMModel model;

	public static FMModel getInstance() {
		if (model == null) {
			model = new FMModel();
		}
		return model;
	}

	public List<FMClass> getClasses() {
		return classes;
	}

	public void setClasses(List<FMClass> classes) {
		this.classes = classes;
	}

	public List<FMEnumeration> getEnumerations() {
		return enumerations;
	}

	public void setEnumerations(List<FMEnumeration> enumerations) {
		this.enumerations = enumerations;
	}

	public List<FMForm> getForms() {
		return forms;
	}

	public void setForms(List<FMForm> forms) {
		this.forms = forms;
	}

	public FMApplication getApplication() {
		return application;
	}

	public void setApplication(FMApplication application) {
		this.application = application;
	}

}
