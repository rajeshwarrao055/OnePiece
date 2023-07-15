package com.personal.OnePiece.graphql.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Author {
    private String id;
    private String firstName;
    private String lastName;

    private static List<Author> authors = new ArrayList<>();

    static {
        authors.addAll(Arrays.asList(
                new Author("author-1", "Joanne", "Rowling"),
                new Author("author-2", "Herman", "Melville"),
                new Author("author-3", "Anne", "Rice")
        ));
    }

    public static Author getById(String id) {
        return authors.stream().filter(author -> author.getId().equals(id)).findFirst().orElse(null);
    }

    public static void addAuthor(String authorId) {
        authors.add(new Author(authorId, UUID.randomUUID().toString(), UUID.randomUUID().toString()));
    }
}
