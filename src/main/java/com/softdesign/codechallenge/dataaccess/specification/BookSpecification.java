package com.softdesign.codechallenge.dataaccess.specification;

import com.softdesign.codechallenge.dataaccess.entity.BookEntity;
import com.softdesign.codechallenge.domain.dto.BooksFilter;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BookSpecification {

    public static Specification<BookEntity> filterBy(BooksFilter booksFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (booksFilter.title() != null && !booksFilter.title().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + booksFilter.title().toLowerCase() + "%"));
            }

            if (booksFilter.author() != null && !booksFilter.author().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + booksFilter.author().toLowerCase() + "%"));
            }

            if (booksFilter.category() != null && !booksFilter.category().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + booksFilter.category() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
