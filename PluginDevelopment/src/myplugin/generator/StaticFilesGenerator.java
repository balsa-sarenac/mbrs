package myplugin.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;

import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.StaticResource;

public class StaticFilesGenerator {

	public void generate() throws IOException {

		for (StaticResource staticResource : ProjectOptions.getProjectOptions().getStaticResources()) {
			String destinationFolder = staticResource.getDestinationFolder();
			String destinationFile = staticResource.getDestinationFilename();
			String destination = destinationFolder + File.separator + destinationFile;
			File df = new File(destination);

			// ako se datoteka ne prepisuje i postoji, ne radi nista
			if (!staticResource.isOverwrite() && df.exists()) {
				return;
			}

			// kreiraj folder
			if (!df.getParentFile().exists()) {
				if (!df.getParentFile().mkdirs()) {
					throw new IOException("An error occurred during output folder creation " + df.getParent());
				}
			}

			// kopiraj fajl
			String source = staticResource.getSourceFolder() + File.separator + staticResource.getSourceFilename();
			File sf = new File(source);
			Files.copy(sf.toPath(), df.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

	}
}
