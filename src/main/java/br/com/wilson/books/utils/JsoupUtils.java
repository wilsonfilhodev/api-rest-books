package br.com.wilson.books.utils;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class JsoupUtils {

	private JsoupUtils() {
	}

	public static Document parseHtmlToDoc(String url) throws IOException {

		Response response = Jsoup.connect(url).userAgent(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
				.referrer("http://www.google.com").execute();
		return response.parse();
	}
}
