package org.adichatz.scenario.util;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adichatz.common.encoding.Base64;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ISqlConnection;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JBossStandaloneReader {
	private File standaloneFile;

	private Document document;

	private DatasourceWrapper datasource;

	private String jndi;

	private boolean isSaved;

	private ScenarioResources scenarioResources;

	public JBossStandaloneReader(File standaloneFile, DatasourceWrapper datasource, String jndi,
			ScenarioResources scenarioResources) {
		this.standaloneFile = standaloneFile;
		this.datasource = datasource;
		this.jndi = jndi;
		this.scenarioResources = scenarioResources;
	}

	private Document getDocument() {
		if (null == document) {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				document = docBuilder.parse(standaloneFile);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				logError(e);
			}

		}
		return document;
	}

	public void addDatasource() {
		NodeList datasourceList = getDocument().getElementsByTagName("datasources");
		if (null == datasourceList)
			logError(getFromGeneratorBundle("scenario.module.datasources.not.exists"));
		else {
			Element datasources = (Element) datasourceList.item(0);
			Element element = document.createElement("datasource");
			element.setAttribute("enabled", "true");
			element.setAttribute("jndi-name", "java:jboss/datasources/".concat(jndi));
			element.setAttribute("jta", "true");
			element.setAttribute("pool-name", jndi);
			element.setAttribute("use-java-context", "true");
			element.appendChild(document.createTextNode("\n"));
			element.appendChild(addElement(document, "connection-url", datasource, ISqlConnection.CONNECTION_URL));
			element.appendChild(document.createTextNode("\n"));
			element.appendChild(addElement(document, "driver", datasource.getDriver()));
			element.appendChild(document.createTextNode("\n"));
			Element security = document.createElement("security");
			security.appendChild(document.createTextNode("\n"));
			security.appendChild(addElement(document, "user-name", datasource, ISqlConnection.CONNECTION_USERNAME));
			security.appendChild(document.createTextNode("\n"));
			security.appendChild(addElement(document, "password", datasource, ISqlConnection.CONNECTION_PASSWORD));
			element.appendChild(security);
			Node firstNode = datasources.getFirstChild();
			if (null != firstNode) {
				datasources.insertBefore(document.createTextNode("\n"), firstNode);
				datasources.insertBefore(element, firstNode);
			} else {
				datasources.appendChild(element);
				datasources.appendChild(document.createTextNode("\n"));
			}
		}
	}

	public void addDriver() {
		NodeList driverList = getDocument().getElementsByTagName("drivers");
		if (null == driverList)
			logError(getFromGeneratorBundle("scenario.module.drivers.not.exists"));
		else {
			Element drivers = (Element) driverList.item(0);
			Element driver = document.createElement("driver");
			String module = datasource.getModule();
			driver.setAttribute("module", module);
			driver.setAttribute("name", datasource.getDriver());
			Node firstNode = drivers.getFirstChild();
			if (null != firstNode)
				drivers.insertBefore(driver, firstNode);
			else
				drivers.appendChild(driver);
			Element xaDatasourceClass = addElement(document, "xa-datasource-class", datasource,
					ISqlConnection.XA_DATASOURCE_CLASS);
			if (null != xaDatasourceClass)
				driver.appendChild(xaDatasourceClass);
		}
	}

	public void writeDoc() {
		// Save before writing. Only one save by eclipse session / project
		if (!isSaved) {
			scenarioResources.createFolderIfNotExist("resources/build/save");
			IFile saveFile = scenarioResources.getProject()
					.getFile("resources/build/save/Standalone.xml." + new Date().getTime());
			try {
				FileInputStream inputStream = new FileInputStream(standaloneFile);
				saveFile.create(inputStream, IResource.FORCE, null);
				inputStream.close();
			} catch (IOException | CoreException e) {
				logError(e);
			}
			isSaved = true;
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setAttribute("indent-number", 4);
		try {
			Transformer transformer = transformerFactory.newTransformer();
			Document doc = getDocument();
			deleteBlankLines(doc.getDocumentElement());
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(new DOMSource(doc), new StreamResult(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(standaloneFile), "UTF-8"))));
		} catch (IOException | TransformerException e) {
			logError(e);
		}
	}

	private void deleteBlankLines(Element parentElement) {
		List<Node> toRemove = new LinkedList<>();

		NodeList children = parentElement.getChildNodes();
		int childrenCount = children.getLength();
		for (int i = 0; i < childrenCount; ++i) {
			Node child = children.item(i);
			if ("#text".equals(child.getNodeName()) && "".equals(child.getNodeValue().trim()))
				toRemove.add(child);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element) child;
				deleteBlankLines(childElement);
			}
		}

		for (Node childElement : toRemove)
			parentElement.removeChild(childElement);
		parentElement.normalize();
	}

	private Element addElement(Document doc, String elementName, DatasourceType datasource, String paramKey) {
		ParamType param = GeneratorUtil.getParam(datasource.getParams(), paramKey);
		if (null != param)
			return addElement(doc, elementName, param.getValue());
		logError(getFromGeneratorBundle("scenario.missing.parameter", paramKey));
		return null;
	}

	private Element addElement(Document doc, String elementName, String attributeName) {
		Element element = doc.createElement(elementName);
		if ("password".equals(elementName))
			try {
				attributeName = Base64.decrypt(attributeName);
			} catch (Exception e) {
				logError(e);
			}
		element.appendChild(doc.createTextNode(attributeName));
		return element;
	}

	public boolean hasDriver(String driver) {
		NodeList driverList = getDocument().getElementsByTagName("drivers");
		for (int s = 0; s < driverList.getLength(); s++) {
			Node drivers = driverList.item(s);
			if (drivers.getNodeType() == Node.ELEMENT_NODE) {
				int count = drivers.getChildNodes().getLength();
				for (int i = 0; i < count; i++) {
					Node node = drivers.getChildNodes().item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						if (driver.equals(((Element) node).getAttribute("name")))
							return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasDatasource(String datasource) {
		NodeList datasourceList = getDocument().getElementsByTagName("datasources");
		for (int s = 0; s < datasourceList.getLength(); s++) {
			Node datasources = datasourceList.item(s);
			if (datasources.getNodeType() == Node.ELEMENT_NODE) {
				int count = datasources.getChildNodes().getLength();
				for (int i = 0; i < count; i++) {
					Node node = datasources.getChildNodes().item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						if (datasource.equals(((Element) node).getAttribute("pool-name")))
							return true;
					}
				}
			}
		}
		return false;
	}

	public File getStandaloneFile() {
		return standaloneFile;
	}
}
