package myplugin.analyzer;

import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import myplugin.generator.fmmodel.FMApplication;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMComponent;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMMethod;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMStandardForm;
import myplugin.generator.fmmodel.FMTableView;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Operation;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

/**
 * Model Analyzer takes necessary metadata from the MagicDraw model and puts it
 * in the intermediate data structure (@see myplugin.generator.fmmodel.FMModel)
 * optimized for code generation using freemarker. Model Analyzer now takes
 * metadata only for ejb code generation
 * 
 * @ToDo: Enhance (or completely rewrite) myplugin.generator.fmmodel classes and
 *        Model Analyzer methods in order to support GUI generation.
 */

public class ModelAnalyzer {
	// root model package
	private Package root;

	// java root package for generated code
	private String filePackage;

	public ModelAnalyzer(Package root, String filePackage) {
		super();
		this.root = root;
		this.filePackage = filePackage;
	}

	public Package getRoot() {
		return root;
	}

	public void prepareModel() throws AnalyzeException {
		FMModel.getInstance().getClasses().clear();
		FMModel.getInstance().getEnumerations().clear();
		FMModel.getInstance().getComponents().clear();
		processPackage(root, filePackage);
	}

	private void processPackage(Package pack, String packageOwner) throws AnalyzeException {
		// Recursive procedure that extracts data from package elements and stores it in
		// the
		// intermediate data structure

		if (pack.getName() == null)
			throw new AnalyzeException("Packages must have names!");

		String packageName = packageOwner;
		if (pack != root) {
			packageName += "." + pack.getName();
		}

		if (pack.hasOwnedElement()) {

			// application description from data (root) package
			FMApplication application = getAppDescription(pack);
			FMModel.getInstance().setApplication(application);

			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();

				if (ownedElement instanceof Class) {
					Class cl = (Class) ownedElement;
					FMClass fmClass = getClassData(cl, packageName);
					FMModel.getInstance().getClasses().add(fmClass);

					// extraction of frontend components
					Stereotype tableS = StereotypesHelper.getAppliedStereotypeByString(cl, "TableView");
					Stereotype formS = StereotypesHelper.getAppliedStereotypeByString(cl, "StandardForm");

					if (tableS != null || formS != null) {
						FMComponent fmComponent = getComponentData(cl, packageName, tableS, formS);
						FMModel.getInstance().getComponents().add(fmComponent);
					}

				}

				if (ownedElement instanceof Enumeration) {
					Enumeration en = (Enumeration) ownedElement;
					FMEnumeration fmEnumeration = getEnumerationData(en, packageName);
					FMModel.getInstance().getEnumerations().add(fmEnumeration);
				}
			}

			/**
			 * @ToDo: Process other package elements, as needed
			 */
		}
	}

	private FMComponent getComponentData(Class cl, String packageName, Stereotype tableS, Stereotype formS) {
		FMComponent component = new FMComponent();
		component.setName(cl.getName());
		if (tableS != null) {
			FMTableView tableView = new FMTableView();
			Iterator<Property> it = ModelHelper.attributes(cl);
			while (it.hasNext()) {
				Property p = it.next();
				Stereotype propS = StereotypesHelper.getAppliedStereotypeByString(p, "ColumnComponent");
				if (propS != null) {
					String columnName = (String) getTagValue(p, propS, "columnName");
					tableView.getColumnNames().put(p.getName(), columnName);
				}

			}
			component.setTableView(tableView);
		}
		if (formS != null) {
			String name = (String) getTagValue(cl, formS, "name");
			Boolean create = (Boolean) getTagValue(cl, formS, "create");
			Boolean update = (Boolean) getTagValue(cl, formS, "update");
			Boolean delete = (Boolean) getTagValue(cl, formS, "delete");
			FMStandardForm fmStandardForm = new FMStandardForm(name, null, create, update, delete);
			// svaki property od forme treba setovati

			 component.setForm(fmStandardForm);
		}
		return component;
	}

	private FMApplication getAppDescription(Package pack) throws AnalyzeException {

		Stereotype packs = StereotypesHelper.getAppliedStereotypeByString(pack, "PackageConfiguration");
		if (packs != null) {
			String dbUrl = (String) getTagValue(pack, packs, "dbUrl");
			String dbUsername = (String) getTagValue(pack, packs, "dbUsername");
			String dbPassword = (String) getTagValue(pack, packs, "dbPassword");
			String dbType = (String) getTagValue(pack, packs, "dbType");
			String appHost = (String) getTagValue(pack, packs, "appHost");
			String appPort = (String) getTagValue(pack, packs, "appPort");
			String appName = (String) getTagValue(pack, packs, "appName");
			String appDescription = (String) getTagValue(pack, packs, "appDescription");
			String appContextPath = (String) getTagValue(pack, packs, "appContextPath");
			FMApplication application = new FMApplication();
			application.setDbUrl(dbUrl);
			application.setDbPassword(dbPassword);
			application.setDbType(dbType);
			application.setDbUsername(dbUsername);
			application.setAppHost(appHost);
			application.setAppPort(appPort);
			application.setAppName(appName);
			application.setAppDescription(appDescription);
			application.setAppContextPath(appContextPath);
			return application;
		} else {
			throw new AnalyzeException("Package must have applied PackageConfiguration stereotype!");
		}
	}

	private Object getTagValue(Element el, Stereotype s, String tagName) {
		List<?> value = StereotypesHelper.getStereotypePropertyValue(el, s, tagName);
		if (value == null)
			return null;
		if (value.size() == 0)
			return null;
		return value.get(0);
	}

	private FMClass getClassData(Class cl, String packageName) throws AnalyzeException {
		if (cl.getName() == null)
			throw new AnalyzeException("Classes must have names!");

		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString());
		Iterator<Property> it = ModelHelper.attributes(cl);
		while (it.hasNext()) {
			Property p = it.next();
			FMProperty prop = getPropertyData(p, cl);
			fmClass.addProperty(prop);
		}

		Stereotype entityStereotype = StereotypesHelper.getAppliedStereotypeByString(cl, "Entity");
		if (entityStereotype != null) {
			fmClass.setTbName((String) getTagValue(cl, entityStereotype, "tableName"));
		}

		Iterator<Operation> it_op = ModelHelper.operations(cl);
		while (it_op.hasNext()) {
			Operation op = it_op.next();
			// FMMethod method = getMethodData(op, cl);
			// fmClass.addMethod(method);
		}

		/**
		 * @ToDo: Add import declarations etc.
		 */
		return fmClass;
	}

	/*
	 * private FMProperty getMethodData(Operation op, Class cl) throws
	 * AnalyzeException { String methodName = op.getName(); if (methodName == null)
	 * throw new AnalyzeException("Properties of the class: " + cl.getName() +
	 * " must have names!"); Type attType = op.getType(); if (attType == null) throw
	 * new AnalyzeException("Property " + cl.getName() + "." + p.getName() +
	 * " must have type!");
	 * 
	 * String typeName = attType.getName(); if (typeName == null) throw new
	 * AnalyzeException("Type ot the property " + cl.getName() + "." + p.getName() +
	 * " must have name!");
	 * 
	 * int lower = p.getLower(); int upper = p.getUpper();
	 * 
	 * FMProperty prop = new FMProperty(attName, typeName,
	 * p.getVisibility().toString(), lower, upper); return prop; }
	 * 
	 */
	private FMProperty getPropertyData(Property p, Class cl) throws AnalyzeException {
		String attName = p.getName();
		if (attName == null)
			throw new AnalyzeException("Properties of the class: " + cl.getName() + " must have names!");
		Type attType = p.getType();
		if (attType == null)
			throw new AnalyzeException("Property " + cl.getName() + "." + p.getName() + " must have type!");

		String typeName = attType.getName();
		if (typeName == null)
			throw new AnalyzeException("Type ot the property " + cl.getName() + "." + p.getName() + " must have name!");

		int lower = p.getLower();
		int upper = p.getUpper();

		FMProperty prop = new FMProperty(attName, typeName, p.getVisibility().toString(), lower, upper);
		return prop;
	}

	private FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size() - 1; i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() + " must have names!");
			fmEnum.addValue(literal.getName());
		}
		return fmEnum;
	}

}
