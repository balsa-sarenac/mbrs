package testTemplates;

import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JOptionPane;

import myplugin.generator.ModelGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMMethod;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMParameter;
import myplugin.generator.fmmodel.ParametarTypeEnum;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.OptionsLoader;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.SerializableProjectOptions;

/** TestPackageGeneration: Class for package generation testing
 * @ToDo: Create another test class that loads metadata saved by MagicDraw plugin 
 * ( @see myplugin.GenerateAction#exportToXml() ) and activate code generation. 
 * This is the way to perform code generation testing without
 *  need to restart MagicDraw 
 *  */

public class TestPackageGeneration {
	
	public TestPackageGeneration(){
		
	}
	
	private void initModel() {		
		
		List<FMClass> classes = FMModel.getInstance().getClasses();
		
		classes.clear();
		
		FMClass cl = new FMClass ("Preduzece", "ejb.orgsema", "public");
		cl.addProperty(new FMProperty("sifraPreduzeca", "String", "private", 1, 1));
		cl.addProperty(new FMProperty("nazivPreduzeca", "String", "private", 1, 1));
		cl.addProperty(new FMProperty("imenaRadnika", "String", "private", 1, -1));
		cl.addMethod(new FMMethod("MName", "private"));
		FMMethod method = new FMMethod("MPName", "public");
		method.addParameter(new FMParameter("par", "String", ParametarTypeEnum.IN, 1, 1));
		method.addParameter(new FMParameter("par", "String", ParametarTypeEnum.OUT, 1, 1));
		cl.addMethod(method);
		
		classes.add(cl);
		
		cl = new FMClass ("Materijal", "ejb.magacin", "public");
		cl.addProperty(new FMProperty("sifraMaterijala", "String", "private", 1, 1));
		cl.addProperty(new FMProperty("nazivMaterijala", "String", "private", 1, 1));
		cl.addProperty(new FMProperty("slozen", "Boolean", "private", 1, 1));
		cl.addMethod(new FMMethod("MName", "private"));
		cl.addMethod(new FMMethod("MPName", "public"));
		
		classes.add(cl);
		
		cl = new FMClass ("Odeljenje", "ejb.orgsema", "public");
		cl.addProperty(new FMProperty("sifra", "String", "private", 1, 1));
		cl.addProperty(new FMProperty("naziv", "String", "private", 1, 1));
		cl.addMethod(new FMMethod("MName", "private"));
		cl.addMethod(new FMMethod("MPName", "public"));
		
		classes.add(cl);
		
		cl = new FMClass ("Osoba", "ejb", "public");
		cl.addProperty(new FMProperty("prezime", "String", "private", 1, 1));		
		cl.addProperty(new FMProperty("ime", "String", "private", 1, 1));
		cl.addProperty(new FMProperty("datumRodjenja", "Date", "private", 0, 1));
		cl.addProperty(new FMProperty("clanoviPorodice", "Osoba", "private", 0, -1));	
		cl.addProperty(new FMProperty("vestina", "String", "private", 1, 3));
		cl.addMethod(new FMMethod("MName", "private"));
		cl.addMethod(new FMMethod("MPName", "public"));
		
		classes.add(cl);
		
		cl = new FMClass ("Kartica", "ejb.magacin.kartica", "public");
		cl.addProperty(new FMProperty("sifraKartice", "String", "private", 1, 1));
		cl.addProperty(new FMProperty("nazivKartice", "String", "private", 1, 1));
		cl.addMethod(new FMMethod("MName", "private"));
		cl.addMethod(new FMMethod("MPName", "public"));
		
		classes.add(cl);		
	}
	
	public void testGenerator() {
		initModel();		
		GeneratorOptions go = ProjectOptions.getProjectOptions().getGeneratorOptions().get("Model");	
		ModelGenerator g = new ModelGenerator(go);
		g.generate();
	}
	
	public static void main(String[] args) {
		TestPackageGeneration tg = new TestPackageGeneration();
		/** @Todo: load project options from xml file */
		OptionsLoader optionsLoader = new OptionsLoader();

		try {
			SerializableProjectOptions projectOptions = optionsLoader.loadProjectOptionsFromXML("C:\\Users\\Balsa\\Documents\\Projects\\MBRS\\PluginDevelopment\\resources",
					"ProjectOptions.xml");
			ProjectOptions.getProjectOptions().setPath(projectOptions.getPath());
			ProjectOptions.getProjectOptions().setGeneratorOptions(projectOptions.getGeneratorOptions());
			ProjectOptions.getProjectOptions().setTypeMappings(projectOptions.getTypeMappings());
			ProjectOptions.getProjectOptions().setStaticResources(projectOptions.getStaticResources());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Loading Spring+React plugin options failed.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getStackTrace());
		}

		tg.testGenerator();
	}
	
	
	
	
}
