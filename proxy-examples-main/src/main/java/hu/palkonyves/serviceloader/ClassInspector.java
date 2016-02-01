
package hu.palkonyves.serviceloader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassInspector {

	/**
	 * Writes some information about a class
	 * to the standard output
	 * @param clazz
	 */
	public static void inspectClass(Class<?> clazz) {
		String name = clazz.getName();
		Class<?>[] ifaces = clazz.getClasses();
		Annotation[] annotations = clazz.getAnnotations();
		Field[] declaredFields = clazz.getDeclaredFields();
		Method[] declaredMethods = clazz.getDeclaredMethods();

		System.out.println(name);
		inspectArray(ifaces);
		inspectArray(annotations);
		inspectArray(declaredFields);
		inspectArray(declaredMethods);

	}

	private static <T> void inspectArray(T[] arr) {

		for (T a : arr) {
			System.out.println("  " + a);
		}

	}
}
