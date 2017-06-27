// A simple Book class
class Book {
  String isbn;
  String title;
  String author;

  private static Book JSERVLET =
    new Book("156592391X", "Java Servlet Programming", "Hunter");

  private static Book HOBBIT =
    new Book("0395282659", "The Hobbit", "Tolkien");

  // Here we simulate a database lookup
  public static Book getBook(String isbn) {
    if (JSERVLET.getISBN().equals(isbn)) {
      return JSERVLET;
    }
    else if (HOBBIT.getISBN().equals(isbn)) {
      return HOBBIT;
    }
    else {
      return null;
    }
  }

  private Book(String isbn, String title, String author) {
    this.isbn = isbn;
    this.title = title;
    this.author = author;
  }

  public String getISBN() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }
}
