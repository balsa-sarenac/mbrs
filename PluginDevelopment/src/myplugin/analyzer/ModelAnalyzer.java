package myplugin.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Operation;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.impl.EnumerationLiteralImpl;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import myplugin.generator.fmmodel.ComponentShowTypeEnum;
import myplugin.generator.fmmodel.ComponentTypeEnum;
import myplugin.generator.fmmodel.FMApplication;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMComponent;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMStandardForm;
import myplugin.generator.fmmodel.FMTableColumn;
import myplugin.generator.fmmodel.FMTableView;
import myplugin.generator.fmmodel.FMType;
import myplugin.generator.fmmodel.FMUIComponent;
import myplugin.generator.options.TypeMapping;

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
			List<Property> props = (List<Property>) cl.getAttribute();
			for (Property p : props) {
				Stereotype propS = StereotypesHelper.getAppliedStereotypeByString(p, "ColumnComponent");
				if (propS != null) {
					String columnName = (String) getTagValue(p, propS, "columnName");
					FMTableColumn tableColumn = new FMTableColumn();
					tableColumn.setColumnName(columnName);
					tableColumn.setSourceName(p.getName());
					tableColumn.setUpper(p.getUpper());
					String typeName = p.getType().getName();
					String typePackage = "";
					Boolean baseType = false;
					TypeMapping typeMapping = PropertyAnalyzer.getDataType(typeName);
					if (typeMapping == null) {
						FMType type = new FMType(typeName, typePackage, baseType);
						tableColumn.setType(type);
					}
					tableView.getColumns().add(tableColumn);
				}

			}
			component.setTableView(tableView);
		}
		if (formS != null) {
			String name = (String) getTagValue(cl, formS, "name");
			Boolean create = (Boolean) getTagValue(cl, formS, "create");
			Boolean update = (Boolean) getTagValue(cl, formS, "update");
			Boolean delete = (Boolean) getTagValue(cl, formS, "delete");
			// svaki property od forme treba setovati
			List<FMUIComponent> components = new ArrayList<FMUIComponent>();
			Iterator<Property> it = ModelHelper.attributes(cl);
			while (it.hasNext()) {
				Property p = it.next();
				int upper = p.getUpper();
				ComponentShowTypeEnum componentShowTypeEnum = null;
				Stereotype propS = StereotypesHelper.getAppliedStereotypeByString(p, "Editable");
				if (propS != null) {
					componentShowTypeEnum = ComponentShowTypeEnum.EDITABLE;
				}
				if (propS == null) {
					propS = StereotypesHelper.getAppliedStereotypeByString(p, "Calculated");
					componentShowTypeEnum = ComponentShowTypeEnum.CALCULATED;
				}
				if (propS == null) {
					propS = StereotypesHelper.getAppliedStereotypeByString(p, "Zoom");
					componentShowTypeEnum = ComponentShowTypeEnum.ZOOM;
				}
				if (propS == null) {
					propS = StereotypesHelper.getAppliedStereotypeByString(p, "Next");
					componentShowTypeEnum = ComponentShowTypeEnum.NEXT;
				}
				if (propS == null) {
					propS = StereotypesHelper.getAppliedStereotypeByString(p, "Hierarchy");
					componentShowTypeEnum = ComponentShowTypeEnum.HIERARCHY;
				}
				Stereotype persP = StereotypesHelper.getAppliedStereotypeByString(p, "PersistentProperty");
				if (propS != null) {
					FMUIComponent com = new FMUIComponent();
					String typeName = p.getType().getName();
					String typePackage = "";
					Boolean baseType = false;
					TypeMapping typeMapping = PropertyAnalyzer.getDataType(typeName);
					if (typeMapping == null) {
						FMType type = new FMType(typeName, typePackage, baseType);
						com.setType(type);
					} else {
						FMType type = new FMType(typeMapping.getDestType(), typePackage, baseType);
						com.setType(type);
					}
					String label = (String) getTagValue(p, propS, "label");
					Boolean visible = (Boolean) getTagValue(p, propS, "visible");
					EnumerationLiteralImpl enumtemp = (EnumerationLiteralImpl) getTagValue(p, propS, "componentType");
					Boolean isKey = null;
					if (persP != null) {
						isKey = (Boolean) getTagValue(p, persP, "isKey");
					}
					ComponentTypeEnum cte = ComponentTypeEnum.valueOf(enumtemp.getName());
					com.setIdName(p.getName());
					com.setLabel(label);
					com.setEditable(true);
					com.setVisible(visible);
					com.setComponentTypeEnum(cte);
					com.setComponentShowTypeEnum(componentShowTypeEnum);
					com.setIsKey(isKey);
					com.setUpper(upper);
					components.add(com);
				}

			}
			FMStandardForm fmStandardForm = new FMStandardForm(name, components, create, update, delete);
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
			String dbDriverClassName = (String) getTagValue(pack, packs, "dbDriverClassName");
			String dbInitMode = (String) getTagValue(pack, packs, "dbInitMode");
			String dbDDLAuto = (String) getTagValue(pack, packs, "dbDDLAuto");
			String dbDialect = (String) getTagValue(pack, packs, "dbDialect");
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
			application.setDbDriverClassName(dbDriverClassName);
			application.setDbInitMode(dbInitMode);
			application.setDbDDLAuto(dbDDLAuto);
			application.setDbDialect(dbDialect);
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
		for (Property p : cl.getAttribute()) {
			FMProperty prop = PropertyAnalyzer.createProperty(p);
			fmClass.addProperty(prop);
		}
		for (FMType imprt : uniqueTypesUsed(fmClass.getProperties())) {
			fmClass.addImportedPackage(imprt);
		}

		Stereotype entityStereotype = StereotypesHelper.getAppliedStereotypeByString(cl, "Entity");
		if (entityStereotype != null) {
			fmClass.setTbName((String) getTagValue(cl, entityStereotype, "tableName"));
		}

		// if we want to support operations
//		Iterator<Operation> it_op = ModelHelper.operations(cl);
//		while (it_op.hasNext()) {
//			Operation op = it_op.next();
//			 FMMethod method = getMethodData(op, cl);
//			 fmClass.addMethod(method);
//		}

		/**
		 * @ToDo: Add import declarations etc.
		 */
		return fmClass;
	}

	public Collection<FMType> uniqueTypesUsed(List<FMProperty> properties) {
		Map<String, FMType> uniqueTypes = new HashMap<String, FMType>();
		for (FMProperty property : properties) {
			FMType type = property.getType();
			uniqueTypes.put(type.getName(), type);
		}
		return uniqueTypes.values();
	}

	private FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size() - 1; i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() + " must have names!");
			fmEnum.getValues().add(literal.getName());
		}
		return fmEnum;
	}

}
