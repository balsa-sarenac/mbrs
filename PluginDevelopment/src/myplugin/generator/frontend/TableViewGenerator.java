package myplugin.generator.frontend;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.FMComponent;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMTableView;
import myplugin.generator.options.GeneratorOptions;

public class TableViewGenerator extends BasicGenerator {

	public TableViewGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
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
			FMTableView tableView = component.getTableView();
			if(tableView!=null) {
				Writer out;
				Map<String, Object> context = new HashMap<String, Object>();
				try {
					out = getWriter(component.getName(), "");
					if (out != null) {
						context.clear();
						context.put("name", component.getName()+"TableView");
						context.put("columns", tableView.getColumnNames());
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
