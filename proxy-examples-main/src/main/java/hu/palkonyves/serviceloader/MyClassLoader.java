
package hu.palkonyves.serviceloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;

/**
 * As opposed to the original ClassLoader contract, this 
 * ClassLoader implementation tries to load the class by 
 * it's own first. If the class is not found, then it delegates
 * to the system classloader.
 * For simplicity this class loader does not delegate to the
 * parent class loader on failure.
 * 
 * <p>It only works if the classes are ordinary files on the 
 * classpath, it does not look into jars or wars.
 */
public class MyClassLoader extends URLClassLoader {

	/**
	 * This class loader will define and own the loaded classes.
	 * This makes it possible that different classloaders can load the same classes and
	 * therefore define the same classes in the JVM multiple times.
	 */
	private Hashtable<String, Class<?>> classes = new Hashtable<String, Class<?>>();

	public MyClassLoader(URL[] urls) {
		super(urls);
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return findClass(className);
	}

	public Class<?> findClass(String className) throws ClassNotFoundException {
		Class<?> result = null;

		result = (Class<?>) classes.get(className); //checks in cached classes  
		if (result != null) {
			return result;
		}

		try {
			byte[] classData = getBytes(className);

			// Create in-memory class definition based on bytecode
			result = defineClass(className, classData, 0, classData.length);
			classes.put(className, result);
			return result;
		}
		catch (Exception e) {
			// noop
		}

		return findSystemClass(className);

	}

	/**
	 * Converts a fully qualified classname to a file path and loads its
	 * contents as a byte array
	 * 
	 */
	private byte[] getBytes(String className) throws IOException {
		InputStream systemResourceAsStream = getSystemResourceAsStream(className.replace(".", "/")
				+ ".class");
		BufferedInputStream in = new BufferedInputStream(systemResourceAsStream);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int i;

		while ((i = in.read()) != -1) {
			out.write(i);
		}

		in.close();
		byte[] classData = out.toByteArray();
		out.close();
		return classData;
	}

}
