package myplugin.generator.fmmodel;

import java.util.ArrayList;

public class FMEnumeration extends FMType {
	private ArrayList<String> values = new ArrayList<String>();

	public FMEnumeration(String name, String typePackage) {
		super(name, typePackage, false);
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public int getValuesCount() {
		return values.size();
	}

	public String getValueAt(int i) {
		return values.get(i);
	}

}
