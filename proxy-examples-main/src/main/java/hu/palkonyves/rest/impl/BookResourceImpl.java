
package hu.palkonyves.rest.impl;

import hu.palkonyves.rest.api.BookDto;
import hu.palkonyves.rest.api.BookResource;

import java.util.ArrayList;
import java.util.List;

public class BookResourceImpl implements BookResource {
	
	private final String host;
	private final boolean doLog;

	public BookResourceImpl(String host, boolean doLog) {
		this.host = host;
		this.doLog = doLog;
	}
	
	@Override
	public List<BookDto> findBook(String author, String title) {
		if (doLog) {
			System.out.println("Called host '" + host + "'");
		}
		
		ArrayList<BookDto> result = new ArrayList<BookDto>();
		result.add(new BookDto("author1","title1",1999));
		
		return result;
	}

}
