package myplugin.generator.frontend;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.FMApplication;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMComponent;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMStandardForm;
import myplugin.generator.options.GeneratorOptions;

public class ContainerGenerator extends BasicGenerator {

	public ContainerGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		List<FMComponent> components = FMModel.getInstance().getComponents();
		FMApplication application = FMModel.getInstance().getApplication();
		for (int i = 0; i < components.size(); i++) {
			FMComponent component = components.get(i);
			String formImport = null;
			Boolean isCreate = false;
			Boolean isEdit = false;
			Boolean isDelete = false;
			if (component.getForm() != null) {
				formImport = component.getForm().getName();
				if (component.getForm() instanceof FMStandardForm) {
					FMStandardForm sf = (FMStandardForm) component.getForm();
					isCreate = sf.isCreate();
					isEdit = sf.isUpdate();
					isDelete = sf.isDelete();
				}
			}
			String tableImport = null;
			if (component.getTableView() != null) {
				tableImport = component.getName() + "TableView";
			}
			Writer out;
			Map<String, Object> context = new HashMap<String, Object>();
			try {
				out = getWriter(component.getName(), "");
				if (out != null) {
					context.clear();
					context.put("name", component.getName());
					context.put("formImport", formImport);
					context.put("tableImport", tableImport);
					context.put("appHost", application.getAppHost());
					context.put("appPort", application.getAppPort());
					context.put("appContextPath", application.getAppContextPath());
					context.put("isCreate", isCreate);
					context.put("isEdit", isEdit);
					context.put("isDelete", isDelete);
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
