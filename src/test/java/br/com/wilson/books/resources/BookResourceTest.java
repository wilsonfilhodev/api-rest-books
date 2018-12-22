package br.com.wilson.books.resources;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import br.com.wilson.books.BooksApplicationTests;
import br.com.wilson.books.model.Book;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class BookResourceTest extends BooksApplicationTests {
	
	@Test
	public void deve_buscar_livro_por_id() {
		given()
			.pathParam("id", "1ABC")
		.get("/books/{id}")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.OK.value())
			.body("id", equalTo("1ABC"),
					"title", equalTo("Kotlin em Ação 1"),
					"description", equalTo("O Kotlin em ação ensina você a usar a linguagem Kotlin para aplicativos com qualidade de produção."),
					"ISBN", equalTo("1234567891123"),
					"language", equalTo("BR"));
	}
	
	@Test
	public void deve_retornar_excessao_se_nao_existir_livro_com_id_informado() {
		given()
			.pathParam("id", "1234")
		.get("/books/{id}")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("message", equalTo("Book with id 1234 not found"));
	}

	
	@Test
	public void deve_salvar_novo_livro() {
		Book book = new Book("1234", "Title Book", "Description Book", "ISBN - 1234567891", "BR");
		
		given()
			.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(book)
		.when()
		.post("/books")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.CREATED.value())
			.header("location", equalTo("http://localhost:"+port+"/books/1234"))
			.body("id", equalTo("1234"),
					"title", equalTo("Title Book"),
					"description", equalTo("Description Book"),
					"ISBN", equalTo("ISBN - 1234567891"),
					"language", equalTo("BR"));
	}
	
	@Test
	public void deve_retornar_excessao_ao_tentar_salvar_livro_sem_titulo() {
		Book book = new Book("1234", "", "Description Book", "ISBN - 1234567891", "BR");
		
		given()
			.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(book)
		.when()
		.post("/books")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("message", equalTo("Field 'title' is required"));
	}
}
