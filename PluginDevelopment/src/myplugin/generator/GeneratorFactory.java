package myplugin.generator;

import myplugin.generator.backend.AbstractControllerGenerator;
import myplugin.generator.backend.AbstractDTODetailsGenerator;
import myplugin.generator.backend.AbstractDTOGenerator;
import myplugin.generator.backend.AbstractServiceGenerator;
import myplugin.generator.backend.ConcreteDTODetailsGenerator;
import myplugin.generator.backend.ConcreteDTOGenerator;
import myplugin.generator.backend.ControllerGenerator;
import myplugin.generator.backend.ModelGenerator;
import myplugin.generator.backend.RepositoryGenerator;
import myplugin.generator.backend.ServiceGenerator;
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
		} else if (name.equals(AbstractDTODetailsGenerator.class.getSimpleName())) {
			return new AbstractDTODetailsGenerator(options);
		} else if (name.equals(ConcreteDTOGenerator.class.getSimpleName())) {
			return new ConcreteDTOGenerator(options);
		} else if (name.equals(ConcreteDTODetailsGenerator.class.getSimpleName())) {
			return new ConcreteDTODetailsGenerator(options);
		} else if (name.equals(RepositoryGenerator.class.getSimpleName())) {
			return new RepositoryGenerator(options);
		} else if (name.equals(AbstractServiceGenerator.class.getSimpleName())) {
			return new AbstractServiceGenerator(options);
		} else if (name.equals(ServiceGenerator.class.getSimpleName())) {
			return new ServiceGenerator(options);
		} else if (name.equals(AbstractControllerGenerator.class.getSimpleName())) {
			return new AbstractControllerGenerator(options);
		} else if (name.equals(ControllerGenerator.class.getSimpleName())) {
			return new ControllerGenerator(options);
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
		} else if (name.equals(FormGenerator.class.getSimpleName())) {
			return new FormGenerator(options);
		} else {
			return null;
		}
	}
}
