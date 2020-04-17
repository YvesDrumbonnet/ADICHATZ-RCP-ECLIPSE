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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.encoding.Base64;
import org.adichatz.common.encoding.ClassLoaderObjectInputStream;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IRankedController;
import org.adichatz.engine.extra.AdiMessageDialog;
import org.adichatz.engine.xjc.ObjectFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class EngineTools.
 * 
 * This class contains static method for specifics utilities.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class EngineTools {

	/** The unmarshaller. */
	private static Unmarshaller unmarshaller;

	/** The checked image. */
	private static Image CHECKED_IMAGE;

	/** The unchecked image. */
	private static Image UNCHECKED_IMAGE;

	/** The engine rbm. */
	private static AdiResourceBundle engineRBM;

	/**
	 * Gets the engine RBM.
	 *
	 * @return the engine RBM
	 */
	private static AdiResourceBundle getEngineRBM() {
		if (null == engineRBM) {
			engineRBM = AdichatzApplication.getPluginResources(EngineConstants.ENGINE_BUNDLE).getResourceBundleManager()
					.getResourceBundle("adichatzEngine");
		}
		return engineRBM;
	}

	/**
	 * Upper case first letter.
	 * 
	 * @param s the string
	 * 
	 * @return the string
	 */
	public static String upperCaseFirstLetter(String s) {
		if (isEmpty(s))
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * Lower case first letter.
	 * 
	 * @param s the string
	 * 
	 * @return the string
	 */
	public static String lowerCaseFirstLetter(String s) {
		if (isEmpty(s))
			return s;
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * Checks if is empty.
	 * 
	 * @param s the string
	 * 
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}

	/**
	 * Gets the value from engine bundle.
	 * 
	 * @param key       the key
	 * @param variables the variables
	 * @return the from bundle
	 */
	public static String getFromEngineBundle(String key, Object... variables) {
		return getEngineRBM().getValueFromBundle(key, variables);
	}

	/**
	 * Gets the message.
	 * 
	 * Message starts with "#MSG(" or "#MESSAGE(" retrieves message from bundle
	 * following syntaxes: <code>bundleFileURI, key, parameters</code>
	 *
	 * @param str the str
	 * @return the message
	 */
	public static String getMessage(String str) {
		if (null == str || !str.endsWith(")") || (!str.startsWith("#MSG(") && !str.startsWith("#MESSAGE(")))
			return str;
		int start = str.startsWith("#MSG(") ? 5 : 9;
		String parameters = str.substring(start, str.length() - 1);
		StringTokenizer tokenizer = new StringTokenizer(parameters, ",");
		int count = tokenizer.countTokens();
		if (count < 2) {
			logWarning("");
			return str;
		}
		String key1 = tokenizer.nextToken().trim();
		String key2 = tokenizer.nextToken().trim();
		String bundleName;
		AdiPluginResources pluginResources;
		if (key1.startsWith("adi://")) {
			String[] instanceKeys = EngineTools.getInstanceKeys(key1);
			pluginResources = AdichatzApplication.getPluginResources(instanceKeys[0]);
			bundleName = instanceKeys[2];
		} else {
			pluginResources = AdichatzApplication.getInstance().getApplicationPluginResources();
			bundleName = key1;
		}
		Object[] keys = new Object[count - 2];
		int i = 0;
		while (tokenizer.hasMoreElements())
			keys[i++] = tokenizer.nextToken().trim();
		return pluginResources.getMessage(bundleName, key2, keys);
	}

	/**
	 * Gets the checked image.
	 *
	 * @return the checked image
	 */
	public static Image getCheckedImage() {
		if (null == CHECKED_IMAGE)
			CHECKED_IMAGE = AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_CHECKED");
		return CHECKED_IMAGE;
	}

	/**
	 * Gets the unchecked image.
	 *
	 * @return the unchecked image
	 */
	public static Image getUncheckedImage() {
		if (null == UNCHECKED_IMAGE)
			UNCHECKED_IMAGE = AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_UNCHECKED");
		return UNCHECKED_IMAGE;
	}

	/**
	 * Gets the suffix.
	 * 
	 * @param bean      the bean
	 * @param suffixMap the suffix map
	 * 
	 * @return the suffix
	 */
	public static String getSuffix(String bean, Map<String, Integer> suffixMap) {
		String suffix = bean.substring(0, 1).toLowerCase();
		if (!suffixMap.containsKey(suffix)) {
			suffixMap.put(suffix, 1);
			return suffix;
		} else {
			Integer number = suffixMap.get(suffix) + 1;
			suffixMap.put(suffix, number);
			return suffix + number.toString();
		}
	}

	/**
	 * Gets the unmarshaller.
	 * 
	 * @return the unmarshaller
	 */
	public static Unmarshaller getUnmarshaller() {
		if (null == unmarshaller) {
			try {
				// new jaxb context for ENGINE object factory:
				// org.adichatz.engine.xjc.ObjectFactory
				JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
				unmarshaller = jc.createUnmarshaller();
			} catch (JAXBException e) {
				logError(e);
			}
		}
		return unmarshaller;
	}

	/**
	 * Nvl.
	 * 
	 * @param <T>          the
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the t
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return null == value ? defaultValue : value;
	}

	/**
	 * Open confirm.
	 * 
	 * @param message the message
	 * @return true, if successful
	 */
	public static boolean openConfirm(String message) {
		return openDialog(MessageDialog.CONFIRM, getFromEngineBundle("dialog.confirm.title"), message);
	}

	/**
	 * Open dialog.
	 *
	 * @param kindle   the kindle
	 * @param messages the messages
	 * @return true, if successful
	 */
	public static boolean openDialog(int kindle, String... messages) {
		return openDialog(Display.getCurrent(), kindle, messages);
	}

	/**
	 * Open dialog.
	 *
	 * @param display  the display
	 * @param kindle   the kindle
	 * @param messages the messages
	 * @return true, if successful
	 */
	public static boolean openDialog(Display display, int kindle, String... messages) {
		Image image = null;
		switch (kindle) {
		case MessageDialog.ERROR:
			image = display.getSystemImage(SWT.ICON_ERROR);
			break;
		case MessageDialog.INFORMATION:
			image = display.getSystemImage(SWT.ICON_INFORMATION);
			break;
		case MessageDialog.CONFIRM:
			image = display.getSystemImage(SWT.ICON_QUESTION);
			break;
		case MessageDialog.WARNING:
			image = display.getSystemImage(SWT.ICON_WARNING);
			break;
		default:
		}
		return openDialog(display, kindle, image, image, messages);
	}

	/**
	 * Open dialog.
	 *
	 * @param display    the display
	 * @param kindle     the kindle
	 * @param image      the image
	 * @param labelImage the label image
	 * @param messages   the messages
	 * @return true, if successful
	 */
	public static boolean openDialog(Display display, int kindle, Image image, Image labelImage, String... messages) {
		AdiMessageDialog dialog = new AdiMessageDialog(display, kindle, labelImage, messages);
		if (null != image)
			dialog.getShell().setImage(image);
		return 0 == dialog.open();
	}

	/**
	 * Checks if is xml type.
	 * 
	 * @param clazz the clazz
	 * 
	 * @return true, if class has xmlType annotation and class does not have xmlEnum
	 *         annotation
	 */
	public static boolean isXmlType(Class<?> clazz) {
		boolean xmlType = false;
		for (Annotation annotation : clazz.getAnnotations()) {
			if (annotation.annotationType().equals(XmlType.class))
				xmlType = true;
			if (annotation.annotationType().equals(XmlEnum.class))
				return false;
		}
		return xmlType;
	}

	/**
	 * Gets the child class of element class. the result must be the class generated
	 * by XJC and corresponding to the XML element.
	 * 
	 * @param element the element
	 * 
	 * @return the child element class
	 */
	public static Class<?> getChildXjcClass(Object element) {
		if (null == element)
			return null;
		return getChildXjcClass(element.getClass());
	}

	/**
	 * Gets the child xjc class.
	 *
	 * @param clazz the clazz
	 * @return the child xjc class
	 */
	public static Class<?> getChildXjcClass(Class<?> clazz) {
		Class<?> elementClass = clazz;

		while (elementClass != Object.class) {
			if (isXmlType(elementClass))
				return elementClass;
			elementClass = elementClass.getSuperclass();
		}
		return elementClass;
	}

	/**
	 * Gets the normalized class name.
	 *
	 * @param controller the controller
	 * @return the normalized class name
	 */
	public static String getNormalizedClassName(Object controller) {
		String className = controller.getClass().getName();
		if (-1 != className.indexOf('$'))
			className = controller.getClass().getSuperclass().getName();
		return className;

	}

	/**
	 * Creates a new Scenario object.
	 * 
	 * @param file           the file
	 * @param treeWrapper    the tree wrapper
	 * @param schemaLocation the schema location
	 */
	public static void createXmlFile(File file, Object treeWrapper, String schemaLocation) {

		try {
			FileOutputStream xmlFileFOS = new FileOutputStream(file);
			createXmlFile(xmlFileFOS, treeWrapper, schemaLocation);
			xmlFileFOS.close();
		} catch (IOException e) {
			logError(e);
		}
	}

	/**
	 * Creates the xml file.
	 *
	 * @param outputStream   the output stream
	 * @param treeWrapper    the tree wrapper
	 * @param schemaLocation the schema location
	 */
	public static void createXmlFile(OutputStream outputStream, Object treeWrapper, String schemaLocation) {

		try {
			JAXBContext context = JAXBContext.newInstance(EngineTools.getChildXjcClass(treeWrapper));

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (null != schemaLocation)
				m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, schemaLocation);
			m.marshal(treeWrapper, outputStream);
		} catch (JAXBException e) {
			logError(e);
		}
	}

	/**
	 * Creates the xml file.
	 * 
	 * @param xmlFileFOS     the xml file fos
	 * @param treeWrapper    the tree wrapper
	 * @param schemaLocation the schema location
	 */
	public static void createXmlFile(FileOutputStream xmlFileFOS, Object treeWrapper, String schemaLocation) {
		try {
			JAXBContext context = JAXBContext.newInstance(EngineTools.getChildXjcClass(treeWrapper));

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (null != schemaLocation)
				m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, schemaLocation);
			m.marshal(treeWrapper, xmlFileFOS);
			xmlFileFOS.close();
		} catch (IOException | JAXBException e) {
			logError(e);
		}
	}

	/**
	 * Reinit miglayout.
	 * 
	 * @param parent          the parent
	 * @param layoutCallBacks the layout call backs
	 * @return the mig layout
	 */
	public static MigLayout reinitMiglayout(Composite parent, LayoutCallback... layoutCallBacks) {
		if (null != parent.getLayout() && parent.getLayout() instanceof MigLayout) {
			MigLayout workLayout = (MigLayout) parent.getLayout();
			MigLayout migLayout = new MigLayout((String) workLayout.getLayoutConstraints(),
					(String) workLayout.getColumnConstraints(), (String) workLayout.getRowConstraints());
			for (LayoutCallback layoutCallback : layoutCallBacks)
				migLayout.addLayoutCallback(layoutCallback);
			if (parent instanceof SharedScrolledComposite)
				((SharedScrolledComposite) parent).reflow(true);
			else
				parent.setLayout(migLayout);
			return migLayout;
		}
		return null;
	}

	/**
	 * Clone serializable.
	 * 
	 * @param object the object
	 * @return the object
	 */
	public static Object cloneSerializable(Object object) {
		if (null == object)
			return null;
		Object clone = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		if (null != object) {
			try {
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				byte[] buf = baos.toByteArray();
				oos.close();
				baos.close();

				ByteArrayInputStream bais = new ByteArrayInputStream(buf);
				ObjectInputStream ois = new ClassLoaderObjectInputStream(object.getClass().getClassLoader(), bais);
				clone = ois.readObject();
				ois.close();
				bais.close();
			} catch (IOException | ClassNotFoundException e) {
				logError(e);
			}
		}
		return clone;
	}

	/**
	 * Gets the instance keys.<br>
	 * Expected formats are <adi://pluginName/subPackage/treeId> or
	 * <adi:///subPackage/treeId>.
	 *
	 * @param adiResourceURI the adi resource uri
	 * 
	 * @return the instance keys
	 */
	public static String[] getInstanceKeys(String adiResourceURI) {
		return getInstanceKeys(adiResourceURI, 3);
	}

	/**
	 * Gets the instance keys.
	 *
	 * @param adiResourceURI the adi resource uri
	 * @param members        the members
	 * @return the instance keys
	 */
	public static String[] getInstanceKeys(String adiResourceURI, int members) {
		if (adiResourceURI.startsWith("adi://"))
			adiResourceURI = adiResourceURI.substring(6);
		StringTokenizer tokenizer = new StringTokenizer(adiResourceURI, "/");
		int count = tokenizer.countTokens();
		if (count > 1 && count < members + 1) {
			String[] keys = new String[members];
			int i = members - count;
			while (tokenizer.hasMoreTokens()) {
				keys[i++] = tokenizer.nextToken().trim();
			}
			return keys;
		} else
			throw new AdichatzErrorException(
					getFromEngineBundle("adi.resource.uri.not.valid", adiResourceURI, members - 1, members));
	}

	/**
	 * Gets the entry URI to strings.
	 *
	 * @param contributionURI the contribution URI
	 * @return the entry URI to strings
	 */
	public static String[] getEntryURIToStrings(String contributionURI) {
		int index = contributionURI.indexOf('!');
		if (-1 == index)
			return getContributionURIToStrings(contributionURI);
		String[] keys = new String[3];
		Arrays.copyOf(getContributionURIToStrings(contributionURI.substring(0, index)), 2);
		keys[2] = contributionURI.substring(index + 1);
		return keys;
	}

	/**
	 * Gets the bundleclass to strings.
	 *
	 * @param contributionURI the contribution uri
	 * @return the bundleclass to strings
	 */
	public static String[] getContributionURIToStrings(String contributionURI) {
		String[] keys = new String[2];
		if (contributionURI.startsWith("bundleclass://")) {
			contributionURI = contributionURI.substring(14);
			StringTokenizer tokenizer = new StringTokenizer(contributionURI, "/");
			int count = tokenizer.countTokens();
			if (count == 2) {
				int i = 0;
				while (tokenizer.hasMoreTokens()) {
					keys[i++] = tokenizer.nextToken().trim();
				}
			} else
				throw new AdichatzErrorException(getFromEngineBundle("contribution.uri.not.valid", contributionURI));
		} else if (contributionURI.startsWith("platform:/plugin/")) {
			contributionURI = contributionURI.substring(17);
			int index = contributionURI.indexOf('/');
			if (-1 == index || contributionURI.length() <= index)
				throw new AdichatzErrorException(getFromEngineBundle("contribution.uri.not.valid", contributionURI));
			keys[0] = contributionURI.substring(0, index);
			keys[1] = contributionURI.substring(index + 1);
		} else {
			throw new AdichatzErrorException(getFromEngineBundle("contribution.uri.not.valid", contributionURI));
		}
		return keys;
	}

	/**
	 * Gets the adi resource uri.
	 * 
	 * @param keys the keys
	 * @return the adi resource uri
	 */
	public static String getAdiResourceURI(String... keys) {
		boolean first = true;
		StringBuffer instanceKeys = new StringBuffer("adi://");
		int length = keys.length;
		if (length != 2 && length != 3) {
			StringBuffer messageSB = new StringBuffer("Invalid number of keys for 'instance keys' (2 or 3 are assumed). <");
			first = true;
			for (String key : keys) {
				if (first)
					first = false;
				else
					messageSB.append(", ");
				messageSB.append(null == key ? "<null>" : key);
			}
			messageSB.append(">;");
			logError(messageSB.toString());
			return null;
		}
		int subPackageIndex = keys.length - 2;
		if (EngineTools.isEmpty(keys[subPackageIndex]))
			keys[subPackageIndex] = ".";
		for (String key : keys)
			if (null != key) {
				if (first)
					first = false;
				else
					instanceKeys.append("/");
				instanceKeys.append(key);
			}
		return instanceKeys.toString();
	}

	/**
	 * Gets the entity id.
	 * 
	 * @param entityIdMM the entity id mm
	 * @return the entity id
	 */
	public static String getEntityId(String entityIdMM) {
		return entityIdMM.substring(0, entityIdMM.length() - 2);
	}

	private static Map<MultiKey, Font> FONT_MAP = new HashMap<>();

	/**
	 * Gets the modified font data.
	 *
	 * @param font  the font
	 * @param style the style
	 * @return the modified font data
	 */
	public static Font getModifiedFont(Font font, int style) {
		if (null == font)
			return null;
		MultiKey multiKey = new MultiKey(font, style);
		Font modifiedFont = FONT_MAP.get(multiKey);
		if (null != modifiedFont && !modifiedFont.isDisposed())
			return modifiedFont;
		FontData[] fontData = font.getFontData();
		FontData[] styleData = new FontData[fontData.length];
		for (int i = 0; i < styleData.length; i++) {
			FontData base = fontData[i];
			if (style == SWT.NORMAL) {
				int fontStyle = base.getStyle();
				if ((fontStyle | SWT.ITALIC) == fontStyle)
					fontStyle = fontStyle ^ SWT.ITALIC;
				if ((fontStyle | SWT.BOLD) == fontStyle)
					fontStyle = fontStyle ^ SWT.BOLD;
				styleData[i] = new FontData(base.getName(), base.getHeight(), fontStyle);
			} else
				styleData[i] = new FontData(base.getName(), base.getHeight(), base.getStyle() | style);
		}
		modifiedFont = new Font(font.getDevice(), styleData);
		FONT_MAP.put(multiKey, modifiedFont);
		return modifiedFont;
	}

	/**
	 * Checks if is hexa color.
	 *
	 * @param hexaString the hexa string
	 * @return true, if is hexa color
	 */
	public static boolean isHexaColor(String hexaString) {
		if (hexaString.startsWith("#"))
			hexaString = hexaString.substring(1);
		int len = hexaString.length();
		if (3 != len && 6 != len)
			return false;
		return hexaString.toUpperCase().matches("[0-9A-F]+");
	}

	/**
	 * Gets the RGB from hexa.
	 *
	 * @param hexaValue the hexa value
	 * @return the RGB from hexa
	 */
	public static RGB getRGBFromHexa(Object hexaValue) {
		if (null == hexaValue)
			return null;
		String hexaString = String.valueOf(hexaValue);
		try {
			if (hexaString.startsWith("#"))
				hexaString = hexaString.substring(1);
			if (3 == hexaString.length()) {
				char[] chars = hexaString.toCharArray();
				hexaString = new String(new char[] { chars[0], chars[0], chars[1], chars[1], chars[2], chars[2] });
			}
			Integer intval = Integer.decode("0x".concat(hexaString));
			int i = intval.intValue();
			return new RGB((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
		} catch (NumberFormatException nfe) {
			logError(nfe);
		}
		return null;

	}

	/**
	 * Gets the color from hexa.
	 *
	 * @param hexaString the hexa string
	 * @return the color from hexa
	 */
	public static Color getColorFromHexa(String hexaString) {
		RGB rgb = getRGBFromHexa(hexaString);
		return null == rgb ? null : new Color(Display.getCurrent(), rgb);
	}

	public static String getHexaFromRGB(RGB rgb) {
		if (null != rgb)
			return String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue);
		return null;
	}

	/**
	 * Gets the font from string.
	 *
	 * @param fontStr the font str
	 * @return the font from string
	 */
	public static FontData getFontFromString(String fontStr) {
		if (null == fontStr)
			return null;
		int index = fontStr.lastIndexOf('(');
		int index2 = fontStr.lastIndexOf(',');
		int height = Integer.parseInt(fontStr.substring(index + 1, index2).trim());
		int style = Integer.parseInt(fontStr.substring(index2 + 1, fontStr.length() - 1).trim());
		FontData fontData = new FontData(fontStr.substring(0, index - 1), height, style);
		return fontData;
	}

	/**
	 * Gets the error string.
	 * 
	 * @param throwable the throwable
	 * @return the error string
	 */
	public static String getErrorString(Throwable throwable) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		throwable.printStackTrace(ps);
		ps.flush();
		try {
			baos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return baos.toString();
	}

	/**
	 * Center window.
	 *
	 * @param shell the shell
	 */
	public static void centerWindow(Shell shell) {
		// Center window
		Rectangle bounds = shell.getParent().getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation(x, y);

	}

	/**
	 * Gets the XML gregorian calendar.
	 *
	 * @param date the date
	 * @return the XML gregorian calendar
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		try {
			XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
			return xmlGregorianCalendar;
		} catch (DatatypeConfigurationException e) {
			logError("Invalid gregorian calendar:" + gregorianCalendar);
		}
		return null;
	}

	/**
	 * Gets the byte array.
	 *
	 * @param inputStream the input stream
	 * @return the byte array
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] getByteArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		copyStream(inputStream, buffer);
		return buffer.toByteArray();
	}

	/**
	 * Copy stream.
	 *
	 * @param in  the in
	 * @param out the out
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/**
	 * Read object.
	 *
	 * @param buffer      the buffer
	 * @param classLoader the class loader
	 * @return the object
	 */
	public static Object readObject(byte[] buffer, ClassLoader classLoader) {
		try {
			return Base64.readObject(buffer, classLoader);
		} catch (ClassNotFoundException | IOException e) {
			logError(e);
			return null;
		}
	}

	/**
	 * Write object.
	 *
	 * @param object the object
	 * @return the byte[]
	 */
	public static byte[] writeObject(Object object) {
		try {
			return Base64.writeObject(object);
		} catch (IOException e) {
			logError(e);
			return null;
		}
	}

	/**
	 * Copy to buffer.
	 *
	 * @param text the text
	 */
	public static void copyToBuffer(String text) {
		// Define a writer
		StringWriter buf = new StringWriter();
		PrintWriter writer = new PrintWriter(buf);

		// writes text
		writer.print(text);
		writer.close();

		Clipboard clipboard = new Clipboard(Display.getCurrent());
		Object[] o = new Object[] { buf.toString() };
		Transfer[] t = new Transfer[] { TextTransfer.getInstance() };
		clipboard.setContents(o, t);
		clipboard.dispose();
	}

	/**
	 * Creates the array from array object.
	 *
	 * @param o the o
	 * @return the object[]
	 */
	public static Object[] createArrayFromArrayObject(Object o) {
		if (null == o)
			return null;
		if (!o.getClass().isArray()) {
			logError(getFromEngineBundle("error.not.an.array", o, o.getClass()));
			return null;
		}

		if (!o.getClass().getComponentType().isPrimitive())
			return (Object[]) o;

		int element_count = Array.getLength(o);
		Object[] elements = new Object[element_count];
		for (int i = 0; i < element_count; i++)
			elements[i] = Array.get(o, i);

		return elements;
	}

	/**
	 * Gets the value from xml.
	 * 
	 * @param fieldType        the field type
	 * @param expression       the expression
	 * @param binaryExpression the binary expression
	 * @return the value from xml
	 */
	public static Serializable getValueFromXml(Class<?> fieldType, String expression, byte[] binaryExpression) {
		if (null != expression) {
			if (fieldType.equals(Byte.class) || fieldType.equals(byte.class))
				return new BigDecimal(expression).byteValue();
			else if (fieldType.equals(Double.class) || fieldType.equals(double.class))
				return new BigDecimal(expression).doubleValue();
			else if (fieldType.equals(Float.class) || fieldType.equals(float.class))
				return new BigDecimal(expression).floatValue();
			else if (fieldType.equals(Integer.class) || fieldType.equals(int.class))
				return new BigDecimal(expression).intValue();
			else if (fieldType.equals(Long.class) || fieldType.equals(long.class))
				return new BigDecimal(expression).longValue();
			else if (fieldType.equals(Short.class) || fieldType.equals(short.class))
				return new BigDecimal(expression).shortValue();
			else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class))
				return Boolean.valueOf(expression);
			else if (fieldType.equals(BigDecimal.class))
				return new BigDecimal(expression);
			else if (ReflectionTools.hasSuperClass(fieldType, Date.class))
				try {
					return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).parse(expression);
				} catch (ParseException e) {
					logError(e);
					return null;
				}
		} else if (null != binaryExpression) {
			return (Serializable) EngineTools.readObject(binaryExpression, fieldType.getClassLoader());
		}
		return expression;
	}

	/**
	 * Gets the xml value.
	 * 
	 * @param value the value
	 * @return the xml value
	 */
	public static Object getXmlValue(Object value) {
		if (null == value)
			return null;
		if (FieldTools.isNumericType(value.getClass()) || FieldTools.isBooleanType(value.getClass()))
			return String.valueOf(value);
		else if (value instanceof String)
			return value;
		else
			return EngineTools.writeObject(value);
	}

	/**
	 * Gets the image descriptor.
	 * 
	 * @param iconURI the icon uri
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String iconURI) {
		if (null == iconURI)
			return null;
		if (iconURI.startsWith("platform:/plugin/")) {
			int pluginKeyIndex = iconURI.indexOf("/", 17);
			int lastIndex = iconURI.lastIndexOf('/');
			String pluginKey = iconURI.substring(17, pluginKeyIndex);

			String subPackage = iconURI.substring(pluginKeyIndex, lastIndex + 1);
			if (subPackage.startsWith(EngineConstants.ICON_FILES_PATH)) {
				if (subPackage.equals(EngineConstants.ICON_FILES_PATH))
					subPackage = "";
				else
					subPackage = subPackage.substring(EngineConstants.ICON_FILES_PATH.length());
				String imageKey = subPackage.concat(iconURI.substring(lastIndex + 1));
				return AdichatzApplication.getInstance().getImageDescriptor(pluginKey, imageKey);
			}
		}
		LogBroker.displayError(getFromEngineBundle("invalid.icon.uri"), getFromEngineBundle("icon.uri.not.transformable"));
		return null;
	}

	/**
	 * Gets the image.
	 * 
	 * @param iconURI the icon uri
	 * @return the image
	 */
	public static Image getImage(String iconURI) {
		ImageDescriptor imageDescriptor = getImageDescriptor(iconURI);
		if (null == imageDescriptor)
			return null;
		return imageDescriptor.createImage();
	}

	/**
	 * Eval param.
	 *
	 * @param param the param
	 * @return the string
	 */
	public static String evalParam(String param) {
		int beginIndex = param.indexOf("#PARAM(");
		if (-1 < beginIndex) {
			int endIndex = param.indexOf(')', beginIndex);
			if (-1 < endIndex) {
				String key = param.substring(beginIndex + 7, endIndex);
				String value = (String) AdichatzApplication.getInstance().getApplicationParamMap().get(key);
				if (null != value) {
					StringBuffer paramSB = new StringBuffer();
					if (beginIndex > 0)
						paramSB.append(param.substring(0, beginIndex));
					paramSB.append(value);
					if (endIndex < param.length())
						paramSB.append(param.substring(endIndex + 1));
					return paramSB.toString();
				}
			}
		}
		return param;
	}

	/**
	 * Concatenate 2 arrays.
	 *
	 * @param <T>         the generic type
	 * @param firstArray  the first array
	 * @param secondArray the second array
	 * @return the t[]
	 */
	public static <T> T[] concatenateArrays(T[] firstArray, T[] secondArray) {
		int firstLen = firstArray.length;
		int secondLen = secondArray.length;

		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(firstArray.getClass().getComponentType(), firstLen + secondLen);
		System.arraycopy(firstArray, 0, result, 0, firstLen);
		System.arraycopy(secondArray, 0, result, firstLen, secondLen);

		return result;
	}

	/**
	 * Sort ranked controllers.
	 *
	 * @param controllers the controllers
	 */
	public static void sortRankedControllers(List<AWidgetController> controllers) {
		Collections.sort(controllers, new Comparator<AWidgetController>() {

			@Override
			public int compare(AWidgetController w1, AWidgetController w2) {
				if (!(w1 instanceof IRankedController) || !(w2 instanceof IRankedController)) {
					String controllerClassName;
					String registerId;
					int rank = 0;
					if (w1 instanceof IRankedController) {
						controllerClassName = w2.getClass().getSimpleName();
						registerId = w2.getRegisterId();
						rank = ((IRankedController) w1).getRank();
					} else {
						controllerClassName = w1.getClass().getSimpleName();
						registerId = w1.getRegisterId();
					}
					logError(getFromEngineBundle("error.controller.not.ranked", controllerClassName, registerId));
					return rank;
				}
				return ((IRankedController) w1).getRank() - ((IRankedController) w2).getRank();
			}
		});
	}
}
