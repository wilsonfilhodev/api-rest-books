package br.com.wilson.books.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.wilson.books.BooksApplicationTests;
import br.com.wilson.books.model.Book;

public class BookRepositoryTest extends BooksApplicationTests {
	
	@Autowired
	private BookRepository sut;
	
	@Test
	public void deve_salvar_um_livro() {
		Book book = sut.save(new Book("Title Book", "Description Book", "ISBN - 1234567891", "BR"));
		
		assertThat(book.getId()).isNotNull();
	}
	
	@Test
	public void deve_buscar_livro_pelo_id() {
		Optional<Book> optionalBook = sut.findById("1ABC");
		
		assertThat(optionalBook.isPresent()).isTrue();
		
		Book book = optionalBook.get();
		
		assertThat(book.getTitle()).isEqualTo("Kotlin em Ação 1");
		assertThat(book.getDescription()).isEqualTo("O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção.");
		assertThat(book.getIsbn()).isEqualTo("1234567891123");
		assertThat(book.getLanguage()).isEqualTo("BR");
	}
	
	@Test
	public void nao_deve_retornar_livro_se_nao_existir_livro_com_id_informado() {
		Optional<Book> optionalBook = sut.findById("1234");
		
		assertThat(optionalBook.isPresent()).isFalse();
	}

}
