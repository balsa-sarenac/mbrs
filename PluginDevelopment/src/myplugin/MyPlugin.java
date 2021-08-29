package myplugin;

import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import myplugin.generator.options.OptionsLoader;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.SerializableProjectOptions;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

/** MagicDraw plugin that performes code generation */
public class MyPlugin extends com.nomagic.magicdraw.plugins.Plugin {

	String pluginDir = null;

	public void init() {
		//JOptionPane.showMessageDialog(null, "Spring+React plugin init");

		pluginDir = getDescriptor().getPluginDirectory().getPath();

		// Creating submenu in the MagicDraw main menu
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getSubmenuActions()));

		/**
		 * @Todo: load project options (@see myplugin.generator.options.ProjectOptions)
		 *        from ProjectOptions.xml and take ejb generator options
		 */

		OptionsLoader optionsLoader = new OptionsLoader();

		try {
			SerializableProjectOptions projectOptions = optionsLoader.loadProjectOptionsFromXML(pluginDir,
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

	}

	private NMAction[] getSubmenuActions() {
		return new NMAction[] { new GenerateAction("Generate"), };
	}

	public boolean close() {
		return true;
	}

	public boolean isSupported() {
		return true;
	}
}
