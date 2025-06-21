package com.library.service;

import com.library.exception.BookNotFoundException;
import com.library.model.BookVO;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final Map<Long, BookVO> books = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BookVO addBook(BookVO book) {
        Long id = idGenerator.getAndIncrement();
        book.setId(id);
        books.put(id, book);
        return book;
    }

    public List<BookVO> addBatchBooks(List<BookVO> bookList) {
        List<BookVO> addedBooks = new ArrayList<>();
        for (BookVO book : bookList) {
            addedBooks.add(addBook(book));
        }
        return addedBooks;
    }

    public List<BookVO> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public BookVO getBookById(Long id) {
        BookVO book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        return book;
    }

    public BookVO updateBook(Long id, BookVO updatedBook) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        updatedBook.setId(id);
        books.put(id, updatedBook);
        return updatedBook;
    }

    public void deleteBook(Long id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        books.remove(id);
    }
}
