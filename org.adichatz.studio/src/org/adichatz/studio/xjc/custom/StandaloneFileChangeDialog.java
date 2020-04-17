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
package org.adichatz.studio.xjc.custom;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.util.JBossStandaloneReader;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.views.console.ProcessConsole;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.forms.IFormColors;

import net.miginfocom.swt.MigLayout;

/**
 * The Class StandaloneFileChangeDialog.
 *
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class StandaloneFileChangeDialog extends ConfirmFormDialog {
	public StandaloneFileChangeDialog(Shell shell, AdiFormToolkit toolkit, JBossStandaloneReader standaloneReader) {
		super(shell, toolkit, getFromStudioBundle("scenario.jboss.installation"),
				AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_SERVERS.gif"),
				new IConfirmContent() {
					private Button stopApplicationServerBTN;
					private IProcess applicatonServerProcess;
					private JBossStandaloneReader jbossStandaloneReader;

					@Override
					public void createConfirmContent(Composite parent, AdiFormToolkit toolkit,
							List<Control> complementControls) {
						parent.setLayout(new MigLayout("wrap 1, ins 10"));
						final Label label = toolkit.createLabel(parent,
								getFromStudioBundle("scenario.jboss.installation.changed"));
						label.setFont(JFaceResources.getBannerFont());
						label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

						jbossStandaloneReader = standaloneReader;
						File applicationServerDir = jbossStandaloneReader.getStandaloneFile().getParentFile();
						if (applicationServerDir.exists() && applicationServerDir.isDirectory()) {
							IConsoleManager conMan = ConsolePlugin.getDefault().getConsoleManager();
							for (IConsole console : conMan.getConsoles()) {
								if (console instanceof ProcessConsole) {
									IProcess process = ((ProcessConsole) console).getProcess();
									if (null != process && process.canTerminate()) {
										String cmdLine = process.getAttribute(IProcess.ATTR_CMDLINE);
										boolean stopServer = cmdLine.contains(applicationServerDir.getAbsolutePath())
												|| cmdLine.contains("-Dprogram.name=JBossTools");
										if (stopServer) {
											parent.setLayout(new MigLayout("wrap 1, ins 20", "", "10[]40[]10"));
											stopApplicationServerBTN = toolkit.createButton(parent,
													getFromStudioBundle("scenario.jboss.terminate.session"), SWT.CHECK);
											stopApplicationServerBTN.setSelection(true);
											applicatonServerProcess = process;
										}
									}
								}
							}

						}

						// TODO Auto-generated method stub

					}

					@Override
					public void okPressed(List<Control> complementControls) {
						if (null != applicatonServerProcess)
							try {
								applicatonServerProcess.terminate();
							} catch (DebugException e) {
								logError(e);
							}
						jbossStandaloneReader.writeDoc();
					}
				});
	}
}
