# jsf-showcases

Showcases of how development should be done in JSF.

Building the artifact

  `mvn clean package install`

Creating eclipse project:
  
  `mvn eclipse:eclipse`
  
The application can be deployed on the standalone WildFly instance by dropping the war in the appropriate folder.

# Java Dynamic proxy and class loading examples
All the examples can be found in proxy-examples-main/src/test/ as JUnit tests

* How to create a client-side load balancer for REST service stub
* How to mimic Mockito with dynamic proxy
* How to find resources on the classpath
* How to load classes with a custom class loader
* How to find classes with a specific annotation
