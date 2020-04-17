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
package org.adichatz.engine.tabular;

import static org.adichatz.engine.common.LogBroker.logError;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.controller.utils.IPredicate;
import org.adichatz.engine.widgets.supplement.AdiDecimalFormat;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class ColumnViewerFilter.
 * 
 * @param <T>
 *            the generic type
 * @author Yves Drumbonnet
 *
 */
public class ColumnViewerFilter<T> extends ViewerFilter {

	private Boolean exactString;

	private Boolean caseInsensitive;

	private String searchString;

	/** The predicate. */
	private IPredicate predicate;

	/** The tabular controller. */
	private ATabularController<T> tabularController;

	@SuppressWarnings("rawtypes")
	private Comparable filterValue;

	private AColumnController<T> columnController;

	/** The text. */
	private String text;

	/**
	 * Instantiates a new column value viewer filter.
	 * 
	 */
	public ColumnViewerFilter(AColumnController<T> columnController, String searchString) {
		this.columnController = columnController;
		this.searchString = searchString;
		tabularController = columnController.getParentController();
	}

	public void initFilter(@SuppressWarnings("rawtypes") Comparable filterValue, IPredicate predicate) {
		this.filterValue = filterValue;
		this.predicate = predicate;
	}

	public Object getFilterValue() {
		return filterValue;
	}

	public Boolean isExactString() {
		return exactString;
	}

	public void setExactString(Boolean exactString) {
		this.exactString = exactString;
	}

	public Boolean isCaseInsensitive() {
		return caseInsensitive;
	}

	public void setCaseInsensitive(Boolean caseInSensitive) {
		this.caseInsensitive = caseInSensitive;
	}

	public String getSearchString() {
		return searchString;
	}

	public AColumnController<T> getColumnController() {
		return columnController;
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return predicate.apply(element);
	}

	/**
	 * Enable.
	 */
	public void enable() {
		tabularController.getControl().setRedraw(false);
		tabularController.getViewer().addFilter(this);
		tabularController.getControl().setRedraw(true);
	}

