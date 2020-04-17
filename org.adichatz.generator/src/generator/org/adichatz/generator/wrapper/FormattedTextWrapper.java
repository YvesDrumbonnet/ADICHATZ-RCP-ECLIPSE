/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.generator.wrapper;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.util.Locale;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.ControlGenerator;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.wrapper.internal.IControlWrapper;
import org.adichatz.generator.xjc.FormattedTextType;
import org.eclipse.nebula.widgets.formattedtext.ByteFormatter;
import org.eclipse.nebula.widgets.formattedtext.DoubleFormatter;
import org.eclipse.nebula.widgets.formattedtext.FloatFormatter;
import org.eclipse.nebula.widgets.formattedtext.IntegerFormatter;
import org.eclipse.nebula.widgets.formattedtext.LongFormatter;
import org.eclipse.nebula.widgets.formattedtext.PercentFormatter;
import org.eclipse.nebula.widgets.formattedtext.ShortFormatter;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractComboWrapper.
 */
public class FormattedTextWrapper extends FormattedTextType implements IControlWrapper {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.wrapper.internal.IWidgetWrapper#preCreate()
	 */
	@Override
	public void preCreate() {
		mandatory = null != mandatory ? mandatory : false;
		noLabel = null != noLabel ? noLabel : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.wrapper.IWidgetWrapper#postCreate(org.adichatz.generator.ControlGenerator, java.lang.String)
	 */
	public void postCreate(ControlGenerator generator, String controllerName) throws IOException {
		String formatter;
		switch (format) {
		case DOUBLE:
			formatter = generator.getObjectName(DoubleFormatter.class);
			break;
		case FLOAT:
			formatter = generator.getObjectName(FloatFormatter.class);
			break;
		case INTEGER:
			formatter = generator.getObjectName(IntegerFormatter.class);
			break;
		case LONG:
			formatter = generator.getObjectName(LongFormatter.class);
			break;
		case PERCENT:
			formatter = generator.getObjectName(PercentFormatter.class);
			break;
		case BYTE:
			formatter = generator.getObjectName(ByteFormatter.class);
			break;
		case SHORT:
			formatter = generator.getObjectName(ShortFormatter.class);
			break;
		default:
			logError("format " + format.value() + " is not valid!?");
			return;
		}
		generator.getObjectName(Locale.class);
		KeyWordGenerator keyWordGenerator = generator.getKeyWordGenerator();
		BufferCode buffer = generator.getAfterCreateControlBuffer();
		StringBuffer formatterSB = new StringBuffer("new ").append(formatter).append("(");
		if (!EngineTools.isEmpty(editPattern))
			formatterSB.append(keyWordGenerator.evalExpr2Str(buffer, editPattern, false)).append(",");
		else
			formatterSB.append("null, ");
		if (!EngineTools.isEmpty(displayPattern))
			formatterSB.append(keyWordGenerator.evalExpr2Str(buffer, displayPattern, false)).append(",");
		else
			formatterSB.append("null, ");
		if (!EngineTools.isEmpty(locale))
			formatterSB.append(keyWordGenerator.getLocale(buffer, locale));
		else
			formatterSB.append("Locale.getDefault()");
		formatterSB.append(")");
		buffer.append("getFormattedText().setFormatter(" + formatterSB.toString() + ");");

	}
}
