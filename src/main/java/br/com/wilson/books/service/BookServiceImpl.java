package br.com.wilson.books.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.wilson.books.model.Book;
import br.com.wilson.books.repository.BookRepository;
import br.com.wilson.books.service.exception.BookNotFoundException;
import br.com.wilson.books.service.exception.RequiredFieldException;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book save(Book book) throws Exception {
		if (!StringUtils.hasText(book.getTitle())) {
			throw new RequiredFieldException("Field 'title' is required");
		}
		
		if (!StringUtils.hasText(book.getDescription())) {
			throw new RequiredFieldException("Field 'description' is required");
		}
		
		if (!StringUtils.hasText(book.getIsbn())) {
			throw new RequiredFieldException("Field 'ISBN' is required");
		}
		
		if (!StringUtils.hasText(book.getLanguage())) {
			throw new RequiredFieldException("Field 'language' is required");
		}
		
		return bookRepository.save(book);
	}

	@Override
	public Book findById(String id) throws BookNotFoundException {
		Optional<Book> optionalBooks = bookRepository.findById(id);
		return optionalBooks.orElseThrow(() -> new BookNotFoundException("Book with id "+id+" not found"));
	}

}
