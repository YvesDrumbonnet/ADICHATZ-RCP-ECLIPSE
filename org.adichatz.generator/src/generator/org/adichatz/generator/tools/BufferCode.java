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
package org.adichatz.generator.tools;

import java.io.IOException;

import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.ACodeGenerator;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.scenario.ScenarioResources;

// TODO: Auto-generated Javadoc
/**
 * The Class BufferCode.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class BufferCode {

	/** The key buffer. */
	private String keyBuffer;

	/** The ident. */
	private int ident = 0;

	/** The string buffer. */
	private final StringBuffer stringBuffer = new StringBuffer();

	/** The generator container. */
	private ACodeGenerator generator;

	private ScenarioResources scenarioResources;

	private boolean customization;

	/** The property. */
	private String property;

	/**
	 * Instantiates a new buffer code.
	 * 
	 * @param ident
	 *            the ident
	 * @param generator
	 *            the generator
	 * @param keyBuffer
	 *            the key buffer
	 */
	public BufferCode(ACodeGenerator generator, int ident, String keyBuffer) {
		this.generator = generator;
		this.ident = ident;
		this.keyBuffer = keyBuffer;
	}

	public BufferCode(ACodeGenerator generator, int ident, String keyBuffer, boolean customization) {
		this(generator, ident, keyBuffer);
		this.customization = customization;
	}

	public BufferCode(ScenarioResources scenarioResources) {
		this.scenarioResources = scenarioResources;
	}

	/**
	 * Adds the string.
	 * 
	 * @param string
	 *            the string
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void addString(String string) throws IOException {
		stringBuffer.append(string);
	}

	/**
	 * Appends string to stringBuffer.
	 * 
	 * @param stringBuffer
	 *            the string buffer
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void append(StringBuffer stringBuffer) throws IOException {
		this.stringBuffer.append(stringBuffer);
	}

	/**
	 * Appends string to stringBuffer.
	 * 
	 * @param string
	 *            the string to append
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void append(String string) throws IOException {
		for (int i = 0; i < ident; i++) {
			stringBuffer.append('\t');
		}
		stringBuffer.append(string + "\n");
	}

	/**
	 * Appends a string to the buffer string and add a tabulation.
	 * 
	 * @param string
	 *            the string to append
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void appendPlus(String string) throws IOException {
		append(string);
		ident++;
	}

	/**
	 * Removes a tabulation, appends a string to the buffer string and adds a tabulation.
	 * 
	 * @param string
	 *            the string to append
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void appendMinusPlus(String string) throws IOException {
		ident--;
		appendPlus(string);
	}

	/**
	 * Removes a tabulation and appends a string to the buffer string.
	 * 
	 * @param string
	 *            the string to append
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void appendMinus(String string) throws IOException {
		ident--;
		append(string);
	}

	/**
	 * Removes a tabulation, appends a string to the buffer string and adds a tabulation.
	 * 
	 * @param string
	 *            the string to append
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void appendPlusMinus(String string) throws IOException {
		appendPlus(string);
		ident--;
	}

	/**
	 * Appends string to stringBuffer.
	 * 
	 * @param string
	 *            the string to append
	 * @param ident
	 *            the ident
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void insert(String string, int ident) throws IOException {
		insert(string, 0, ident);
	}

	public void insert(String string, int position, int ident) throws IOException {
		StringBuffer sb = new StringBuffer(string);
		for (int i = 0; i < ident; i++) {
			sb.insert(0, '\t');
		}
		sb.append('\n');
		stringBuffer.insert(position, sb);
	}

	/**
	 * Gets the key buffer.
	 * 
	 * @return the key buffer
	 */
	public String getKeyBuffer() {
		return keyBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return stringBuffer.toString();
	}

	/**
	 * Length.
	 * 
	 * @return the int
	 */
	public int length() {
		return stringBuffer.length();
	}

	/**
	 * Gets the ident.
	 * 
	 * @return the ident
	 */
	public int getIdent() {
		return ident;
	}

	/**
	 * Adds the ident.
	 * 
	 * @param i
	 *            the i
	 */
	public void addIdent(int i) {
		ident += i;
	};

	/**
	 * Gets the string buffer.
	 * 
	 * @return the string buffer
	 */
	public StringBuffer getStringBuffer() {
		return stringBuffer;
	}

	/**
	 * Gets the generator.
	 * 
	 * @return the generator
	 */
	public ACodeGenerator getGenerator() {
		return generator;
	}

	public PluginEntity getPluginEntity() {
		return generator.getPluginEntity();
	}

	public PluginEntityWrapper getPluginEntityWrapper() {
		return generator.getScenarioInput().getPluginEntityWrapper();
	}

	/**
	 * Gets the scenario resources.
	 * 
	 * @return the scenario resources
	 */
	public ScenarioResources getScenarioResources() {
		if (null == scenarioResources)
			scenarioResources = generator.getScenarioInput().getScenarioResources();
		return scenarioResources;
	}

	public boolean isCustomization() {
		return customization;
	}

	public boolean isEmpty() {
		return 0 == stringBuffer.length();
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
