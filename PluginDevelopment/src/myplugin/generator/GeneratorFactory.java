package myplugin.generator;

import myplugin.generator.options.GeneratorOptions;

public class GeneratorFactory {

	public static StaticFilesGenerator getStaticFilesGenerator() {
		return new StaticFilesGenerator();
	}

	public static BasicGenerator getGenerator(String name, GeneratorOptions options) {
		if (name.equals(ModelGenerator.class.getSimpleName())) {
			return new ModelGenerator(options);
		} else {
			return null;
		}
	}
}
