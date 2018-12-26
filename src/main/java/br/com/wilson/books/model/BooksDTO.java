package br.com.wilson.books.model;

import java.util.List;

public class BooksDTO {
	
	private Integer numberBooks;
	
	private List<Book> books;
	
	public BooksDTO() {
	}
	
	public BooksDTO(Integer numberBooks, List<Book> books) {
		this.numberBooks = numberBooks;
		this.books = books;
	}
	public Integer getNumberBooks() {
		return numberBooks;
	}
	public void setNumberBooks(Integer numberBooks) {
		this.numberBooks = numberBooks;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
}
