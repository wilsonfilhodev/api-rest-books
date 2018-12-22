package br.com.wilson.books;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.wilson.books.model.Book;
import br.com.wilson.books.repository.BookRepository;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@EnableMongoRepositories
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class BooksApplicationTests {
	
	@Value("${local.server.port}")
	protected int port;
	
	@Autowired
	private BookRepository bookRepository;

	@Test
	public void contextLoads() {
	}
	
	@Before
	public void setup() {
		RestAssured.port = port;
		initDataBase();
	}

	private void initDataBase() {
		// Delete all books
		bookRepository.deleteAll();
		
		// Insert books
		bookRepository.save(new Book("1ABC", "Kotlin em Ação 1", "O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção.", "1234567891123", "BR"));
		bookRepository.save(new Book("1ABD", "Kotlin em Ação 2", "O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção.", "2234567891123", "BR"));
		bookRepository.save(new Book("1ABF", "Kotlin em Ação 3", "O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção.", "3234567891123", "EN"));
		bookRepository.save(new Book("1ABG", "Kotlin em Ação 4", "O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção.", "4234567891123", "EN"));
		bookRepository.save(new Book("1ABH", "Kotlin em Ação 5", "O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção.", "5234567891123", "BR"));
	}
	
}

