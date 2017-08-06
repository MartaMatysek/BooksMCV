package pl.spring.demo.service;

import pl.spring.demo.to.BookTo;

import java.util.List;

public interface BookService {

    List<BookTo> findAllBooks();
    List<BookTo> findBooksByTitle(String title);
    List<BookTo> findBooksByAuthor(String author);
    BookTo findBooksById(Long id);
    List<BookTo> findBooksByAuthorOrTitles(String author, String title);

    BookTo saveBook(BookTo book);
    void deleteBook(Long id);
}
