package br.com.wilson.books.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.wilson.books.model.Book;
import br.com.wilson.books.repository.BookRepository;
import br.com.wilson.books.service.exception.BookNotFoundException;
import br.com.wilson.books.service.exception.RequiredFieldException;
import br.com.wilson.books.utils.JsoupUtils;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book save(Book book) throws RequiredFieldException {
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
		return optionalBooks.orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
	}

	@Override
	public List<Book> findAllBooks() throws IOException {
		verifyBookWebExistInDataBaseAndSaveIfNotExist();
		return bookRepository.findAll();
	}
	
	@Override
	public void deleteAllBooks() {
		bookRepository.deleteAll();
	}
	
	@Override
	public void deleteById(String id) throws BookNotFoundException {
		findById(id);
		bookRepository.deleteById(id);
	}

	private void verifyBookWebExistInDataBaseAndSaveIfNotExist() throws IOException {
		List<Book> booksWebPage = getBooksWebPage();
		List<Book> booksDataBase = bookRepository.findAll();
		
		booksWebPage.parallelStream().forEach(bookWebPage -> {
			boolean newBook = true;
			newBook = booksDataBase.stream().noneMatch(obj -> bookWebPage.getHref().equals(obj.getHref()));
			if (newBook) {
				bookRepository.save(bookWebPage);
			}
		});
		
	}

	private List<Book> getBooksWebPage() throws IOException {
		Book book = new Book();
		List<Book> books = new ArrayList<>();
		Element element;

		// connect to the website and get the HTML document
		String url = "https://kotlinlang.org/docs/books.html";
		Document doc = JsoupUtils.parseHtmlToDoc(url);

		// select all elements child of class page-content
		Elements elements = doc.select("article.page-content *");

		// iterate elements and creates objects books
		for (int i = 0; i < elements.size(); i++) {
			element = elements.get(i);
			book = addObjectBookInListAndCreateNew(book, books, element);

			if (element.tagName().equals("h2")) {
				book.setTitle(element.html());
			} else if (element.className().equals("book-lang")) {
				book.setLanguage(element.html().toUpperCase());
				Element nextElement = elements.get(elements.indexOf(element) + 1);
				book.setHref(nextElement.attr("href"));
			} else if (element.tagName().equals("p")) {
				book.setDescription(StringUtils.hasText(book.getDescription()) ? 
						book.getDescription().concat(" " + element.text()) : element.text());
			}
		}

		books.add(book);
		
		findISBNAndSetInBook(books);
		return books;
	}

	private void findISBNAndSetInBook(List<Book> books) {
		books.parallelStream().forEach(book -> {
			try {
				Document doc = JsoupUtils.parseHtmlToDoc(book.getHref());
				Element element = doc
				.select("body li:matches((?i)isbn)," + "body h2:matches((?i)isbn)," + "body [itemprop=isbn]").first();

				// adds content in the ISBN attribute
				if (element != null) {
					String[] parts = element.text().split(" ");
					String lastPart = parts[parts.length - 1];
					String onlyNumber = lastPart.replaceAll("\\D+", "");
					book.setIsbn(onlyNumber);
				} else {
					book.setIsbn("Unavailable");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}


	private Book addObjectBookInListAndCreateNew(Book book, List<Book> books, Element row) {
		if (row.tagName().equals("h2") && StringUtils.hasText(book.getTitle())) {
			books.add(book);
			book = new Book();
		}
		return book;
	}

}
