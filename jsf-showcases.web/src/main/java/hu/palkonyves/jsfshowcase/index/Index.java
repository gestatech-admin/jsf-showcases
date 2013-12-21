package hu.palkonyves.jsfshowcase.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class Index {

    /**
     * @return list of xhtml pages within the war
     */
    public List<String> getPages() {
	FacesContext facesCtx = FacesContext.getCurrentInstance();
	ExternalContext externalContext = facesCtx.getExternalContext();

	List<String> result = new ArrayList<String>();
	extractXhtmls(externalContext, "/", result);
	return result;
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
