# jsf-showcases

Showcases of how development should be done in JSF.

Creating eclipse project:
  
  `mvn eclipse:eclipse`
  
Building the artifact

  `mvn clean package install`
  
Running the container. This will download a Tomcat 7 version and run the application on that.
  
  `mvn clean verify org.codehaus.cargo:cargo-maven2-plugin:run`
  
To redeploying the application, from a separate console issue the command:

  `mvn verify org.codehaus.cargo:cargo-maven2-plugin:deployer-redeploy`
