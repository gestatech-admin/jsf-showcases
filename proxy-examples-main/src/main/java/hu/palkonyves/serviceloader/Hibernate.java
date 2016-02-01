
package hu.palkonyves.serviceloader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.JPAService;

/**
 * Mimics Hibernate on finding entities
 */
public class Hibernate implements JPAService {

	@Override
	public void init() throws IOException, ClassNotFoundException {

		ClassLoadingUtils clUtils = new ClassLoadingUtils();
		ClassLoader cl = Hibernate.class.getClassLoader();
		
		List<String> filesOnClasspath = clUtils.getFilesOnClasspath(cl);
		
		// Load the classes
		List<Class<?>> classes = clUtils.loadClasses(filesOnClasspath, cl);
		
		Class<? extends Annotation> entityClass = javax.persistence.EntityAnnotation.class;
		
		List<Class<?>> entities = new ArrayList<Class<?>>();
		for (Class<?> clazz : classes) {
			if (clazz.isAnnotationPresent(entityClass)) {
				entities.add(clazz);
			}
		}
		
		System.out.println("entities found: ");
		System.out.println(entities);
	}
}
