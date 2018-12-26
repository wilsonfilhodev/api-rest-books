
<h3>API Rest Books</h3>

<p>API Restfull Example Using Spring Boot + MongoDB + Jsoup. In this project you have an example of scraping data using Jsoup.</p>

## Contents

- [Quick start](#quick-start)
- [What's included](#whats-included)
- [Authors](#authors)
- [License](#license)

## Quick start

**Warning**

> Verify that you are running at least JDK 1.8+ and Maven 3.3+ by running java -version and mvn -version in a terminal/console window. Older versions produce errors, but newer versions are fine.

1. Download or clone project

2. Go to project folder and execute command.
 ```bash
 mvn clean spring-boot:run
 ```
 
 **Notes**
 > It is api uses cloud database (MLab) and does scraping data from a web page, so it is necessary is connected to the internet to utilize the features of this project.


## What's included

* Create book

**POST** http://localhost:8080/books
```
  Example data send in body
  
  {
    "title": "Book title example",
    "description": "Book description example",
    "ISBN": "9781617293290",
    "language": "BR"
  }
```

**Notes**
> All attributes are required

* Get books by id

**GET** http://localhost:8080/books/{id}

```
  Example return data.
  
 {
    "id": "1234",
    "title": "Book Java Spring Boot",
    "description": "Book description example",
    "language": "BR",
    "ISBN": "9781617293290"
}
```

* Return all books

**GET** http://localhost:8080/books

```
  Example return data.
  
  {
    "numberBooks": 2,
    "books": [
        {
            "title": "Kotlin in Action",
            "description": "Kotlin in Action teaches you to use the Kotlin language for production-quality applications. Written for experienced Java developers, this example-rich book goes further than most language books, covering interesting topics like building DSLs with natural language syntax. The book is written by Dmitry Jemerov and Svetlana Isakova, developers on the Kotlin team. Chapter 6, covering the Kotlin type system, and chapter 11, covering DSLs, are available as a free preview on the publisher Web site.",
            "language": "EN",
            "ISBN": "9781617293290"
        },
        {
            "title": "Kotlin for Android Developers",
            "description": "Kotlin for Android Developers is a book by Antonio Leiva showing how Kotlin can be used for creating an Android application from scratch.",
            "language": "EN",
            "ISBN": "Unavailable"
        }]
  }
```
**Notes**
> In this request the api consult the available books in the web page (https://kotlinlang.org/docs/books.html) and if it does not already exist in the internal database the book is saved automatically.

* Delete books by id

**DELETE** http://localhost:8080/books/{id}

* Delete all books

**DELETE** http://localhost:8080/books

## Authors

* **Wilson Filho**  - [Linkedin](https://www.linkedin.com/in/wilson-filho-4424b5bb)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
