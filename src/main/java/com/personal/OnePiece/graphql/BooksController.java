package com.personal.OnePiece.graphql;

import com.personal.OnePiece.graphql.data.Author;
import com.personal.OnePiece.graphql.data.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BooksController {

    @QueryMapping
    public Book bookById(@Argument String id) {
        return Book.getById(id);
    }

    /*
    NOTE ::
    From the code's point of view there is no reason why query mapping can't change the data
    However it is a surprising behaviour and may cause side effects and is generally consiered
    bad practice.
     */
    @MutationMapping
    public Book addBook(@Argument String id, @Argument String name,
                        @Argument int pageCount, @Argument String authorId) {
        return Book.addBook(id, name, pageCount, authorId);
    }

    @SchemaMapping
    public Author author(Book book) {
        return Author.getById(book.getAuthorId());
    }
}
