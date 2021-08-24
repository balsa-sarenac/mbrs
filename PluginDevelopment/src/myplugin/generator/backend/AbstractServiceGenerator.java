package myplugin.generator.backend;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMPeristentProperty;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMReferencedProperty;
import myplugin.generator.options.GeneratorOptions;

public class AbstractServiceGenerator extends BasicGenerator {

	public AbstractServiceGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		List<FMClass> classes = FMModel.getInstance().getClasses();
		for (int i = 0; i < classes.size(); i++) {
			FMClass cl = classes.get(i);
			Writer out;
			Map<String, Object> context = new HashMap<String, Object>();
			String keyName = null;
			try {
				out = getWriter(cl.getName(), cl.getTypePackage());
				if (out != null) {
					context.clear();
					context.put("name", cl.getName());
					List<FMProperty> props = new ArrayList<FMProperty>();
					List<FMProperty> peristantProps = new ArrayList<FMProperty>();
					List<FMProperty> referencedProps = new ArrayList<FMProperty>();
					for (FMProperty p : cl.getProperties()) {
						if (p instanceof FMPeristentProperty) {
							peristantProps.add(p);
							FMPeristentProperty persProp = (FMPeristentProperty) p;
							if (keyName == null && persProp.getIsKey()) {
								keyName = persProp.getName();
							}
						} else if (p instanceof FMReferencedProperty) {
							referencedProps.add(p);
						} else {
							props.add(p);
						}
					}
					context.put("properties", props);
					context.put("persistentProps", peristantProps);
					context.put("referencedProps", referencedProps);
					context.put("keyName", keyName);
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
