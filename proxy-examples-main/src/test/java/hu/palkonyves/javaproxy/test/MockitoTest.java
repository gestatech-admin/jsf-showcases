
package hu.palkonyves.javaproxy.test;

import org.junit.Assert;

import hu.palkonyves.mockito.Mock;
import hu.palkonyves.mockito.ResultRegistrator;
import hu.palkonyves.rest.api.BookDto;
import hu.palkonyves.rest.api.BookResource;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

public class MockitoTest {

	/**
	 * Simple excercise to use the Mockito framework
	 */
	@Test
	public void real_mockito_test() {

		// Setting up Mock object
		BookResource bookMock = Mockito.mock(BookResource.class);
		ArrayList<BookDto> expectedResult = new ArrayList<BookDto>();
		ArrayList<BookDto> expectedResult2 = new ArrayList<BookDto>();

		bookMock.findBook("one", "two");
		Mockito.when(null /* it is only eyecandy */).thenReturn(expectedResult);

		Mockito.when(bookMock.findBook("one2", "two2")).thenReturn(expectedResult2);

		// Trying out
		List<BookDto> actResult = bookMock.findBook("one", "two");
		Assert.assertEquals(expectedResult, actResult);
		
		// Trying out
		List<BookDto> actResult2 = bookMock.findBook("one2", "two2");
		Assert.assertEquals(expectedResult2, actResult2);
	}

	/**
	 * Example of misusing Mockito framework.
	 * An exception will be thrown if we forget to call the <i>thenReturn</i> method
	 */
	@Test
	public void real_mockito_missuse_test() {
		BookResource bookMock = Mockito.mock(BookResource.class);
		ArrayList<BookDto> expectedResult = new ArrayList<BookDto>();

		try {
			OngoingStubbing<List<BookDto>> returnStub1 = Mockito.when(bookMock.findBook("one",
					"two"));

			OngoingStubbing<List<BookDto>> returnStub2 = Mockito.when(bookMock.findBook("one",
					"two"));
			returnStub1.thenReturn(expectedResult); // how to implement this error
			
			Assert.fail("Mockito exception is expected");
		}
		catch (Exception e) {
			System.out.println("OK, mockito exception was thrown");
		}
	}

	/**
	 * Testing our own implementation of Mockito
	 */
	@Test
	public void my_mockito_test() {
		BookResource bookMock = Mock.mock(BookResource.class);
		ArrayList<BookDto> expectedResult = new ArrayList<BookDto>();
		ArrayList<BookDto> expectedResult2 = new ArrayList<BookDto>();

		Mock.when(bookMock.findBook("one", "two")).thenReturn(expectedResult);
		Mock.when(bookMock.findBook("one2", "two2")).thenReturn(expectedResult2);
		
		List<BookDto> actResult = bookMock.findBook("one", "two");
		Assert.assertEquals(expectedResult, actResult);
		
		List<BookDto> actResult2 = bookMock.findBook("one2", "two2");
		Assert.assertEquals(expectedResult2, actResult2);
	}

	/**
	 * Testing misuse of our own implementation of Mockito.
	 * Exception must be thrown when we forget to call <i>thenReturn</i> method
	 */
	@Test
	public void my_mockito_missuse_test() {
		BookResource bookMock = Mock.mock(BookResource.class);

		ResultRegistrator when = null;
		try {
			when = Mock.when(bookMock.findBook("one", "two"));
			ResultRegistrator when2 = Mock.when(bookMock.findBook("one2", "two2"));

			Assert.fail("Mockito exception is expected");
		}
		catch (Exception e) {
			System.out.println("OK, mockito exception was thrown");
			
			// fix this call, otherwise we cannot proceed with the other tests
			when.thenReturn(new ArrayList<BookDto>());
		}
	}
	
}
