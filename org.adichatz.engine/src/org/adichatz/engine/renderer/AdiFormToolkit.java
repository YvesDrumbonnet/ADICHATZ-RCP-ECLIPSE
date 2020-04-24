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
package org.adichatz.engine.renderer;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.widgets.AdiFormText;
import org.adichatz.engine.widgets.CompositeBag;
import org.adichatz.engine.widgets.DateText;
import org.adichatz.engine.widgets.FileText;
import org.adichatz.engine.widgets.FontDataText;
import org.adichatz.engine.widgets.GMap;
import org.adichatz.engine.widgets.MultiChoice;
import org.adichatz.engine.widgets.NumericText;
import org.adichatz.engine.widgets.RGBText;
import org.adichatz.engine.widgets.StarRating;
import org.adichatz.engine.widgets.extratext.ExtraText;
import org.adichatz.engine.widgets.imageviewer.ImageViewer;
import org.adichatz.engine.widgets.imageviewer.ImageViewer.IMAGE_TYPE;
import org.adichatz.engine.widgets.supplement.ButtonItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.miginfocom.swt.MigLayout;

public class AdiFormToolkit extends FormToolkit {
	public static String CSS_ADICHATZ_COMMON_SELECTOR = "#adichatz-common";

	private Map<String, Image> registeredImages = new HashMap<>();

	private Map<String, ImageDescriptor> registeredImageDescriptors = new HashMap<>();

	/**
	 * Creates a toolkit that is self-sufficient (will manage its own colors).
	 * <p>
	 * Clients that call this method must call {@link #dispose()} when they are finished using the toolkit.
	 * 
	 */
	public AdiFormToolkit(Display display) {
		super(new FormColors(display));
		// Caution: see FormToolkit.initializeBorderStyle(): '"5.1".compareTo(osversion) <= 0' return false for Windows 10
		setBorderStyle(SWT.BORDER);
		getColors().markShared(); // Bug, some colors could be disposed!?
	}

	public DateText createDateText(Composite parent, int style) {
		DateText dateText = new DateText(parent, style);
		dateText.getText().setForeground(getColors().getForeground());
		dateText.getText().setBackground(getColors().getBackground());
		adapt(dateText);
		return dateText;
	}

	public DateTime createDateTime(Composite parent, int style) {
		DateTime dateTime = new DateTime(parent, style);
		dateTime.setForeground(getColors().getForeground());
		dateTime.setBackground(getColors().getBackground());
		adapt(dateTime);
		return dateTime;
	}

	public FileText createFileText(Composite parent, int style) {
		FileText fileText = new FileText(parent, style);
		fileText.getText().setForeground(getColors().getForeground());
		fileText.getText().setBackground(getColors().getBackground());
		adapt(fileText);
		return fileText;
	}

	public ImageViewer createImageViewer(Composite parent, IMAGE_TYPE imageType, int style, int imageStyle, int toolBarStyle) {
		ImageViewer imageViewer = new ImageViewer(parent, imageType, style, imageStyle, toolBarStyle);
		// adapt(imageViewer);
		return imageViewer;
	}

	public GMap createGMap(Composite parent, int style, int toolBarStyle) {
		GMap gmap = new GMap(parent, style, toolBarStyle);
		adapt(gmap);
		return gmap;
	}

	/**
	 * Creates a clabel as a part of the form.
	 *
	 * @param parent
	 *            the clabel parent
	 * @param text
	 *            the clabel text
	 * @return the clabel widget
	 */
	public CLabel createCLabel(Composite parent, String text) {
		return createCLabel(parent, text, SWT.NONE);
	}

	/**
	 * Creates a clabel as a part of the form.
	 *
	 * @param parent
	 *            the clabel parent
	 * @param text
	 *            the clabel text
	 * @param style
	 *            the clabel style
	 * @return the clabel widget
	 */
	public CLabel createCLabel(Composite parent, String text, int style) {
		CLabel label = new CLabel(parent, style);
		if (text != null)
			label.setText(text);
		adapt(label, false, false);
		return label;
	}

	public Group createGroup(Composite parent, String text, int style) {
		Group group = createGroup(parent, style);
		group.setText(text);
		return group;
	}

	public Group createGroup(Composite parent, int style) {
		Group group = new Group(parent, style);
		adapt(group);
		return group;
	}

	public CCombo createCCombo(Composite parent, int style) {
		CCombo ccombo = new CCombo(parent, style);
		return ccombo;
	}

	public CompositeBag createCompositeBag(Composite parent, int style) {
		CompositeBag compositeBag = new CompositeBag(parent, style);
		compositeBag.getContent().setLayout(new MigLayout("wrap, ins 0, hidemode 3", "grow, fill", "grow,fill"));

		ScrolledComposite scrolledComposite = compositeBag.getScrolledComposite();
		compositeBag.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		adapt(scrolledComposite);

		compositeBag.getDefaultComposite().setLayout(new MigLayout("wrap, ins 0", "grow", "grow"));
		adapt(compositeBag.getDefaultComposite());

		return compositeBag;
	}

