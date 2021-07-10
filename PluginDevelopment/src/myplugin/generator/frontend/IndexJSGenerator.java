package myplugin.generator.frontend;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMComponent;
import myplugin.generator.fmmodel.FMForm;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMStandardForm;
import myplugin.generator.options.GeneratorOptions;

public class IndexJSGenerator extends BasicGenerator {

	public IndexJSGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		List<String> imports = new ArrayList<String>();

		List<FMComponent> components = FMModel.getInstance().getComponents();
		for (int i = 0; i < components.size(); i++) {
			FMComponent component = components.get(i);
			imports.add(component.getName());
		}

		String appDescription = FMModel.getInstance().getApplication().getAppDescription();

		Writer out;
		Map<String, Object> context = new HashMap<String, Object>();
		try {
			out = getWriter(this.getOutputFileName(), "");
			if (out != null) {
				context.clear();
				context.put("imports", imports);
				context.put("appDescription", appDescription);
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
