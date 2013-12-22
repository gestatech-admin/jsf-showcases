package hu.palkonyves.jsfshowcase;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;

@Named
@ApplicationScoped
public class SourceCodeReader {

    /**
     * file extensions we are interested in
     */
    private Set<String> sourceExtensions;

    {
        Set<String> extensions = new HashSet<String>();
        extensions.add(".xhtml");
        extensions.add(".java");
        extensions.add(".html");

        sourceExtensions = Collections.unmodifiableSet(extensions);
    }

    /**
     * list of sources with {@link #sourceExtensions} postfixes
     */
    private List<String> sources;

    /**
     * Retrieve .xhtml and .java files on @PostConstruct
     */
    @PostConstruct
    protected void fetchPages() {
        ExternalContext externalContext = getExternalContext();

        List<String> result = new ArrayList<String>();
        findSources(externalContext, "/", result);
        sources = result;
    }

    /**
     * @return FacesContext#getExternalSource()
     */
    private ExternalContext getExternalContext() {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesCtx.getExternalContext();
        return externalContext;
    }

    /**
     * recursively find and bookmark .xhtml and .java pages from any directories
     * 
     * @param externalContext
     * @param pathPrefix
     * @param result
     */
    private void findSources(ExternalContext externalContext,
            String pathPrefix, List<String> result) {

        Set<String> resourcePaths = externalContext
                .getResourcePaths(pathPrefix);

        for (String path : resourcePaths) {
            if (isSourceExtension(path)) {
                result.add(path);
            } else if (path.endsWith("/")) {
                // directory, go deeper
                findSources(externalContext, path, result);
            }
        }
    }

    /**
     * Determines whether or not the file ends with a source file extensions
     * 
     * @param path
     * @return true if source file
     */
    private boolean isSourceExtension(String path) {
        int lastIndexOfDot = path.lastIndexOf(".");
        if (lastIndexOfDot < 0) {
            return false;
        }
        String pathExtension = path.substring(lastIndexOfDot);
        return sourceExtensions.contains(pathExtension);
    }

    /**
     * @return list of xhtml pages within the war
     */
    public List<String> getPages() {
        return sources;
    }

    /**
     * Returns the whole source code of a file
     * 
     * @param pageName
     * @return whole String of the file
     */
    public String getFileSource(String pageName) {

        String pageUrl = getSourceFilePath(pageName);
        ExternalContext externalContext = getExternalContext();
        InputStream resourceAsStream = externalContext.getResourceAsStream(pageUrl);
        try {
            return inputStreamToString(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("could not retrieve source of '"
                    + pageUrl + "'", e);
        }
    }

    /**
     * @param file
     * @return absolute path of file with name
     */
    private String getSourceFilePath(String file) {
        for (String pageUrl : sources) {
            if (pageUrl.endsWith(file)) {
                return pageUrl;
            }
        }

        throw new RuntimeException("could not retrieve source '" + file
                + "'");
    }

    /**
     * Converts an inputstream to String
     * 
     * @param inputStream
     * @return String representation of an inputStream
     * @throws IOException
     */
    private String inputStreamToString(InputStream inputStream)
            throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF8");
        return writer.toString();

    }

    private static enum SnippetState {
        FIRSTLINE, WITHIN, OUT
    }

    private static final String SNIPPET_START_XHTML = "<!-- snippet=";
    private static final String SNIPPET_END_XHTML = "<!-- endsnippet";

    private static final String SNIPPET_START_JAVA = "// snippet=";
    private static final String SNIPPET_END_JAVA = "// endsnippet";

    /**
     * Returns a code snippet referenced by path/to/file.java?2
     * 
     * @param key
     * @return
     */
    public String getSnippet(String key) {
        String[] key0 = snippet(key);
        String pageName = key0[0];

        if (key0.length < 2) {
            return getFileSource(pageName);
        }

        String number = key0[1];

        SnippetState withinSnippet = SnippetState.OUT;
        String startPattern = getStartPattern(pageName) + number;
        String endPattern = getEndPattern(pageName);

        StringBuilder result = new StringBuilder();
        int indentation = 0;

        String[] lines = getFileSource(pageName).split("\\r?\\n");

        FOREACH_LINE: for (String line : lines) {
            switch (withinSnippet) {
            case OUT:
                if (line.contains(startPattern)) {
                    withinSnippet = SnippetState.FIRSTLINE;
                }
                break;
            case FIRSTLINE:
                indentation = firstLineIndentation(line);
                withinSnippet = SnippetState.WITHIN;
                // result.append(indentation).append("\n");
                // just flow toward the WITHIN state
            case WITHIN:
                if (line.contains(endPattern)) {
                    // end, break out of the for
                    break FOREACH_LINE;
                } else {
                    // append line to end
                    line = removeIndentation(indentation, line);
                    result.append(line).append("\n");
                }
                break;
            }
        }

        return result.toString();
    }

    private int firstLineIndentation(String line) {
        int count = 0;
        while (Character.isWhitespace(line.charAt(count))) {
            count++;
        }
        return count;
    }

    private String removeIndentation(int indentation, String line) {
        return line.substring(indentation);
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
