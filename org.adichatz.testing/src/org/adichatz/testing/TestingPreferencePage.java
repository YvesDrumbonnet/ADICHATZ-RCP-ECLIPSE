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
package org.adichatz.testing;

import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import java.text.SimpleDateFormat;
import java.util.function.BooleanSupplier;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.FiltersMatcher;
import org.adichatz.engine.e4.preference.APreferencePage;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.e4.preference.BooleanFieldEditor;
import org.adichatz.engine.e4.preference.TextFieldEditor;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolPreferencePage.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class TestingPreferencePage extends APreferencePage {
	private BooleanFieldEditor openManagerPartOnfailBFE;

	private BooleanFieldEditor openManagerPartBFE;

	/**
	 * Instantiates a new tool preference page.
	 * 
	 * @param preferenceManager
	 *            the preference manager
	 * @param eclipsePreferences
	 *            the eclipse preferences
	 * @param parent
	 *            the parent
	 * @param treeItem
	 *            the tree item
	 */
	public TestingPreferencePage(AdiPreferenceManager preferenceManager, IEclipsePreferences eclipsePreferences, Composite parent,
			TreeViewer treeViewer) {
		super(preferenceManager, eclipsePreferences, parent, treeViewer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.preference.APreferencePage#createPropertiesContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPropertiesContent(Composite parent) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		Group testingGroup = toolkit.createGroup(parent, getFromTestingBundle("testing.preference.group"), SWT.NONE);
		testingGroup.setLayout(new MigLayout("wrap 1", "[grow, fill, al right]"));
		testingGroup.setFont(JFaceResources.getBannerFont());

		openManagerPartBFE = new BooleanFieldEditor(TestingTools.OPEN_MANAGER_PART, false, this,
				getFromTestingBundle("testing.preference.open.manager"), testingGroup);
		addField(openManagerPartBFE);
		openManagerPartOnfailBFE = new BooleanFieldEditor(TestingTools.OPEN_MANAGER_PART_ON_FAIL, true, this,
				getFromTestingBundle("testing.preference.open.manager.on.fail"), testingGroup);
		addField(openManagerPartOnfailBFE);
		openManagerPartBFE.getControl().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openManagerPartOnfailBFE.getControl().setEnabled(!((Button) e.widget).getSelection());
			}
		});
		openManagerPartOnfailBFE.getControl().setEnabled(!eclipsePreferences.getBoolean(TestingTools.OPEN_MANAGER_PART, false));
		Composite textComposite = toolkit.createComposite(testingGroup);
		textComposite.setLayout(new MigLayout("wrap 2, ins 0", "[fill, al right][grow,fill]"));
		String defaultPattern = InstanceScope.INSTANCE.getNode(TestingTools.TESTING_BUNDLE).get(TestingTools.DATE_PATTERN, "");
		final TextFieldEditor datePatternTFE = new TextFieldEditor(TestingTools.DATE_PATTERN, defaultPattern, this,
				getFromTestingBundle("testing.preference.date.pattern"), textComposite);
		evaluators.add(new BooleanSupplier() {
			@Override
			public boolean getAsBoolean() {
				try {
					new SimpleDateFormat(datePatternTFE.getValue());
					return true;
				} catch (IllegalArgumentException e) {
					addMessage(getFromTestingBundle("testing.preference.invalid.date.pattern"));
					return false;
				}
			}
		});
		addField(datePatternTFE);

		final TextFieldEditor navigatorFilterTFE = new TextFieldEditor(TestingTools.NAVIGATOR_FILTERS,
				InstanceScope.INSTANCE.getNode(TestingTools.TESTING_BUNDLE).get(TestingTools.NAVIGATOR_FILTERS, ""), this,
				getFromTestingBundle("testing.preference.navigator.filters"), textComposite);
		navigatorFilterTFE.getControl().setToolTipText(getFromTestingBundle("testing.preference.navigator.filters.tooltip"));
		evaluators.add(new BooleanSupplier() {
			@Override
			public boolean getAsBoolean() {
				String invalidFilter = new FiltersMatcher(navigatorFilterTFE.getValue()).checkFilters();
				if (null == invalidFilter)
					return true;
				addMessage(getFromTestingBundle("testing.preference.invalid.filter", invalidFilter));
				return false;
			}
		});
		addField(navigatorFilterTFE);
	}

	@Override
	protected void initValues() {
		super.initValues();
		openManagerPartOnfailBFE.getControl().setEnabled(!openManagerPartBFE.getControl().getSelection());
	}
}
