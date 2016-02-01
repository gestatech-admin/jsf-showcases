
package hu.palkonyves.rest.api;

public class BookDto {

	private String author;
	private String title;
	private int published;
	
	public BookDto(String author, String title, int published) {
		this.author = author;
		this.title = title;
		this.published = published;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPublished() {
		return published;
	}

	public void setPublished(int published) {
		this.published = published;
	}

	/**
	 * @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "BookDto [author=" + author + ", title=" + title + ", published=" + published + "]";
	}
	
	
}
