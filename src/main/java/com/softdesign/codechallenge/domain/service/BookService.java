package com.softdesign.codechallenge.domain.service;

import com.softdesign.codechallenge.dataaccess.entity.BookEntity;
import com.softdesign.codechallenge.dataaccess.repository.BookRepository;
import com.softdesign.codechallenge.dataaccess.specification.BookSpecification;
import com.softdesign.codechallenge.domain.dto.BookRequestDTO;
import com.softdesign.codechallenge.domain.dto.BookResponseDTO;
import com.softdesign.codechallenge.domain.dto.BooksFilter;
import com.softdesign.codechallenge.domain.dto.GetAllBookResponseDTO;
import com.softdesign.codechallenge.domain.exception.BookIsRentedException;
import com.softdesign.codechallenge.domain.exception.BookNotFoundException;
import com.softdesign.codechallenge.domain.mapper.BookMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    public GetAllBookResponseDTO getAllBooks(Integer page, Integer size, BooksFilter booksFilter) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<BookEntity> specification = BookSpecification.filterBy(booksFilter);
        Page<BookEntity> books = bookRepository.findAll(specification, pageable);

        return GetAllBookResponseDTO.builder()
                .books(books.getContent().stream().map(bookMapper::toDTO).toList())
                .totalPages(books.getTotalPages())
                .build();
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

    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        Optional<BookEntity> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }

        book.get().setTitle(bookRequestDTO.title());
        book.get().setAuthor(bookRequestDTO.author());
        book.get().setCategory(bookRequestDTO.category());
        book.get().setPublicationYear(bookRequestDTO.publicationYear());

        return bookMapper.toDTO(bookRepository.save(book.get()));
    }
}
