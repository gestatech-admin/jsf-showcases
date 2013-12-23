package hu.palkonyves.jsfshowcase;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Topics {

    private static final Pattern H1_PATTERN = Pattern
            .compile(".*\\<h1\\>(.*)\\</h1\\>.*");

    @Inject
    SourceCodeReader sourceCodeReader;

    private List<String[]> topicsCache;

    @PostConstruct
    void init() {
        topicsCache = fetchTopics();
    }

    /**
     * @return String[]{ pageUrl, topic } from cache
     */
    public List<String[]> getTopics() {
        return topicsCache;
    }

    /**
     * @return String[]{ pageUrl, topic }
     */
    private List<String[]> fetchTopics() {
        List<String[]> result = new LinkedList<String[]>();

        for (String pageUrl : getXhtmlPages()) {
            String source = sourceCodeReader.getFileSource(pageUrl);
            String topic = getTopic(source);
            System.out.println(topic);

            if (topic != null) {
                String[] resultLink = new String[] { pageUrl, topic };
                result.add(resultLink);
            }
        }

        return result;
    }


    /**
     * Impl: Finds the first &lt;h1&gt; tag on the page and returns it
     * 
     * @param source
     * @return
     */
    private String getTopic(String source) {

        String[] lines = source.split("\\r?\\n");
        for (String line : lines) {
            Matcher h1Matcher = H1_PATTERN.matcher(line);
            if (h1Matcher.matches()) {
                return h1Matcher.group(1);
            }
        }

        return null;
    }

    /**
     * Retrieve only pages ending to .xhtml
     * 
     * @return
     */
    private List<String> getXhtmlPages() {
        List<String> result = new LinkedList<String>();
        for (String pageUrl : sourceCodeReader.getPages()) {
            if (pageUrl.endsWith(".xhtml")) {
                result.add(pageUrl);
            }
        }

        return result;
    }
}
