package com.softdesign.codechallenge.app.controller;

import com.softdesign.codechallenge.domain.dto.BookRequestDTO;
import com.softdesign.codechallenge.domain.dto.BookResponseDTO;
import com.softdesign.codechallenge.domain.dto.BooksFilter;
import com.softdesign.codechallenge.domain.dto.GetAllBookResponseDTO;
import com.softdesign.codechallenge.domain.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public GetAllBookResponseDTO getAllBooks(
            @RequestParam(required = false, defaultValue = "0" ) Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @ModelAttribute BooksFilter booksFilter
    ) {
        return bookService.getAllBooks(page, size, booksFilter);
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/{id}")
    public void rentBook(@PathVariable Long id) {
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

    @PutMapping("/{id}")
    public BookResponseDTO updateBook(@PathVariable Long id, @RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return bookService.updateBook(id, bookRequestDTO);
    }
}
