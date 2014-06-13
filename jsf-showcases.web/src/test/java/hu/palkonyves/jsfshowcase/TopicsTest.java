package hu.palkonyves.jsfshowcase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TopicsTest {

	private static final Pattern H1_PATTERN = Pattern
			.compile("\\<h1\\>(.*)\\</h1\\>");

	private static final String TOPIC = "toptop";

	private final String source = "<html>\n" + "<body>\n"
			+ "<div> something </div>\n" + "<h1>" + TOPIC + "</h1>\n"
			+ "</body>\n" + "</html>";

	@Test
	public void getTopicsTest() {

		String[] lines = source.split("\\r?\\n");
		String matching = null;
		for (String line : lines) {
			Matcher h1Matcher = H1_PATTERN.matcher(line);
			if (h1Matcher.matches()) {
				matching = h1Matcher.group(1);
			}
		}

		System.out.println(matching);
		Assert.assertEquals(TOPIC, matching);
	}

}
