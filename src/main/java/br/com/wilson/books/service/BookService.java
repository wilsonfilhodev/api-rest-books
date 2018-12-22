package br.com.wilson.books.service;

import br.com.wilson.books.model.Book;
import br.com.wilson.books.service.exception.BookNotFoundException;

public interface BookService {

	Book save(Book book) throws Exception;

	Book findById(String id) throws BookNotFoundException;

}
