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
package org.adichatz.engine.widgets;

import static org.adichatz.engine.common.LogBroker.logError;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

// TODO: Auto-generated Javadoc
/**
 * Numeric text viewer. Add numeric formating capabilities to the <code>Text</code> widget of SWT. The embedded text widget is
 * accessible by the getControl() method, allowing to apply to it all necessary behaviors (layout, listeners...).
 * <p>
 * 
 * Automatically adds:
 * <ul>
 * <li>a VerifyListener to text to check the entering text.</li>
 * <li>a ModifyListener to text to check and format the result (BigDecimal value).</li>
 * </ul>
 * 
 * Formatting is delegated to <code>DecimalFormat</code>.
 */
public class NumericText {

	/** The do it flag. */
	private boolean doit = true;

	/** Encapsulated Text widget. */
	protected final Text text;

	/** the value corresponding to text. */
	protected BigDecimal value;

	/** the value corresponding to text. */
	protected BigDecimal oldValue;

	/** the format pattern. */
	protected String pattern;

	/** position of cursor in the text. */
	protected Integer caretPos;

	/** value of text.getText() before the change of the text */
	protected String textBefore;

	/** the decimal format. */
	protected DecimalFormat formatter = new DecimalFormat();

	/** the edit decimal format. */
	protected DecimalFormat editFormatter = new DecimalFormat();

	/** the current decimal format. */
	protected DecimalFormat currentFormatter = new DecimalFormat();

	/** greatest integer digits. */
	private int maximumIntegerDigits = -1;

	/** greatest fraction digits. */
	private int maximumFractionDigits = 0;

	/** Flag for null value accepted. */
	private boolean nullOk = true;

	public NumericText(Text text) {
		this.text = text;
		formatter.setParseBigDecimal(true);
	}

	/**
	 * Adds the standard listeners.
	 */
	public void addStandardListeners() {
		text.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (text.isEnabled()) {
					text.setSelection(0);
				}
				setEditFormatter();
				currentFormatter = editFormatter;
				textBefore = text.getText();
				if (null != text.getText() && 0 < text.getText().length()) {
					try {
						doit = false;
						value = (BigDecimal) formatter.parse(text.getText());
						text.setText(currentFormatter.format(value));
					} catch (ParseException e1) {
						logError(e1);
					} finally {
						doit = true;
					}
				}
				if (null == caretPos)
					caretPos = text.getText().length();
				text.setSelection(caretPos);
			}

