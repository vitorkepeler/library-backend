package com.softdesign.codechallenge.domain.service;

import com.softdesign.codechallenge.dataaccess.entity.BookEntity;
import com.softdesign.codechallenge.dataaccess.repository.BookRepository;
import com.softdesign.codechallenge.domain.dto.BookRequestDTO;
import com.softdesign.codechallenge.domain.dto.BookResponseDTO;
import com.softdesign.codechallenge.domain.exception.BookIsRentedException;
import com.softdesign.codechallenge.domain.exception.BookNotFoundException;
import com.softdesign.codechallenge.domain.mapper.BookMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDTO).toList();
    }

    public BookResponseDTO getBookById(Long id) {
        Optional<BookEntity> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }

        return bookMapper.toDTO(book.get());
    }

    public void rentBook(Long id) {
        Optional<BookEntity> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }

        boolean available = book.get().isAvailable();

        book.get().setAvailable(!available);

        bookRepository.save(book.get());
    }

    public void deleteBook(Long id) {
        Optional<BookEntity> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }

        if (!book.get().isAvailable()) {
            throw new BookIsRentedException();
        }

        bookRepository.delete(book.get());
    }

    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        BookEntity book = bookMapper.toEntity(bookRequestDTO);

        book.setAvailable(true);

        return bookMapper.toDTO(bookRepository.save(book));
    }
}
