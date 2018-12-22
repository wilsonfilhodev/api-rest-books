package br.com.wilson.books.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.wilson.books.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

	Optional<Book> findById(String id);

}
