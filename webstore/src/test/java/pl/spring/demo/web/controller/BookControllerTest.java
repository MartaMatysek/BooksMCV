package pl.spring.demo.web.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.controller.BookController;
import pl.spring.demo.enumerations.BookStatus;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

	@Mock
	private BookService bookService;

	@InjectMocks
	private BookController bookController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void shouldReturnAllBooks() throws Exception {
		// given
		List<BookTo> books = new ArrayList<>();
		when(bookService.findAllBooks()).thenReturn(books);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books"));

		// then
		resultActions.andExpect(view().name("books")).andExpect(model().attribute(ModelConstants.BOOK_LIST, books));
		verify(bookService).findAllBooks();
	}

	@Test
	public void shouldCheckBookById() throws Exception {
		// given
		BookTo book = new BookTo();
		when(bookService.findBooksById(1L)).thenReturn(book);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book/").param("id", "1"));

		// then
		resultActions.andExpect(view().name("book")).andExpect(model().attribute(ModelConstants.BOOK, book));
		verify(bookService).findBooksById(1L);
	}

	@Test
	public void shouldCheckDoesNotExistBookById() throws Exception {
		// given
		when(bookService.findBooksById(1L)).thenReturn(null);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book/").param("id", "1"));

		// then
		resultActions.andExpect(view().name("403"))
				.andExpect(model().attribute(ModelConstants.ERROR_MESSAGE, "This book doesn't exist."));
		verify(bookService).findBooksById(1L);
	}

	@Test
	public void shouldAddBook() throws Exception {
		// given when
		ResultActions resultActions = mockMvc.perform(get("/books/add"));

		// then
		resultActions.andExpect(view().name("addBook"));
	}

	@Test
	public void shouldCheckPostAddBooksMethod() throws Exception {
		// given
		BookTo book = new BookTo();
		book.setAuthors("A");
		book.setTitle("S");
		book.setStatus(BookStatus.FREE);

		BookTo savedBook = new BookTo();
		savedBook.setId(1L);
		savedBook.setAuthors("A");
		savedBook.setTitle("S");
		savedBook.setStatus(BookStatus.FREE);

		when(bookService.saveBook(book)).thenReturn(savedBook);

		// when
		ResultActions resultActions = mockMvc.perform(post("/books/add").flashAttr("newBook", book));

		// then
		resultActions.andExpect(view().name("createdBook"));
		verify(bookService).saveBook(book);
	}

	@Test
	public void shouldCheckPostAddMethodForWrongInternalData() throws Exception {
		// given
		BookTo book = new BookTo();
		book.setAuthors("A");
		book.setTitle("");
		book.setStatus(BookStatus.FREE);

		// when
		ResultActions resultActions = mockMvc.perform(post("/books/add").flashAttr("newBook", book));

		// then
		resultActions.andExpect(view().name("addBook"));
	}

	@Test
	public void shouldFindBooks() throws Exception {
		// given when
		ResultActions resultActions = mockMvc.perform(get("/books/find").param("A", "S"));

		// then
		resultActions.andExpect(view().name("find"));
	}

	@Test
	public void shouldCheckPostFindBooksMethod() throws Exception {
		// given
		BookTo book = new BookTo();
		book.setId(1L);
		book.setAuthors("A");
		book.setTitle("S");
		book.setStatus(BookStatus.FREE);

		when(bookService.findBooksByAuthorOrTitles("A", "S")).thenReturn(Arrays.asList(book));

		// when
		ResultActions resultActions = mockMvc.perform(post("/books/find").flashAttr("newBook", book));

		// then
		resultActions.andExpect(view().name("books"));
		verify(bookService).findBooksByAuthorOrTitles("A", "S");
	}

	@Test
	public void shouldCheckPostFindMethodForDoesNotExistBooks() throws Exception {
		// given
		List<BookTo> books = new ArrayList<>();

		BookTo book = new BookTo();
		book.setAuthors("A");
		book.setTitle("S");
		book.setStatus(BookStatus.FREE);

		when(bookService.findBooksByAuthorOrTitles("A", "S")).thenReturn(books);

		// when
		ResultActions resultActions = mockMvc.perform(post("/books/find").flashAttr("newBook", book));

		// then
		resultActions.andExpect(view().name("find"));
		verify(bookService).findBooksByAuthorOrTitles("A", "S");
	}

	@Test
	public void shouldDeleteBook() throws Exception {
		// given
		when(bookService.findBooksById(1L)).thenReturn(new BookTo());

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete/").param("id", "1"));

		// then
		resultActions.andExpect(view().name("delete"));
		verify(bookService).findBooksById(1L);
	}

	@Test
	public void shouldThrowErrorDuringDeleteBookWhichDoesNotExist() throws Exception {
		// given
		when(bookService.findBooksById(1L)).thenReturn(null);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete/").param("id", "1"));

		// then
		resultActions.andExpect(view().name("403"))
				.andExpect(model().attribute(ModelConstants.ERROR_MESSAGE, "This book doesn't exist."));
		verify(bookService).findBooksById(1L);
	}

}