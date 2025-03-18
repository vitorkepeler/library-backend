package com.softdesign.codechallenge.app.controller;

import com.softdesign.codechallenge.domain.dto.BookRequestDTO;
import com.softdesign.codechallenge.domain.dto.BookResponseDTO;
import com.softdesign.codechallenge.domain.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/{id}")
    public void rentBook(@PathVariable Long id) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }


        bookService.rentBook(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @PostMapping
    public BookResponseDTO createBook(@RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return bookService.createBook(bookRequestDTO);
    }
}
