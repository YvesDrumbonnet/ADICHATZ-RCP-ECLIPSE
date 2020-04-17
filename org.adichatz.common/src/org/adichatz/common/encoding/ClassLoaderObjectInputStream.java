package org.adichatz.common.encoding;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

/**
 * A special ObjectInputStream that loads a class based on a specified <code>ClassLoader</code> rather than the system default.
 * <p>
 * This is useful in dynamic container environments.
 * 
 * @author Paul Hammant (apache commons)
 * @version $Id: ClassLoaderObjectInputStream.java 241817 2005-08-27 23:16:02Z scolebourne $
 */
public class ClassLoaderObjectInputStream extends ObjectInputStream {

	/** The class loader to use. */
	private ClassLoader classLoader;

	/**
	 * Constructs a new ClassLoaderObjectInputStream.
	 * 
	 * @param classLoader
	 *            the ClassLoader from which classes should be loaded
	 * @param inputStream
	 *            the InputStream to work on
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws StreamCorruptedException
	 *             if the stream is corrupted
	 */
	public ClassLoaderObjectInputStream(ClassLoader classLoader, InputStream inputStream) throws IOException,
			StreamCorruptedException {
		super(inputStream);
		this.classLoader = classLoader;
	}

	/** @inheritDoc */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Class resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {

		Class clazz = Class.forName(objectStreamClass.getName(), false, classLoader);

		if (clazz != null) {
			// the classloader knows of the class
			return clazz;
		} else {
			// classloader knows not of class, let the super classloader do it
			return super.resolveClass(objectStreamClass);
		}
	}
}