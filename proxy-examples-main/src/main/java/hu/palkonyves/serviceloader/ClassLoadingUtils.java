
package hu.palkonyves.serviceloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClassLoadingUtils {

	private static final String CLASS_SUFFIX = ".class";
	private static final String CLASSES = "classes/";

	/**
	 * Uses the passed Class loader to load classes identified by 
	 * their relative path from the class loader URL
	 */
	public List<Class<?>> loadClasses(List<String> filesOnClasspath, ClassLoader cl)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		for (String file : filesOnClasspath) {
			file = file.replace("/", ".");

			if (file.endsWith(CLASS_SUFFIX)) {
				file = file.substring(0, file.length() - CLASS_SUFFIX.length());
				
				Class<?> loadClass = cl.loadClass(file);
				classes.add(loadClass);
			}
		}
		
		return classes;
	}

	/**
	 * Returns a list of files on the classpath with relative path 
	 * to the Class loader URL
	 * @param cl
	 * @return
	 */
	public List<String> getFilesOnClasspath(ClassLoader cl) {

		try {
			Queue<String> stack = new ArrayDeque<String>();
			Enumeration<URL> classPaths = cl.getResources("");

			// initialize path
			while (classPaths.hasMoreElements()) {
				String path = classPaths.nextElement().getPath();
				stack.offer(path);
				System.out.println("classpath: " + path);
			}

			List<String> filesOnClasspath = new LinkedList<String>();
			// Depth-first traversal
			while (!stack.isEmpty()) {

				String poll = stack.poll();
				File f = new File(poll);
				File[] children = f.listFiles();

				if (children == null) {
					// f is a file
					String path = f.getPath();
					int indexOf = path.lastIndexOf(CLASSES);
					path = path.substring(indexOf + CLASSES.length());
					filesOnClasspath.add(path);
				}
				else {
					
					// f is a directory
					for (File c : children) {
						String path = c.getPath();
						stack.offer(path);
					}
				}
			}
			
			return filesOnClasspath;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
