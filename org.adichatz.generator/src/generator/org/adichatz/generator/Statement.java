package org.adichatz.generator;

import org.adichatz.generator.tools.BufferCode;

/**
 * The Class Statement.
 *
 * @author Yves Drumbonnet
 */
public class Statement {
	public static Statement newStatement(Statement statementParam, String expression) {
		Statement statement = new Statement(null, null, null, statementParam.getBufferCode(), statementParam.property, expression);
		return statement;
	}

	/** The prefix. */
	private String prefix;

	/** The getter. */
	private String getter;

	/** The setter. */
	private String setter;

	/** The buffer code. */
	private BufferCode bufferCode;

	/** The property. */
	private String property;

	/** The expression. */
	private String expression;

	private String preSetter = "";

	private String postSetter = "";

	/**
	 * Instantiates a new statement.
	 *
	 * @param prefix
	 *            the prefix
	 * @param getter
	 *            the getter
	 * @param setter
	 *            the setter
	 * @param bufferCode
	 *            the buffer code
	 * @param property
	 *            the property
	 * @param expression
	 *            the expression
	 */
	public Statement(String prefix, String getter, String setter, BufferCode bufferCode, String property, String expression) {
		this.prefix = prefix;
		this.getter = getter;
		this.setter = setter;
		this.bufferCode = bufferCode;
		this.property = property;
		this.expression = expression;
	}

	/**
	 * Gets the statement.
	 *
	 * @return the statement
	 */
	public String getStatement(KeyWordGenerator keyWordGenerator) {
		Object value = keyWordGenerator.evalProperty(this);
		return prefix + getter + preSetter + setter + "(" + value + ")" + postSetter + ";";
	}

	public String getPrefix() {
		return prefix;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getSetter() {
		return setter;
	}

	public void setSetter(String setter) {
		this.setter = setter;
	}

	public BufferCode getBufferCode() {
		return bufferCode;
	}

	public String getProperty() {
		return property;
	}

	public String getExpression() {
		return expression;
	}

	public String getPostSetter() {
		return postSetter;
	}

	public void setPostSetter(String postSetter) {
		this.postSetter = postSetter;
	}
}
