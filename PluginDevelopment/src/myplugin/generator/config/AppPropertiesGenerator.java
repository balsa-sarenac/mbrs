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

public class AppPropertiesGenerator extends BasicGenerator {

	public AppPropertiesGenerator(GeneratorOptions generatorOptions) {
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
				context.put("dbUrl", application.getDbUrl());
				context.put("dbUsername", application.getDbUsername());
				context.put("dbPassword", application.getDbPassword());
				context.put("dbType", application.getDbType());
				context.put("appHost", application.getAppHost());
				context.put("appPort", application.getAppPort());
				context.put("appName", application.getAppName());
				context.put("appContextPath", application.getAppContextPath());
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
