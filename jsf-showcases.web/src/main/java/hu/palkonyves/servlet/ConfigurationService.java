package hu.palkonyves.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigurationService {
    
    public static final String APPLICATION_PROPERTIES = "/application.properties";
    public static final String REST_BASE_URL = "rest.base.url";
    public static final String REST_ELAPSEDTIME_URL = "rest.elapsedtime.url";

    private Properties applicationProperties;
    
    public ConfigurationService() {
        loadApplicationProperties();
    }

    public String getProperty(String key, String defaultValue) {
        return applicationProperties.getProperty(key, defaultValue);
    }
    
    public Integer getInt(String key, Integer defaultValue) {
        String property = getProperty(key, null);
        return property == null ? defaultValue : Integer.parseInt(property);
    }
    
    public Long getLong(String key, Long defaultValue) {
        String property = getProperty(key, null);
        return property == null ? defaultValue : Long.parseLong(property);
    }
    
    public Boolean getBoolean(String key, Boolean defaultValue) {
        String property = getProperty(key, null);
        return property == null ? defaultValue : Boolean.parseBoolean(property);
    }

    protected void loadApplicationProperties() {
        ClassLoader contextClassLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream resourceAsStream = contextClassLoader
                .getResourceAsStream(APPLICATION_PROPERTIES);
        Properties applicationProperties = new Properties();
        try {
            applicationProperties.load(resourceAsStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this.applicationProperties = applicationProperties;
    }
    
    
}
