package com.personal.OnePiece.graphql.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Data
public class Book {
    private String id;
    private String name;
    private int pageCount;
    private String authorId;

    private static List<Book> bookList = new ArrayList<>();

    static {
        bookList.addAll(List.of(new Book("book-1", "Harry Potter and the Philosopher's Stone", 200, "author-1"),
                new Book("book-2", "Moby Dick", 600, "author-2"),
                new Book("book-3", "Interview with the vampire", 371, "author-3")
        ));
    }

    public static Book getById(String id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    public static Book addBook(String id, String name, int pageCount, String authorId) {
        bookList.add(new Book(id, name, pageCount,authorId));
        if(Author.getById(authorId)==null){
            Author.addAuthor(authorId);
        }
        return getById(id);
    }
}
