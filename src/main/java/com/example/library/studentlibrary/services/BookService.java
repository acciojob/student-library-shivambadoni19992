package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.Author;
import com.example.library.studentlibrary.models.Book;
import com.example.library.studentlibrary.models.Genre;
import com.example.library.studentlibrary.repositories.AuthorRepository;
import com.example.library.studentlibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository2;

    @Autowired
    AuthorRepository authorRepository;

    public void createBook(Book book) {
        int authorId = book.getAuthor().getId();
        Author author = authorRepository.findById(authorId).get();
        author.getBooksWritten().add(book);
        book.setAuthor(author);
        authorRepository.save(author);
    }

    public List<Book> getBooks(String genre, boolean available, String author) {
        List<Book> temp = bookRepository2.findByAvailability(available);
        List<Book> books = new ArrayList<>();
        Genre gen = Genre.valueOf(genre);
        for (Book b : temp) {
            if (author == null) {

                if (b.getGenre().equals(gen)) {
                    books.add(b);
                }
            } else {
                if (b.getGenre().equals(gen) && b.getAuthor().getName().equalsIgnoreCase(author)) {
                    books.add(b);
                }
            }
        }
        // find the elements of the list by yourself
        return books;
    }
}