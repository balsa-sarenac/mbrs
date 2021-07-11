package myplugin.generator.config;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.FMApplication;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;

public class PomXMLGenerator extends BasicGenerator {

	public PomXMLGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		FMApplication application = FMModel.getInstance().getApplication();

		Writer out;
		Map<String, Object> context = new HashMap<String, Object>();
		try {
			out = getWriter(getOutputFileName(), "");
			if (out != null) {
				context.clear();
				context.put("appName", application.getAppName());
				context.put("appDescription", application.getAppDescription());
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
