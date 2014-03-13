jsf-showcases
=============

Showcases of how development should be done in JSF.

Creating eclipse project
  mvn eclipse:eclipse
  
Building the artifact
  mvn clean package
  
Test the application. For that change the containerUrl property in jsf-showcase.web/pom.xml 
  mvn clean verify org.codehaus.cargo:cargo-maven2-plugin:run
