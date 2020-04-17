/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 * 
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 * 
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 * 
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.engine.widgets;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class FileText.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class FileText extends Composite {

	/** The delete button. */
	protected Button deleteButton;

	/** The file chooser button. */
	protected Button fileChoosertButton;

	/** The file text. */
	private Text text;

	/** The value. */
	private String value;

	/** The filter extension. */
	private String[] filterExtension;

	/** The filter path. */
	private String filterPath;

	/**
	 * Instantiates a new file text.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public FileText(final Composite parent, final int style) {
		super(parent, SWT.BORDER | SWT.TRAIL);

		text = new Text(this, SWT.NONE);
		text.setEditable(false);

		addButtons(parent, style);

		/*
		 * Text max size is computed following to the FilText size. FileText must have a layoutdata like "w 0:n:n" which is the
		 * default value.
		 */
		setLayoutData("w 0:n:n");
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				text.setLayoutData("w 0:0:" + (getSize().x - (null == deleteButton ? 22 : 44)));
				FileText.this.layout();
			}
		});
	}

	protected void addButtons(final Composite parent, final int style) {
		fileChoosertButton = new Button(this, SWT.IMAGE_BMP);
		fileChoosertButton.setToolTipText(getFromEngineBundle("field.fileText.chooseFileToolTip"));
		fileChoosertButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String selected;
				if (0 != (style & AdiSWT.DIRECTORY)) {
					DirectoryDialog dd = new DirectoryDialog(parent.getShell(), SWT.OPEN);
					dd.setText(getFromEngineBundle("field.fileText.chooseDirectory"));
					dd.setFilterPath(filterPath);
					selected = dd.open();
				} else {
					FileDialog fd = new FileDialog(parent.getShell(), SWT.OPEN);
					fd.setText(getFromEngineBundle("field.fileText.chooseFile"));
					if (!EngineTools.isEmpty(value)) {
						fd.setFileName(value);
						// fd.setFilterPath(new File(value).getParent());
					} else
						fd.setFilterPath(filterPath);
					fd.setFilterExtensions(filterExtension);
					selected = fd.open();
				}
				if (null != selected) {
					setValue(selected);
					text.setToolTipText(value);
					notifyListeners(SWT.Modify, null);
				}
				text.pack();
				FileText.this.layout();
			}
		});

		if (0 == (style & AdiSWT.DELETE_BUTTON)) {
			setLayout(new MigLayout("wrap 2, ins 0", "[fill,grow, growprio 0][]"));
			deleteButton = null;
		} else {
			setLayout(new MigLayout("wrap 3, ins 0", "[fill,grow, growprio 0][]0[]"));
			deleteButton = new Button(this, SWT.IMAGE_GIF);
			deleteButton.setToolTipText("clear value");
			deleteButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					setValue(null);
					text.setToolTipText("");
					notifyListeners(SWT.Modify, null);
				}
			});
		}

		setImages();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Composite#setFocus()
	 */
	@Override
	public boolean setFocus() {
		return fileChoosertButton.setFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (null != deleteButton)
			deleteButton.setEnabled(enabled);
		fileChoosertButton.setEnabled(enabled);
		setImages();
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the images.
	 */
	protected void setImages() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		if (null != deleteButton) {
			deleteButton.setImage(toolkit.getRegisteredImage("IMG_DELETE"));
			deleteButton.setLayoutData("h 16!, w 16!");
		}
		fileChoosertButton.setImage(toolkit.getRegisteredImage("IMG_FILECHOOSER"));
		fileChoosertButton.setLayoutData("h 16!, w 16!");
	}

	/**
	 * Sets a new <code>File name</code> value.
	 * 
	 * @param value
	 *            new value
	 */
	public void setValue(String value) {
		this.value = value;
		text.setText((null == value) ? "" : value);
		if (null != value) {
			File file = new File(value);
			if (file.exists())
				filterPath = file.getParent();
		}
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	/**
	 * Gets the filter extension.
	 * 
	 * @return the filter extension
	 */
	public String[] getFilterExtension() {
		return filterExtension;
	}

	/**
	 * Sets the filter extension.
	 * 
	 * @param filterExtension
	 *            the new filter extension
	 */
	public void setFilterExtension(String filterExtensionsString) {
		StringTokenizer tokenizer = new StringTokenizer(filterExtensionsString, ",");
		List<String> filterExtensions = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			filterExtensions.add(tokenizer.nextToken().trim());
		}
		if (filterExtensions.isEmpty())
			this.filterExtension = null;
		else
			this.filterExtension = filterExtensions.toArray(new String[filterExtensions.size()]);
	}

	/**
	 * Sets the filter extension.
	 * 
	 * @param filterExtension
	 *            the new filter extension
	 */
	public void setFilterExtension(String[] filterExtension) {
		this.filterExtension = filterExtension;
	}

	/**
	 * Gets the filter path.
	 * 
	 * @return the filterPath
	 */
	public String getFilterPath() {
		return filterPath;
	}

	/**
	 * Sets the filter path.
	 * 
	 * @param filterPath
	 *            the filterPath to set
	 */
	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath;
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified when the receiver's text is modified, by sending it one
	 * of the messages defined in the <code>ModifyListener</code> interface.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 * @see ModifyListener
	 * @see #removeModifyListener
	 */
	public void addModifyListener(ModifyListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		TypedListener typedListener = new TypedListener(listener);
		addListener(SWT.Modify, typedListener);
	}

	/**
	 * Removes the modify listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeModifyListener(ModifyListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		removeListener(SWT.Modify, listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Control#computeSize(int, int)
	 */
	@Override
	public Point computeSize(int wHint, int hHint) {
		return super.computeSize(wHint, hHint);
	}
}
