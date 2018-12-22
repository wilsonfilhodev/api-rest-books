package br.com.wilson.books.service.exception;

public class RequiredFieldException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequiredFieldException(String message) {
		super(message);
	}

}
