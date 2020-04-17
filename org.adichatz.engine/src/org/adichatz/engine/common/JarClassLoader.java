package org.adichatz.engine.common;

import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassLoader extends ClassLoader {
	private JarFile jarFile;

	public JarClassLoader(JarFile jarFile, ClassLoader parentClassLoader) {
		super(parentClassLoader);
		this.jarFile = jarFile;
	}

	@Override
	public synchronized Class<?> findClass(String className) throws ClassNotFoundException {
		byte[] b;
		b = loadClassData(className);
		if (b == null) {
			throw new ClassNotFoundException("Could not find class " + className); //$NON-NLS-1$
		}
		return defineClass(className, b, 0, b.length);
	}

	private byte[] loadClassData(String className) throws ClassNotFoundException {
		try {
			JarEntry entry = jarFile.getJarEntry(className.replace('.', '/').concat(".class"));
			if (null != entry) {
				return EngineTools.getByteArray(jarFile.getInputStream(entry));
			}
		} catch (IOException e) {
			throw new ClassNotFoundException("IO Exception for loading class " + className + " from " + jarFile.getName()); //$NON-NLS-1$
		}
		return null;
	}
}