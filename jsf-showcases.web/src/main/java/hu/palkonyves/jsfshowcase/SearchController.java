package hu.palkonyves.jsfshowcase;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SearchController {

	private String searchString = "";
	private SourceCodeReader sourceCodeReader;
	private List<SelectItem> resultList = new LinkedList<SelectItem>();

	/**
	 * Satisfy CDI proxyable capability
	 */
	protected SearchController() {

	}

	@Inject
	public SearchController(SourceCodeReader sourceCodeReader) {
		this.sourceCodeReader = sourceCodeReader;
	}

	/**
	 * Populate resultList with matching pages
	 */
	public void searchAction() {
		List<String> pages = sourceCodeReader.getPages();
		resultList.clear();

		for (String page : pages) {
			if (page.contains(searchString)) {
				SelectItem item = new SelectItem(page, page);
				resultList.add(item);
			}
		}
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/**
	 * Returns a comma separated list of search hints. It's calculated on the
	 * fly
	 * 
	 * @return comma separated list of hints
	 */
	public String getAutoCompleteHints() {
		List<String> pages = sourceCodeReader.getPages();
		StringBuffer b = new StringBuffer("");

		for (String page : pages) {
			if (page.contains(searchString)) {
				// append if contains searchString
				b.append(page);
				b.append(",");
			} else if (" ".equals(searchString)) {
				// append if searchString is space
				b.append(page);
				b.append(",");
			}
		}

		// remove last comma
		int bLength = b.length();
		if (bLength > 0) {
			b.delete(bLength - 1, bLength);
		}
		if (b.length() > 0) {
			return b.toString();
		} else {
			return "";
		}
	}

	/**
	 * Do nothing, only used when a full request or an ajax request is made that
	 * calls the setter of autoCompleteHints property, so there won't be any
	 * runtime exceptions.
	 * 
	 * @param hints
	 */
	public void setAutoCompleteHints(String hints) {

	}

	public List<SelectItem> getResultList() {
		return resultList;
	}

}
