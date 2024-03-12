package com.abigail.libraryapp.repository.book;

import com.abigail.libraryapp.entity.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
