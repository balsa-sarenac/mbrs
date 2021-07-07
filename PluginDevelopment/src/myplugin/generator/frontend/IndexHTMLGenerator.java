package myplugin.generator.frontend;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;

public class IndexHTMLGenerator extends BasicGenerator {

	public IndexHTMLGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		String appName = FMModel.getInstance().getApplication().getAppName();

		Writer out;
		Map<String, Object> context = new HashMap<String, Object>();
		try {
			out = getWriter(this.getOutputFileName(), "");
			if (out != null) {
				context.clear();
				context.put("appName", appName);
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
