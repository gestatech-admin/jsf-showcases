package hu.palkonyves.jsfshowcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@SessionScoped
public class DynamicTemplateExample implements java.io.Serializable {

	private static List<SelectItem> templates;
	private String selectedTemplate = (String) templates.get(0).getValue();

	static {
		SelectItem item1 = new SelectItem(
				"/resources/templates/dynamicTemplate1.xhtml",
				"dynamicTemplate1");
		SelectItem item2 = new SelectItem(
				"/resources/templates/dynamicTemplate2.xhtml",
				"dynamicTemplate2");

		List<SelectItem> tmpls = new ArrayList<SelectItem>();
		tmpls.add(item1);
		tmpls.add(item2);

		DynamicTemplateExample.templates = Collections.unmodifiableList(tmpls);
	}

	protected DynamicTemplateExample() {

	}

	public List<SelectItem> getTemplates() {
		return templates;
	}

	public String getSelectedTemplate() {
		return selectedTemplate;
	}

	public void setSelectedTemplate(String template) {
		selectedTemplate = template;
	}
}
