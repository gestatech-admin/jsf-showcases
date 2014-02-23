package hu.palkonyves.jsfshowcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class DynamicTemplateExample implements java.io.Serializable {

    private static List<String> templates;
    private String selectedTemplate = templates.get(0);

    static {
        List<String> tmpls = new ArrayList<String>();
        tmpls.add("/resources/templates/dynamicTemplate1.xhtml");
        tmpls.add("/resources/templates/dynamicTemplate2.xhtml");

        DynamicTemplateExample.templates = Collections
                .unmodifiableList(tmpls);
    }

    protected DynamicTemplateExample() {

    }

    public List<String> getTemplates() {
        return templates;
    }

    public String getSelectedTemplate() {
        return selectedTemplate;
    }

    public void setSelectedTemplate(String template) {
        selectedTemplate = template;
    }

}
