package hu.palkonyves.jsfshowcase;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
			String source;

			try {
				source = sourceCodeReader.getFileSource(pageUrl);
				String topic = getTopic(source);
				if (topic != null) {
					String[] resultLink = new String[] { pageUrl, topic };
					result.add(resultLink);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		Collections.sort(result, new Comparator<String[]>() {

			@Override
			public int compare(String[] arg0, String[] arg1) {
				String topic0 = arg0[1];
				String topic1 = arg1[1];
				if (topic0 != null && topic1 != null) {
					return topic0.compareTo(topic1);
				} else {
					return 0;
				}
			}

		});

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
