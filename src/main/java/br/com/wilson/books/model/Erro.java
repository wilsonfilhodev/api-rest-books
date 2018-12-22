package br.com.wilson.books.model;

public class Erro {

	private String message;

	public Erro(String message) {
		this.message = message;
	}

	public Erro() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Error [message=" + message + "]";
	}
	
}
