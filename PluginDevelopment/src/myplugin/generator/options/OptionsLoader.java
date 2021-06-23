package myplugin.generator.options;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class OptionsLoader {

	public void saveProjectOptionsToXML(String optionsFilePath) throws IOException {
		ProjectOptions projectOptions = ProjectOptions.getProjectOptions();
		XStream xstream = new XStream(new StaxDriver());
		xstream.toXML(new SerializableProjectOptions(projectOptions), new FileWriter(optionsFilePath));
	}

	public SerializableProjectOptions loadProjectOptionsFromXML(String path, String fileName)
			throws FileNotFoundException {
		FileReader fileReader = new FileReader(path + File.separator + fileName);
		XStream xstream = new XStream(new StaxDriver());
		SerializableProjectOptions projectOptions = (SerializableProjectOptions) xstream.fromXML(fileReader);
		// podesavanje template datoteka na apsolutne putanje
		for (GeneratorOptions generatorOptions : projectOptions.getGeneratorOptions().values()) {
			generatorOptions.setTemplateDir(path + File.separator + generatorOptions.getTemplateDir());
		}
		// podesavanje static resource datoteka na apsolutne putanje
		for (StaticResource staticResource : projectOptions.getStaticResources()) {
			staticResource.setSourceFolder(path + File.separator + staticResource.getSourceFolder());
		}
		return projectOptions;
	}
}