	@SuppressWarnings("unchecked")
	public void computePredicate(boolean exactString, boolean caseInsensitive)
			throws ParseException, SecurityException, NoSuchMethodException {
		Class<?> returnType;
		returnType = columnController.getClass().getMethod("getValue", tabularController.getBeanClass()).getReturnType();
		if (returnType.equals(String.class)) {
			this.exactString = exactString;
			this.caseInsensitive = caseInsensitive;
			final boolean inverse;
			if (searchString.startsWith("!=")) {
				filterValue = searchString.substring(' ' == searchString.charAt(2) ? 3 : 2);// skip '!=' or '!= ' characters
				inverse = true;
			} else {
				inverse = false;
				filterValue = searchString;
			}
			if (exactString) {
				if (!inverse)
					text = EngineTools.getFromEngineBundle("query.search.equal", columnController.getText(), filterValue);
				else
					text = EngineTools.getFromEngineBundle("query.search.different", columnController.getText(), filterValue);
				if (!caseInsensitive) {
					text = text.concat(".");
					// String, case sentitive, exact string
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							String value = columnController.getColumnText((T) element);
							return null != value && !inverse == value.equals(filterValue);
						}
					};
				} else {
					// String, case Insentitive, exact string
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							String value = columnController.getColumnText((T) element);
							return null != value && !inverse == value.equalsIgnoreCase((String) filterValue);
						}
					};
					text = text.concat(" (").concat(EngineTools.getFromEngineBundle("query.search.caseInsensitive")).concat(").");
				}
			} else {
				if (!inverse) // search string starts with !=
					text = EngineTools.getFromEngineBundle("query.search.contain.string", columnController.getText(), filterValue);
				else
					text = EngineTools.getFromEngineBundle("query.search.no.contain.string", columnController.getText(),
							filterValue);
				final Pattern pattern;
				if (!caseInsensitive) {
					pattern = Pattern.compile((String) filterValue);
					text = text.concat(".");
				} else {
					pattern = Pattern.compile((String) filterValue, Pattern.CASE_INSENSITIVE);
					text = text.concat(" (").concat(EngineTools.getFromEngineBundle("query.search.caseInsensitive")).concat(").");
				}
				predicate = new IPredicate() {
					@Override
					public boolean apply(Object element) {
						String value = columnController.getColumnText((T) element);
						return null != value && !inverse == pattern.matcher(value).find();
					}
				};
			}
		} else if (FieldTools.isBooleanType(returnType)) { // if (returnType != String.class)
			boolean value = Boolean.valueOf(searchString);
			filterValue = value;
			text = EngineTools.getFromEngineBundle(value ? "query.search.true" : "query.search.false", columnController.getText(),
					filterValue);
			predicate = new IPredicate() {
				@Override
				public boolean apply(Object element) {
					Object value = columnController.getValue((T) element);
					return null != value && filterValue.equals(value);
				}
			};
		} else { // if (returnType != String.class && is not boolean)
			final Format format = columnController.getFormat();
			if (searchString.startsWith(">=")) {
				String searchParam = searchString.substring(2).trim();
				filterValue = getFilterValue(format, returnType, searchParam);
				if (null == format || format instanceof AdiDecimalFormat)
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							Object value = getValue(returnType, format, element);
							return null != value && 0 >= filterValue.compareTo(value);
						}
					};
				else {
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							try {
								String text = columnController.getColumnText((T) element);
								if (null == text)
									return false;
								Object value = ((DateFormat) format).parse(text);
								return null != value && 0 >= filterValue.compareTo(value);
							} catch (ParseException e) {
								logError(e);
							}
							return true;
						}
					};
				}
				text = EngineTools.getFromEngineBundle("query.search.greaterOrEqual", columnController.getText(), searchParam);
			} else if (searchString.startsWith("<=")) {
				String searchParam = searchString.substring(2).trim();
				filterValue = getFilterValue(format, returnType, searchParam);
				if (null == format || format instanceof AdiDecimalFormat)
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							Object value = getValue(returnType, format, element);
							return null != value && 0 <= filterValue.compareTo(value);
						}
					};
				else {
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							try {
								String text = columnController.getColumnText((T) element);
								if (null == text)
									return false;
								Object value = ((DateFormat) format).parse(text);
								return null != value && 0 <= filterValue.compareTo(value);
							} catch (ParseException e) {
								logError(e);
							}
							return true;
						}
					};
				}
				text = EngineTools.getFromEngineBundle("query.search.lessOrEqual", columnController.getText(), searchParam);
			} else if (searchString.startsWith(">")) {
				String searchParam = searchString.substring(1).trim();
				filterValue = getFilterValue(format, returnType, searchParam);
				if (null == format || format instanceof AdiDecimalFormat)
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							Object value = getValue(returnType, format, element);
							return null != value && 0 > filterValue.compareTo(value);
						}
					};
				else {
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							try {
								String text = columnController.getColumnText((T) element);
								if (null == text)
									return false;
								Object value = ((DateFormat) format).parse(text);
								return null != value && 0 > filterValue.compareTo(value);
							} catch (ParseException e) {
								logError(e);
							}
							return true;
						}
					};
				}
				text = EngineTools.getFromEngineBundle("query.search.greater", columnController.getText(), searchParam);
			} else if (searchString.startsWith("<")) {
				String searchParam = searchString.substring(1).trim();
				filterValue = getFilterValue(format, returnType, searchParam);
				if (null == format || format instanceof AdiDecimalFormat)
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							Object value = getValue(returnType, format, element);
							return null != value && 0 < filterValue.compareTo(value);
						}
					};
				else {
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							try {
								String text = columnController.getColumnText((T) element);
								if (null == text)
									return false;
								Object value = ((DateFormat) format).parse(text);
								return null != value && 0 < filterValue.compareTo(value);
							} catch (ParseException e) {
								logError(e);
							}
							return true;
						}
					};
				}
				text = EngineTools.getFromEngineBundle("query.search.less", columnController.getText(), searchParam);
			} else if (searchString.startsWith("!=")) {
				String searchParam = searchString.substring(2).trim();
				filterValue = getFilterValue(format, returnType, searchParam);
				if (null == format || format instanceof AdiDecimalFormat)
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							Object value = getValue(returnType, format, element);
							return null != value && 0 != filterValue.compareTo(value);
						}
					};
				else {
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							try {
								String text = columnController.getColumnText((T) element);
								if (null == text)
									return true;
								Object value = ((DateFormat) format).parse(text);
								return 0 != filterValue.compareTo(value);
							} catch (ParseException e) {
								logError(e);
							}
							return true;
						}
					};
				}
				text = EngineTools.getFromEngineBundle("query.search.different", columnController.getText(), searchParam)
						.concat(".");
			} else {
				filterValue = getFilterValue(format, returnType, searchString);
				if (null == format || format instanceof AdiDecimalFormat)
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							Object value = getValue(returnType, format, element);
							return null != value && 0 == filterValue.compareTo(value);
						}

					};
				else {
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							try {
								String text = columnController.getColumnText((T) element);
								if (null == text)
									return false;
								Object value = ((DateFormat) format).parse(text);
								return null != value && 0 == filterValue.compareTo(value);
							} catch (ParseException e) {
								logError(e);
							}
							return true;
						}
					};
				}
				text = EngineTools.getFromEngineBundle("query.search.equal", columnController.getText(), searchString).concat(".");
			}
		}
	}

	private Object getValue(Class<?> returnType, final Format format, Object element) {
		try {
			return getFilterValue(format, returnType, columnController.getLabelProvider().getText(element));
		} catch (ParseException e) {
			logError(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private Comparable getFilterValue(Format format, Class<?> returnType, String searchParam) throws ParseException {
		if (returnType.equals(Byte.class) || returnType.equals(byte.class)) {
			return Byte.valueOf(getBigDecimal(format, searchParam).byteValue());
		} else if (returnType.equals(Short.class) || returnType.equals(short.class)) {
			return Short.valueOf(getBigDecimal(format, searchParam).shortValue());
		} else if (returnType.equals(Integer.class) || returnType.equals(int.class)) {
			return Integer.valueOf(getBigDecimal(format, searchParam).intValue());
		} else if (returnType.equals(Long.class) || returnType.equals(long.class)) {
			return Long.valueOf(getBigDecimal(format, searchParam).longValue());
		} else if (returnType.equals(Float.class) || returnType.equals(float.class)) {
			return Float.valueOf(getBigDecimal(format, searchParam).floatValue());
		} else if (returnType.equals(Double.class) || returnType.equals(double.class)) {
			return Double.valueOf(getBigDecimal(format, searchParam).doubleValue());
		} else if (returnType.equals(Boolean.class) || returnType.equals(boolean.class)) {
			return Boolean.valueOf(searchParam);
		} else if (returnType.equals(BigDecimal.class)) {
			return getBigDecimal(format, searchParam);
		} else if (returnType.equals(Date.class)) {
			Date date = ((DateFormat) format).parse(searchParam);
			if (returnType.equals(Timestamp.class)) {
				return new Timestamp(date.getTime());
			}
			return date;
		}
		return searchParam;
	}

	private BigDecimal getBigDecimal(Format format, String searchParam) throws ParseException {
		DecimalFormat decimalFormat = (DecimalFormat) format;
		if (null == decimalFormat)
			decimalFormat = new DecimalFormat();
		decimalFormat.setParseBigDecimal(true);
		return (BigDecimal) decimalFormat.parse(searchParam);
	}
}
