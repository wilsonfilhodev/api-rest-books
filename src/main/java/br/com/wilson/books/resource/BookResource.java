package br.com.wilson.books.resource;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.wilson.books.model.Book;
import br.com.wilson.books.model.BooksDTO;
import br.com.wilson.books.service.BookService;
import br.com.wilson.books.service.exception.BookNotFoundException;

@RestController
@RequestMapping("/books")
public class BookResource {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping
	public ResponseEntity<Book> adicionarServico(@RequestBody Book book, HttpServletResponse res) throws Exception {
		Book bookSaved = bookService.save(book);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(book.getId()).toUri();
		res.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bookSaved);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> findById(@PathVariable("id") String id) throws BookNotFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
	}
	
	@GetMapping()
	public ResponseEntity<BooksDTO> findAllBooks() throws IOException {
		List<Book> books = bookService.extractDataHtml();
		return ResponseEntity.status(HttpStatus.OK).body(new BooksDTO(books.size(), books));
	}

}
