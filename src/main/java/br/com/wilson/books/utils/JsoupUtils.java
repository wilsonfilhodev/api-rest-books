package br.com.wilson.books.utils;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class JsoupUtils {

	private static final String URL_REFERRER = "http://www.google.com";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";

	private JsoupUtils() {
	}

	public static Document parseHtmlToDoc(String url) throws IOException {

		Response response = Jsoup.connect(url)
				.userAgent(USER_AGENT)
				.referrer(URL_REFERRER)
				.execute();
		return response.parse();
	}
}
