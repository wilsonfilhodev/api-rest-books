package br.com.wilson.books.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.wilson.books.BooksApplicationTests;
import br.com.wilson.books.model.Book;
import br.com.wilson.books.repository.BookRepository;
import br.com.wilson.books.service.exception.BookNotFoundException;
import br.com.wilson.books.service.exception.RequiredFieldException;

public class BookServiceTest extends BooksApplicationTests {

	private static final String ID = "1AAA";
	private static final String TITLE = "Kotlin em Ação 1";
	private static final String DESCRIPTION = "O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção.";
	private static final String ISBN = "1234567891123";
	private static final String LANGUAGE = "BR";

	private BookService sut;
	
	private Book book;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	private BookRepository bookRepository;
	
	@Before
	public void setup() {
		sut = new BookServiceImpl(bookRepository);
		book = new Book(ID, TITLE, DESCRIPTION, ISBN, LANGUAGE);
	}
	
	@Test
	public void deve_salvar_um_livro_no_repository() throws Exception {
		this.sut.save(book);
		
		verify(bookRepository).save(book);
	}
	
	@Test
	public void deve_retornar_excessao_ao_tentar_salvar_um_livro_sem_nome() throws Exception {
		thrown.expect(RequiredFieldException.class);
		thrown.expectMessage("Field 'title' is required");
		
		book.setTitle("");
		this.sut.save(book);
	}
	
	@Test
	public void deve_retornar_excessao_ao_tentar_salvar_um_livro_sem_descrição() throws Exception {
		thrown.expect(RequiredFieldException.class);
		thrown.expectMessage("Field 'description' is required");
		
		book.setDescription("");
		this.sut.save(book);
	}
	
	@Test
	public void deve_retornar_excessao_ao_tentar_salvar_um_livro_sem_isbn() throws Exception {
		thrown.expect(RequiredFieldException.class);
		thrown.expectMessage("Field 'ISBN' is required");
		
		book.setIsbn("");
		this.sut.save(book);
	}
	
	@Test
	public void deve_retornar_excessao_ao_tentar_salvar_um_livro_sem_linguagem() throws Exception {
		thrown.expect(RequiredFieldException.class);
		thrown.expectMessage("Field 'language' is required");
		
		book.setLanguage("");
		this.sut.save(book);
	}
	
	@Test
	public void deve_buscar_livro_pelo_id() throws Exception {
		when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
		
		Book bookTeste = sut.findById(ID);
		
		verify(bookRepository).findById(ID);
		
		assertThat(bookTeste).isNotNull();
		assertThat(bookTeste.getId()).isEqualTo(ID);
		assertThat(book.getTitle()).isEqualTo(TITLE);
		assertThat(book.getDescription()).isEqualTo(DESCRIPTION);
		assertThat(book.getIsbn()).isEqualTo(ISBN);
		assertThat(book.getLanguage()).isEqualTo(LANGUAGE);
	}
	
	@Test
	public void deve_retornar_excessao_de_nao_encontrado_quando_nao_existir_livro_com_id_informado() throws Exception {
		thrown.expect(BookNotFoundException.class);
		thrown.expectMessage("Book with id 1234 not found");
		
		sut.findById("1234");
	}
	
	@Test
	public void deve_retornar_todos_os_livros_da_pagina_html() throws IOException {
		Document doc = Jsoup.connect("https://kotlinlang.org/docs/books.html").get();
		Elements titles = doc.select("article.page-content h2");
		Elements languages = doc.select("div.book-lang");
		
		for (int i = 0; i < titles.size(); i++) {
			System.out.println("Title: "+titles.get(i).text());
			System.out.println("Language: "+languages.get(i).text().toUpperCase());
		}
	}
	
}
