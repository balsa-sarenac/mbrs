package myplugin.generator;

import myplugin.generator.config.AppPropertiesGenerator;
import myplugin.generator.config.PomXMLGenerator;
import myplugin.generator.frontend.ContainerGenerator;
import myplugin.generator.frontend.FormGenerator;
import myplugin.generator.frontend.IndexHTMLGenerator;
import myplugin.generator.frontend.IndexJSGenerator;
import myplugin.generator.frontend.TableViewGenerator;
import myplugin.generator.options.GeneratorOptions;

public class GeneratorFactory {

	public static StaticFilesGenerator getStaticFilesGenerator() {
		return new StaticFilesGenerator();
	}

	public static BasicGenerator getGenerator(String name, GeneratorOptions options) {
		if (name.equals(ModelGenerator.class.getSimpleName())) {
			return new ModelGenerator(options);
		} else if (name.equals(AppPropertiesGenerator.class.getSimpleName())) {
			return new AppPropertiesGenerator(options);
		} else if (name.equals(PomXMLGenerator.class.getSimpleName())) {
			return new PomXMLGenerator(options);
		} else if (name.equals(AbstractDTOGenerator.class.getSimpleName())) {
			return new AbstractDTOGenerator(options);
		} else if (name.equals(ConcreteDTOGenerator.class.getSimpleName())) {
			return new ConcreteDTOGenerator(options);
		} else if (name.equals(RepositoryGenerator.class.getSimpleName())) {
			return new RepositoryGenerator(options);
		} else if (name.equals(IndexJSGenerator.class.getSimpleName())) {
			return new IndexJSGenerator(options);
		} else if (name.equals(IndexHTMLGenerator.class.getSimpleName())) {
			return new IndexHTMLGenerator(options);
		} else if (name.equals(ContainerGenerator.class.getSimpleName())) {
			return new ContainerGenerator(options);
		} else if (name.equals(TableViewGenerator.class.getSimpleName())) {
			return new TableViewGenerator(options);
		} else if (name.equals(FormGenerator.class.getSimpleName())) {
			return new FormGenerator(options);
		} else {
			return null;
		}
	}
}
