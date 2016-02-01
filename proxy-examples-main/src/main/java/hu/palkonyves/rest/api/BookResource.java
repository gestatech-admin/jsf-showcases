
package hu.palkonyves.rest.api;

import java.util.List;

public interface BookResource {

	
	public List<BookDto> findBook(String author, String title) ;
}
