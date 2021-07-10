package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMReferencedProperty;
import myplugin.generator.fmmodel.FMType;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMPeristentProperty;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;

/**
 * Model generator that now generates incomplete model classes based on MagicDraw
 * class model
 * 
 * @ToDo: enhance resources/templates/ejbclass.ftl template and intermediate
 *        data structure (@see myplugin.generator.fmmodel) in order to generate
 *        complete model classes
 */

public class ModelGenerator extends BasicGenerator {

	public ModelGenerator(GeneratorOptions generatorOptions) {
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
			try {
				out = getWriter(cl.getName(), cl.getTypePackage());
				if (out != null) {
					context.clear();
					context.put("class", cl);
					context.put("package", cl.getTypePackage());
					context.put("importedPackages", cl.getImportedPackages());
					context.put("properties", cl.getProperties());
					context.put("methods", cl.getMethods());
					List<FMProperty> props = new ArrayList<FMProperty>();
					List<FMProperty> peristantProps = new ArrayList<FMProperty>();
					List<FMProperty> referencedProps = new ArrayList<FMProperty>();
					for (FMProperty p : cl.getProperties()) {
						if (p instanceof FMPeristentProperty)
							peristantProps.add(p);
						else if (p instanceof FMReferencedProperty)
							referencedProps.add(p);
						else
							props.add(p);
					}
					context.put("properties", props);
					context.put("persistentProps", peristantProps);
					context.put("referencedProps", referencedProps);
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
