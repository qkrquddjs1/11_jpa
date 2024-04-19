package org.example.mapping.section02.embedded;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookRegistService {

    private BookRepository bookRepository;

    public BookRegistService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void registBook(BookRegistDTO newBook) {

        Book book = new Book(
                newBook.getBookTitle(),
                newBook.getAuthor(),
                newBook.getPublisher(),
                newBook.getPublishedDate(),
                new Price(
                        newBook.getRegularPrice(),
                        newBook.getDiscountRate()
                )
        );

        bookRepository.save(book);
    }
}
