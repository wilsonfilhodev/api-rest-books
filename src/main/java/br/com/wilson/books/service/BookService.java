package br.com.wilson.books.service;

import java.io.IOException;
import java.util.List;

import br.com.wilson.books.model.Book;
import br.com.wilson.books.service.exception.BookNotFoundException;

public interface BookService {

	Book save(Book book) throws Exception;

	Book findById(String id) throws BookNotFoundException;
	
	List<Book> extractDataHtml() throws IOException;

}
