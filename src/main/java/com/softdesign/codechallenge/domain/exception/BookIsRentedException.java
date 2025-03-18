package com.softdesign.codechallenge.domain.exception;

public class BookIsRentedException extends RuntimeException {
    public BookIsRentedException() {
        super("Book is rented");
    }
}
