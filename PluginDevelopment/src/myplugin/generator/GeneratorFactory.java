package myplugin.generator;

import myplugin.generator.frontend.IndexJSGenerator;
import myplugin.generator.options.GeneratorOptions;

public class GeneratorFactory {

	public static StaticFilesGenerator getStaticFilesGenerator() {
		return new StaticFilesGenerator();
	}

	public static BasicGenerator getGenerator(String name, GeneratorOptions options) {
		if (name.equals(EJBGenerator.class.getSimpleName())) {
			return new EJBGenerator(options);
		} else if (name.equals(IndexJSGenerator.class.getSimpleName())) {
			return new IndexJSGenerator(options);
		} else {
			return null;
		}
	}
}
