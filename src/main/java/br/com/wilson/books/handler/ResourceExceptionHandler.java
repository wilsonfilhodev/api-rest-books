package br.com.wilson.books.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.wilson.books.model.Erro;
import br.com.wilson.books.service.exception.BookNotFoundException;
import br.com.wilson.books.service.exception.RequiredFieldException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler({ BookNotFoundException.class })
	public ResponseEntity<Erro> handleBookNotFoundException(BookNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(e.getMessage()));
	}
	
	@ExceptionHandler({ RequiredFieldException.class })
	public ResponseEntity<Erro> handleRequiredFieldException(RequiredFieldException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erro(e.getMessage()));
	}
	

}