	public ExtraText createExtraText(Composite parent, int style) {
		return new ExtraText(parent, style);
	}

	public FormattedText createFormattedText(Composite parent, int style) {
		FormattedText formattedText = new FormattedText(parent, style);
		formattedText.getControl().setForeground(getColors().getForeground());
		formattedText.getControl().setBackground(getColors().getBackground());
		return formattedText;
	}

	@Override
	public AdiFormText createFormText(Composite parent, boolean trackFocus) {
		AdiFormText formText = new AdiFormText(parent, SWT.WRAP);
		formText.marginWidth = 1;
		formText.marginHeight = 0;
		formText.setHyperlinkSettings(getHyperlinkGroup());
		adapt(formText, false, true);
		formText.setMenu(parent.getMenu());
		return formText;
	}

	public FontDataText createFontDataText(Composite parent, int style) {
		FontDataText fontDataText = new FontDataText(parent, style);
		fontDataText.getText().setForeground(getColors().getForeground());
		fontDataText.getText().setBackground(getColors().getBackground());
		adapt(fontDataText);
		return fontDataText;
	}

	public NumericText createNumericText(Composite parent, int style) {
		Text text = createText(parent, null, style);
		NumericText numericText = new NumericText(text);
		return numericText;

	}

	public MultiChoice createMultiChoice(Composite parent, int style) {
		MultiChoice multiChoice = new MultiChoice(parent, style);
		adapt(multiChoice);
		return multiChoice;
	}

	public PGroup createPGroup(Composite parent, int style) {
		final PGroup pgroup = new PGroup(parent, style);
		// AVOID getToolkit().adapt(pgroup); that could generate SWTException (disposed) on MouseEvent (see toolki#adapt(...)
		pgroup.setLayout(new FillLayout());
		pgroup.setImagePosition(SWT.TRAIL);
		pgroup.setTogglePosition(SWT.LEFT);
		pgroup.setBackground(getColors().getBackground());
		pgroup.setStrategy(new AdiGroupStrategy());
		pgroup.addExpandListener(new ExpandListener() {

			@Override
			public void itemExpanded(ExpandEvent e) {
				pgroup.layout();
			}

			@Override
			public void itemCollapsed(ExpandEvent e) {
			}
		});
		return pgroup;
	}

	public PShelf createPShelf(Composite parent, int style) {
		PShelf pshelf = new PShelf(parent, style);
		AdiPShelfRenderer shelfRenderer = new AdiPShelfRenderer();
		pshelf.setRenderer(shelfRenderer);
		if (null != AReskinManager.getInstance())
			AReskinManager.getInstance().addSkinnedWidget(pshelf);
		return pshelf;
	}

	public PShelfItem createPShelfItem(PShelf parent, int style) {
		PShelfItem pshelfItem = new PShelfItem(parent, style);
		adapt(pshelfItem.getBody());
		return pshelfItem;
	}

	public RGBText createRGBText(Composite parent, int style) {
		RGBText rgbText = new RGBText(parent, style);
		rgbText.getText().setForeground(getColors().getForeground());
		rgbText.getText().setBackground(getColors().getBackground());
		adapt(rgbText);
		return rgbText;
	}

	public StarRating createStarRating(Composite parent, int style, String numericPattern) {
		StarRating starRating = new StarRating(parent, style, numericPattern);
		adapt(starRating);
		return starRating;
	}

	public Image getRegisteredImage(String imageKey) {
		Image image = registeredImages.get(imageKey);
		if (null == image) {
			image = getRegisteredImageDescriptor(imageKey).createImage();
			registeredImages.put(imageKey, image);
		}
		return image;
	}

	public ImageDescriptor getRegisteredImageDescriptor(String imageKey) {
		ImageDescriptor imageDescriptor = registeredImageDescriptors.get(imageKey);
		if (null == imageDescriptor) {
			imageDescriptor = AdichatzApplication.getInstance().getImageDescriptor(EngineConstants.ENGINE_BUNDLE,
					imageKey.concat(".png"));
			registeredImageDescriptors.put(imageKey, imageDescriptor);
		}
		return imageDescriptor;
	}

	@Override
	public Label createLabel(Composite parent, String text, int style) {
		Label label = new Label(parent, SWT.NONE);
		if (text != null)
			label.setText(text);
		adapt(label, false, false);
		return label;
	}

	/**
	 * Creates the button item.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @return the button item
	 */
	public ButtonItem createButtonItem(Composite parent, int style) {
		ButtonItem buttonItem = new ButtonItem(parent, style);
		adapt(buttonItem, true, true);
		return buttonItem;
	}
}
