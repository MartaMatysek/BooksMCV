package pl.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

/**
 * Book controller
 * 
 * @author mmotowid
 *
 */
@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	/**
	 * Method collects info about all books
	 */
	@RequestMapping
	public ModelAndView allBooks() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(ModelConstants.BOOK_LIST, bookService.findAllBooks());
		modelAndView.setViewName(ViewNames.BOOKS);
		return modelAndView;
	}

	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ModelAndView bookDetails(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		BookTo book = bookService.findBooksById(id);

		if (book == null) {
			modelAndView.addObject(ModelConstants.ERROR_MESSAGE, "This book doesn't exist.");
			modelAndView.setViewName(ViewNames._403);
		} else {
			modelAndView.addObject(ModelConstants.BOOK, book);
			modelAndView.setViewName(ViewNames.BOOK);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addNewBook() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("newBook", new BookTo());
		modelAndView.setViewName(ViewNames.ADD_BOOK);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addNewBook(@ModelAttribute("newBook") BookTo book) {
		ModelAndView modelAndView = new ModelAndView();

		if (book.getTitle().isEmpty() || book.getAuthors().isEmpty() || book.getStatus() == null) {
			modelAndView.addObject(ModelConstants.INFO, "You have to complete all fields!");
			modelAndView.setViewName(ViewNames.ADD_BOOK);
		} else {
			bookService.saveBook(book);
			modelAndView.setViewName(ViewNames.CREATED_BOOK);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView findBook() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("newBook", new BookTo());
		modelAndView.setViewName(ViewNames.FIND);
		return modelAndView;
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView findBook(@ModelAttribute("newBook") BookTo book) {
		ModelAndView modelAndView = new ModelAndView();
		List<BookTo> books = bookService.findBooksByAuthorOrTitles(book.getAuthors(), book.getTitle());
		modelAndView.addObject(ModelConstants.BOOK_LIST, books);

		if (books.size() == 0) {
			modelAndView.addObject(ModelConstants.INFO, "There is no such books. Change criteria.");
			modelAndView.setViewName(ViewNames.FIND);
		} else {
			modelAndView.setViewName(ViewNames.BOOKS);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView goToDeleteBook(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();

		if (bookService.findBooksById(id) == null) {
			modelAndView.addObject(ModelConstants.ERROR_MESSAGE, "This book doesn't exist.");
			modelAndView.setViewName(ViewNames._403);
		} else {
			bookService.deleteBook(id);
			modelAndView.setViewName(ViewNames.DELETE_BOOK);
		}

		return modelAndView;
	}

	/**
	 * Binder initialization
	 */
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("id", "title", "authors", "status");
	}

}
