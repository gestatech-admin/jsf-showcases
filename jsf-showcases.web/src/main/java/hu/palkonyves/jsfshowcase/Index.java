package hu.palkonyves.jsfshowcase;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;

@Named
@RequestScoped
public class Index {

    private List<String> pages;

    @PostConstruct
    protected void fetchPages() {
	ExternalContext externalContext = getExternalContext();

	List<String> result = new ArrayList<String>();
	extractXhtmls(externalContext, "/", result);
	pages = result;
    }

    private ExternalContext getExternalContext() {
	FacesContext facesCtx = FacesContext.getCurrentInstance();
	ExternalContext externalContext = facesCtx.getExternalContext();
	return externalContext;
    }

    /**
     * @return list of xhtml pages within the war
     */
    public List<String> getPages() {
	return pages;
    }

    public String getSource(String pageName) throws IOException {

	for (String pageUrl : pages) {
	    if (pageUrl.endsWith(pageName)) {
		ExternalContext externalContext = getExternalContext();
		InputStream resourceAsStream = externalContext.getResourceAsStream(pageUrl);
		return inputStreamToString(resourceAsStream);
	    }
	}

	return null;
    }

    private String inputStreamToString(InputStream inputStream)
	    throws IOException {
	StringWriter writer = new StringWriter();
	IOUtils.copy(inputStream, writer, "UTF8");
	return writer.toString();

    }

    /**
     * recursively extracth .xhtml pages from any directories
     * 
     * @param externalContext
     * @param pathPrefix
     * @param result
     */
    private void extractXhtmls(ExternalContext externalContext,
	    String pathPrefix, List<String> result) {

	Set<String> resourcePaths = externalContext
		.getResourcePaths(pathPrefix);

	for (String path : resourcePaths) {

	    if (path.endsWith(".xhtml")) {
		// if xhtml, append

		result.add(path);
	    } else if (path.endsWith("/")) {
		// if directory, recurse

		extractXhtmls(externalContext, path, result);
	    }

	}
    }
}
