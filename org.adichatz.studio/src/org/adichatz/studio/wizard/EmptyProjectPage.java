/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.studio.wizard;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.widgets.FileText;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.studio.xjc.custom.ClassNameValidator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.dialogs.ProjectContentsLocationArea;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectGenerationPage.
 */
@SuppressWarnings("restriction")
public class EmptyProjectPage extends AAdiWizardPage {

	/** The project name field. */
	private Text projectNameField;

	/** The package name field. */
	private Text packageNameField;

	/** The location area. */
	private ProjectContentsLocationArea locationArea;

	/** The scenario customization field. */
	protected FileText scenarioCustomizationField;

	/** The data composite. */
	protected Composite dataComposite;

	/**
	 * Instantiates a new a project generation page.
	 * 
	 * @param pageName
	 *            the page name
	 */
	public EmptyProjectPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setPageComplete(false);
	}

	/**
	 * Returns the value of the project name field with leading and trailing spaces removed.
	 * 
	 * @return the project name in the field
	 */
	protected String getProjectNameFieldValue() {
		if (projectNameField == null) {
			return ""; //$NON-NLS-1$
		}

		return projectNameField.getText().trim();
	}

	/**
	 * Gets the location area.
	 * 
	 * @return the location area
	 */
	public ProjectContentsLocationArea getLocationArea() {
		return locationArea;
	}

	/**
	 * Creates a project resource handle for the current project name field value. The project handle is created relative to the
	 * workspace root.
	 * <p>
	 * This method does not create the project resource; this is the responsibility of <code>IProject::create</code> invoked by the
	 * new project resource wizard.
	 * </p>
	 * 
	 * @return the new project resource handle
	 */
	public IProject getProjectHandle() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
	}

	/**
	 * Returns the current project name as entered by the user, or its anticipated initial value.
	 * 
	 * @return the project name, its anticipated initial value, or <code>null</code> if no project name is known
	 */
	public String getProjectName() {
		if (projectNameField == null) {
			return "";
		}
		return getProjectNameFieldValue();
	}

	/**
	 * Gets the package name field.
	 * 
	 * @return the packageNameField
	 */
	public Text getPackageNameField() {
		return packageNameField;
	}

	/**
	 * Gets the scenario customization field.
	 *
	 * @return the scenario customization field
	 */
	public FileText getScenarioCustomizationField() {
		return scenarioCustomizationField;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			projectNameField.setFocus();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.wizard.AAdiWizardPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		locationArea = new ProjectContentsLocationArea(getErrorReporter(), composite);

		// Scale the button based on the rest of the dialog
		setButtonLayoutData(locationArea.getBrowseButton());

	}

	/**
	 * Creates the field.
	 *
	 * @param parent
	 *            the parent
	 * @param labelStr
	 *            the label str
	 * @param fieldCase
	 *            the field case
	 * @param suffix
	 *            the suffix
	 * @param buttonToolTip
	 *            the button tool tip
	 * @param specifyHelp
	 *            the specify help
	 * @param messageHelp
	 *            the message help
	 * @param validPage
	 *            the valid page
	 * @param isPackage
	 *            the is package
	 * @return the text
	 */
	protected Text createField(final Composite parent, String labelStr, final GeneratorUtil.FieldCaseEnum fieldCase,
			final String suffix, String buttonToolTip, final String specifyHelp, final String messageHelp, boolean validPage,
			final boolean isPackage) {
		final Text text = createField(parent, labelStr, buttonToolTip, specifyHelp, messageHelp, validPage);
		final ModifyListener projectML = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String fieldName = GeneratorUtil.getJavaName(projectNameField.getText(), fieldCase, isPackage);
				switch (fieldCase) {
				case UPPER_CASE_FIRST_LETTER:
					fieldName = EngineTools.upperCaseFirstLetter(fieldName);
					break;
				case LOWER_CASE_FIRST_LETTER:
					fieldName = EngineTools.lowerCaseFirstLetter(fieldName);
					break;
				default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
					break;
				}
				text.setText(fieldName + suffix);
			}
		};

		projectNameField.addModifyListener(projectML);
		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (null != projectML) {
					projectNameField.removeModifyListener(projectML);
				}
			}
		};
		text.addKeyListener(keyListener);

		return text;

	}

	/**
	 * Creates the field.
	 *
	 * @param parent
	 *            the parent
	 * @param labelStr
	 *            the label str
	 * @param buttonToolTip
	 *            the button tool tip
	 * @param specifyHelp
	 *            the specify help
	 * @param messageHelp
	 *            the message help
	 * @param validPage
	 *            the valid page
	 * @return the text
	 */
	protected Text createField(final Composite parent, String labelStr, String buttonToolTip, final String specifyHelp,
			final String messageHelp, boolean validPage) {
		if (null != labelStr) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(getFromStudioBundle(labelStr));
		}
		final Text text = new Text(parent, SWT.BORDER);

		if (validPage)
			text.addModifyListener((e) -> {
				setPageComplete(validatePage());
			});
		WizardUtil.createHelp(parent, buttonToolTip, specifyHelp, messageHelp);

		return text;

	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 * 
	 * @return <code>true</code> if all controls are valid, and <code>false</code> if at least one is invalid
	 */
	protected boolean validatePage() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		String projectFieldContents = getProjectNameFieldValue();
		if (projectFieldContents.equals("")) { //$NON-NLS-1$
			setErrorMessage(null);
			setMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectNameEmpty);
			return false;
		}

		IStatus nameStatus = workspace.validateName(projectFieldContents, IResource.PROJECT);
		if (!nameStatus.isOK()) {
			setErrorMessage(nameStatus.getMessage());
			return false;
		}

		if (getProjectHandle().exists()) {
			setErrorMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectExistsMessage);
			return false;
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectNameFieldValue());
		locationArea.setExistingProject(project);

		File file = new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString() + "/" + getProjectNameFieldValue());
		if (file.exists()) {
			setErrorMessage(getFromStudioBundle("studio.directoryAlreadyExist", file.getAbsolutePath()));
			return false;
		}

		String validLocationMessage = locationArea.checkValidLocation();
		if (validLocationMessage != null) { // there is no destination location given
			setErrorMessage(validLocationMessage);
			return false;
		}

		if (!ClassNameValidator.PACKAGE_PATTERN.matcher(packageNameField.getText()).matches()
				|| ClassNameValidator.JAVA_KEYWORDS.contains(packageNameField.getText())) {
			setErrorMessage(getFromStudioBundle("studio.newProject.invalidPackageName"));
			return false;
		}
		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	/**
	 * Creates the project name specification controls.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createContents(final Composite parent) {
		new Status(IStatus.INFO, "AdichatzStudio", getFromStudioBundle("studio.launchingProjectGeneration"));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		dataComposite = new Composite(parent, SWT.NONE);
		dataComposite.setLayoutData(gridData);
		dataComposite.setLayout(new MigLayout("wrap 3, ins 5	", "[][fill,grow][]"));

		Label projectLabel = new Label(dataComposite, SWT.NONE);
		projectLabel.setText(IDEWorkbenchMessages.WizardNewProjectCreationPage_nameLabel);

		projectNameField = new Text(dataComposite, SWT.BORDER);
		projectNameField.setLayoutData("span 2");

		Label packageLabel = new Label(dataComposite, SWT.NONE);
		packageLabel.setText(getFromStudioBundle("studio.newProject.packageName"));
		packageLabel.setLayoutData("newline");

		packageNameField = createField(dataComposite, null, FieldCaseEnum.LOWER_CASE_FIRST_LETTER, "",
				"studio.newProject.packageNameToolTip", "studio.newProject.specifyPackageName", "studio.newProject.packageNameHelp",
				true, true);

		projectNameField.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event e) {
				locationArea.updateProjectName(getProjectNameFieldValue());
				setPageComplete(validatePage());
			}
		});
	}
}