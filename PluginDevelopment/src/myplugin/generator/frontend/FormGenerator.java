package myplugin.generator.frontend;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.ComponentShowTypeEnum;
import myplugin.generator.fmmodel.ComponentTypeEnum;
import myplugin.generator.fmmodel.FMComponent;
import myplugin.generator.fmmodel.FMForm;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMStandardForm;
import myplugin.generator.fmmodel.FMUIComponent;
import myplugin.generator.options.GeneratorOptions;

public class FormGenerator extends BasicGenerator {

	public FormGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public String getElement(ComponentTypeEnum cte) {
		switch (cte) {
		case textArea:
		case textBox:
			return "Input";
		case number:
			return "InputNumber";
		case checkBox:
			return "Checkbox";
		case dateTime:
		case date:
			return "DatePicker";
		case time:
			return "TimePicker";
		case radioButton:
			return "Radio";
		case comboBox:
			return "Select";
		default:
			return null;
		}
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		List<FMComponent> components = FMModel.getInstance().getComponents();
		for (int i = 0; i < components.size(); i++) {
			FMComponent component = components.get(i);
			FMForm form = component.getForm();
			if (form != null) {
				Writer out;
				Map<String, Object> context = new HashMap<String, Object>();
				try {
					out = getWriter(component.getName(), "");
					List<FMUIComponent> formElements = new ArrayList<FMUIComponent>();
					List<FMUIComponent> formZoomElements = new ArrayList<FMUIComponent>();
					if (out != null) {
						context.clear();
						context.put("name", component.getName());
						if (form instanceof FMStandardForm) {
							FMStandardForm sf = (FMStandardForm) form;
							Set<String> imports = new HashSet<String>();
							for (FMUIComponent comp : sf.getComponents()) {
								imports.add(this.getElement(comp.getComponentTypeEnum()));
								if (comp.getComponentShowTypeEnum() == ComponentShowTypeEnum.EDITABLE
										|| comp.getComponentShowTypeEnum() == ComponentShowTypeEnum.CALCULATED) {
									formElements.add(comp);
								} else {
									formZoomElements.add(comp);
								}
							}
							context.put("standardForm", (FMStandardForm) form);
							context.put("formElements", formElements);
							context.put("formZoomElements", formZoomElements);
							context.put("imports", imports);
						}
						getTemplate().process(context, out);
						out.flush();
					}
				} catch (TemplateException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
	}

}
