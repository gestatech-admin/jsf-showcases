
package hu.palkonyves.javaproxy.test;

import hu.palkonyves.serviceloader.ClassLoadingUtils;
import hu.palkonyves.serviceloader.MyClassLoader;

import java.net.URL;
import java.util.List;

import javax.persistence.EntityAnnotation;

import org.junit.Assert;
import org.junit.Test;

public class ClassLoaderTest {

	/**
	 * Only find classes on the classpath using the same classloader
	 * that loaded <i>this class</i>. This example does not load the
	 * classes in memory, only finds them on the classpath.
	 */
	@Test
	public void find_classes_on_classpath_test() {
		ClassLoader cl = ClassLoaderTest.class.getClassLoader();
		ClassLoadingUtils clUtils = new ClassLoadingUtils();
		
		List<String> filesOnClasspath = clUtils.getFilesOnClasspath(cl);
		
		System.out.println("Classes found: ");
		for (String string : filesOnClasspath) {
			System.out.println(string);
		}
	}
	
	/**
	 * Load classes from the classpath using the same classloader
	 * that loaded <i>this class</i>
	 * 
	 * <p>
	 * This is a programmatic approach of loading classes even before 
	 * referencing them. The use-case is to explore annotated classes
	 * on the classpath e.g. JPA, CDI, EJB containers do the same.
	 * @throws ClassNotFoundException
	 */
	@Test
	public void load_classes_on_classpath_test() throws ClassNotFoundException {
		ClassLoader cl = ClassLoaderTest.class.getClassLoader();
		ClassLoadingUtils clUtils = new ClassLoadingUtils();
		
		List<String> filesOnClasspath = clUtils.getFilesOnClasspath(cl);
		List<Class<?>> classes = clUtils.loadClasses(filesOnClasspath, cl);
		
		System.out.println("Classes loaded: ");
		
		for (Class<?> clazz : classes) {
			System.out.println(clazz.getName());
		}
	}
	
    /**
     * Demonstrates that classes can be loaded by different classloaders
     * in a way that the classes will be present multiple times in the memory.
     * 
     * <p>This is how Java EE containers work. Each deployment is loaded by a separate
     * classloader that does not allow sharing classes between deployments
     * @throws ClassNotFoundException
     */
	@Test
	public void custom_classloader_test() throws ClassNotFoundException {
		ClassLoader cl = new MyClassLoader(new URL[] {});
		ClassLoadingUtils clUtils = new ClassLoadingUtils();
		
		List<String> filesOnClasspath = clUtils.getFilesOnClasspath(cl);
		List<Class<?>> classes = clUtils.loadClasses(filesOnClasspath, cl);
		
		System.out.println("Classes loaded: ");
		
		for (Class<?> clazz : classes) {
			System.out.println(clazz.getName());
		}
		
		Class<?> entityAnnotationClazz = null;
		for (Class<?> clazz : classes) {
			if (clazz.getName().contains(EntityAnnotation.class.getName())) {
				entityAnnotationClazz = clazz;
				break;
			}
		}
		
		Assert.assertNotEquals(EntityAnnotation.class.getName() + " is loaded twice", EntityAnnotation.class, entityAnnotationClazz);
	}

}
