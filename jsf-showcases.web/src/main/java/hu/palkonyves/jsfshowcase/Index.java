package hu.palkonyves.jsfshowcase;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, String> snippetToPages = new HashMap<String, String>();

    @PostConstruct
    protected void fetchPages() {
        ExternalContext externalContext = getExternalContext();

        List<String> result = new ArrayList<String>();
        extractSources(externalContext, "/", result);
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

    public String getSource(String pageName) {

        for (String pageUrl : pages) {
            if (pageUrl.endsWith(pageName)) {
                ExternalContext externalContext = getExternalContext();
                InputStream resourceAsStream = externalContext
                        .getResourceAsStream(pageUrl);
                try {
                    return inputStreamToString(resourceAsStream);
                } catch (IOException e) {
                    throw new RuntimeException("could not retrieve source ", e);
                }
            }
        }

        throw new RuntimeException("could not retrieve source '" + pageName
                + "'");
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
    private void extractSources(ExternalContext externalContext,
            String pathPrefix, List<String> result) {

        Set<String> resourcePaths = externalContext
                .getResourcePaths(pathPrefix);

        for (String path : resourcePaths) {

            if (path.endsWith(".xhtml") || path.endsWith(".java")) {
                // if xhtml, append

                result.add(path);
            } else if (path.endsWith("/")) {
                // if directory, recurse

                extractSources(externalContext, path, result);
            }

        }
    }

    private static enum SnippetState {
        WITHIN, OUT
    }

    private static final String SNIPPET_START_XHTML = "<!-- snippet=";
    private static final String SNIPPET_END_XHTML = "<!-- endsnippet";

    private static final String SNIPPET_START_JAVA = "// snippet=";
    private static final String SNIPPET_END_JAVA = "// endsnippet";

    public String getSnippet(String key) {
        String[] key0 = snippet(key);
        String pageName = key0[0];
        String number = null;

        if (key0.length > 1) {
            number = key0[1];
        } else {
            return getSource(pageName);
        }

        SnippetState withinSnippet = SnippetState.OUT;
        String startPattern = getStartPattern(pageName) + number;
        String endPattern = getEndPattern(pageName);

        StringBuilder result = new StringBuilder();

        String[] lines = getSource(pageName).split("\\r?\\n");

        for (String line : lines) {
            switch (withinSnippet) {
            case OUT:
                if (line.contains(startPattern)) {
                    withinSnippet = SnippetState.WITHIN;
                }
                break;
            case WITHIN:
                if (line.contains(endPattern)) {
                    return result.toString();
                } else {
                    result.append(line).append("\n");
                }
                break;
            }
        }

        return "";
    }

    private static String getStartPattern(String fileName) {
        return (fileName.contains("xhtml")) ? SNIPPET_START_XHTML
                : SNIPPET_START_JAVA;
    }

    private static String getEndPattern(String fileName) {
        return (fileName.contains("xhtml")) ? SNIPPET_END_XHTML
                : SNIPPET_END_JAVA;
    }

    private String[] snippet(String key) {
        return key.split("\\?");
    }
}
