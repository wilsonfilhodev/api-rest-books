package br.com.wilson.books.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	
	@Override
	public List<Book> extractDataHtml() throws IOException {
		Book book = new Book();
		List<Book> books = new ArrayList<>();
		
		Document doc = Jsoup.connect("https://kotlinlang.org/docs/books.html").get();
		Elements elements = doc.select("article.page-content *");
		
		for (Element element : elements) {
			
			book = addObjectBookInListAndCreateNew(book, books, element);
			
			if (element.tagName().equals("h2")) {
				book.setTitle(element.html());
			} else if (element.className().equals("book-lang")) {
				book.setLanguage(element.html().toUpperCase());
				Element nextElement = elements.get(elements.indexOf(element) + 1);
				book.setHref(nextElement.attr("href"));
			} else if (element.tagName().equals("p")) {
				book.setDescription(StringUtils.hasText(book.getDescription()) ? book.getDescription().concat(" "+element.text()) : element.text());
			}
			
		}
		
		books.add(book);
		
		return books;
	}

	private Book addObjectBookInListAndCreateNew(Book book, List<Book> books, Element row) {
		if (row.tagName().equals("h2") && StringUtils.hasText(book.getTitle())) {
			books.add(book);
			book = new Book();
		}
		return book;
	}

}
