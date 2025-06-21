package com.library.controller;

import com.library.model.BookVO;
import com.library.service.BookService;
import com.library.exception.BookNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BookService bookService() {
            return mock(BookService.class);
        }
    }

    @Test
    void testAddBook_Success() throws Exception {
        BookVO book = new BookVO(null, "Test Book", "Test Author", 29.99);
        BookVO savedBook = new BookVO(1L, "Test Book", "Test Author", 29.99);

        when(bookService.addBook(any(BookVO.class))).thenReturn(savedBook);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    void testAddBook_ValidationError() throws Exception {
        BookVO book = new BookVO(null, "", "Test Author", -10.0);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllBooks_Success() throws Exception {
        List<BookVO> books = Arrays.asList(
                new BookVO(1L, "Book 1", "Author 1", 19.99),
                new BookVO(2L, "Book 2", "Author 2", 29.99)
        );

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void testGetBookById_Success() throws Exception {
        BookVO book = new BookVO(1L, "Test Book", "Test Author", 29.99);

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(999L)).thenThrow(new BookNotFoundException("Book with id 999 not found"));

        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book with id 999 not found"));
    }

    @Test
    void testUpdateBook_Success() throws Exception {
        BookVO bookToUpdate = new BookVO(null, "Updated Book", "Updated Author", 39.99);
        BookVO updatedBook = new BookVO(1L, "Updated Book", "Updated Author", 39.99);

        when(bookService.updateBook(eq(1L), any(BookVO.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.price").value(39.99));
    }

    @Test
    void testUpdateBook_NotFound() throws Exception {
        BookVO bookToUpdate = new BookVO(null, "Updated Book", "Updated Author", 39.99);

        when(bookService.updateBook(eq(999L), any(BookVO.class)))
                .thenThrow(new BookNotFoundException("Book with id 999 not found"));

        mockMvc.perform(put("/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookToUpdate)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book with id 999 not found"));
    }

    @Test
    void testDeleteBook_Success() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService).deleteBook(1L);
    }

    @Test
    void testDeleteBook_NotFound() throws Exception {
        doThrow(new BookNotFoundException("Book with id 999 not found"))
                .when(bookService).deleteBook(999L);

        mockMvc.perform(delete("/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book with id 999 not found"));
    }
}