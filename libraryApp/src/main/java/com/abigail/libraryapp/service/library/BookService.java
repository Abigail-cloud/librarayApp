package com.abigail.libraryapp.service.library;

import com.abigail.libraryapp.dto.library.BookDto;
import com.abigail.libraryapp.entity.library.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookService {
   Book createBook(BookDto bookDto);

//   Possibility of getting a single book and borrowing it.
   Book getSingleBook(Long id);
   List<Book> getAllBooks();
   Book updateBook(Long id, BookDto bookDto);
   Book deleteBook(Long id);
}