			public void focusLost(FocusEvent e) {
				if (null == text.getText() || 0 == text.getText().length())
					if (nullOk)
						value = null;
					else {
						doit = false;
						value = new BigDecimal(0).setScale(maximumFractionDigits, RoundingMode.HALF_UP);
						text.setText("0");
						doit = true;
					}
				else
					try {
						doit = false;
						value = (BigDecimal) currentFormatter.parse(text.getText());
						text.setText(formatter.format(value));
					} catch (ParseException e1) {
						text.setText(textBefore);
						text.getDisplay().beep();
						logError(e1);
					} finally {
						doit = true;
						currentFormatter = formatter;
					}
			}
		});
		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				textBefore = text.getText();
			}
		});
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				NumericText.this.modifyText();
			}
		});
	}

	/**
	 * Modify text.
	 */
	public void modifyText() {
		if (doit) {
			/** value of text.getText() before the change of the text */
			BigDecimal valueBefore = value;
			oldValue = value;
			doit = false;
			if (null == text.getText() || 0 == text.getText().length() || text.getText().equals("-")) {
				if (nullOk) {
					value = null;
				} else {
					value = new BigDecimal(0).setScale(maximumFractionDigits, RoundingMode.HALF_UP);
				}
			} else
				try {
					if (!text.getDisplay().getFocusControl().equals(text)) {
						// Change comes from outside control
						// Generatally changed is raised from programmatically invocation of notifyListener(SWT.Modify...)
						oldValue = (BigDecimal) currentFormatter.parse(textBefore);
						value = (BigDecimal) currentFormatter.parse(text.getText());
						text.setText(formatter.format(value));
					} else {
						String textValue = text.getText();
						new BigDecimal(text.getText());
						int integerDigits = textValue.indexOf('.');
						if (integerDigits > -1) {
							if (0 == maximumFractionDigits)
								throw new ParseException("Fraction symbol is not allowed!", 0);
							if (maximumFractionDigits < textValue.length() - integerDigits - 1)
								throw new ParseException("Too many fraction digits", 0);
						} else {
							integerDigits = text.getText().length();
						}
						if ('-' == textValue.charAt(0))
							integerDigits--;
						if (integerDigits > maximumIntegerDigits && -1 != maximumIntegerDigits)
							throw new ParseException("Too many integer digits", 0);
						value = (BigDecimal) currentFormatter.parse(text.getText());
						caretPos = text.getCaretPosition();
					}
				} catch (Exception e1) {
					value = valueBefore;
					setText(textBefore);
					text.setSelection(caretPos);
					text.getDisplay().beep();
				}
			doit = true;
		}
	}

	/**
	 * Gets the text control.
	 * 
	 * @return the text control
	 */
	public Text getControl() {
		return text;
	}

	/**
	 * Gets the BigDecimal value.
	 * 
	 * @return the BigDecimal value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Gets the BigDecimal value.
	 * 
	 * @return the BigDecimal value
	 */
	public BigDecimal getOldValue() {
		return oldValue;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public void setValue(BigDecimal value) throws ParseException {
		doit = false;
		if (null == value) {
			this.value = null;
			setText("");
		} else {
			this.value = value.setScale(maximumFractionDigits, RoundingMode.HALF_UP);
			setText(currentFormatter.format(value));
		}
		doit = true;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public void setValue(Number value) throws ParseException {
		if (null == value) {
			this.value = null;
			setText("");
		} else
			setValue(new BigDecimal(value.toString()).setScale(maximumFractionDigits, RoundingMode.HALF_UP));
		oldValue = this.value;
	}

	/**
	 * Sets the Text widget value, preventing fire of Modify events.
	 * 
	 * @param textValue
	 *            the text value
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	protected void setText(String textValue) {
		if (null == textValue)
			text.setText("");
		else
			text.setText(textValue);
	}

	/**
	 * Sets the formatter.
	 */
	public void setFormatter() {
		formatter = new DecimalFormat();
		formatter.setParseBigDecimal(true);
		maximumIntegerDigits = -1;
		maximumFractionDigits = 0;
		currentFormatter = formatter;
	}

	/**
	 * Sets the formatter.
	 * 
	 * @param decimalFormatSymbols
	 *            the decimal format symbols
	 */
	public void setFormatter(DecimalFormatSymbols decimalFormatSymbols) {
		formatter = new DecimalFormat();
		formatter.setParseBigDecimal(true);
		formatter.setDecimalFormatSymbols(decimalFormatSymbols);
		maximumIntegerDigits = -1;
		maximumFractionDigits = 0;
		currentFormatter = formatter;
	}

	/**
	 * Sets the formatter.
	 * 
	 * @param pattern
	 *            the new pattern
	 */
	public void setFormatter(String pattern) {
		setFormatter(pattern, new DecimalFormatSymbols(Locale.getDefault()));
	}

	/**
	 * Sets the formatter.
	 * 
	 * @param pattern
	 *            the pattern
	 * @param decimalFormatSymbols
	 *            the decimal format symbols
	 */
	public void setFormatter(String pattern, DecimalFormatSymbols decimalFormatSymbols) {
		this.pattern = pattern;
		formatter = new DecimalFormat(pattern, decimalFormatSymbols);
		formatter.setParseBigDecimal(true);

		if (null != pattern && pattern.length() > 0) {
			int grouping = 0;
			for (int i = 0; i < pattern.length(); i++)
				if (pattern.charAt(i) == ',')
					grouping++;
			if (pattern.indexOf('.') >= 0) {
				maximumIntegerDigits = pattern.indexOf('.') - grouping;
				maximumFractionDigits = pattern.length() - pattern.indexOf('.') - 1;
				formatter.setMaximumIntegerDigits(maximumIntegerDigits);
				formatter.setMaximumFractionDigits(maximumFractionDigits);
			} else {
				maximumIntegerDigits = pattern.length() - grouping;
				maximumFractionDigits = 0;
				formatter.setParseIntegerOnly(true);
				formatter.setMaximumIntegerDigits(maximumIntegerDigits);
			}
		}
		currentFormatter = formatter;
	}

	/**
	 * Sets the null Allowed.
	 * 
	 * @param nullOk
	 *            the null ok
	 */
	public void setNullOk(boolean nullOk) {
		this.nullOk = nullOk;
	}

	/**
	 * Checks if is doit.
	 * 
	 * @return true, if is doit
	 */
	public boolean isDoit() {
		return doit;
	}

	/**
	 * Sets the edit formatter.
	 */
	private void setEditFormatter() {
		if (null == pattern) {
			editFormatter = new DecimalFormat();
			editFormatter.setGroupingSize(0);
		} else {
			char[] patternElements = pattern.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < patternElements.length; i++) {
				if ('#' == patternElements[i] || '0' == patternElements[i] || '.' == patternElements[i])
					sb.append(patternElements[i]);
			}
			String editPattern = sb.toString();
			editFormatter = new DecimalFormat(editPattern, new DecimalFormatSymbols(Locale.US));
		}
		editFormatter.setParseBigDecimal(true);
	}

	/**
	 * Gets the maximum fraction digits.
	 * 
	 * @return the maximumFractionDigits
	 */
	public int getMaximumFractionDigits() {
		return maximumFractionDigits;
	}
}
