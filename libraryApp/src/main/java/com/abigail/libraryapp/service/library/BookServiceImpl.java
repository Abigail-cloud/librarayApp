package com.abigail.libraryapp.service.library;

import com.abigail.libraryapp.dto.library.BookDto;
import com.abigail.libraryapp.entity.library.Book;
import com.abigail.libraryapp.exception.BookNotFoundException;
import com.abigail.libraryapp.mapper.BookMapper;
import com.abigail.libraryapp.repository.book.BookRepository;
import com.abigail.libraryapp.validation.book.BookValidationImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private final BookValidationImpl bookValidation;
    public BookServiceImpl(BookRepository bookRepository, @Qualifier("cool") BookMapper bookMapper, BookValidationImpl bookValidation){
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.bookValidation = bookValidation;
    }

    @Override
    public Book createBook(BookDto bookDto) {
        bookValidation.bookValidation(bookDto);
        Book book = bookMapper.toBookEntity(bookDto);
        return bookRepository.save(book);
    }

    @Override
    public Book getSingleBook(Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            return book.get();
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Long id, BookDto bookDto) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            Book putBook = book.get();
            putBook.setAuthor(bookDto.getAuthor());
            putBook.setTitle(bookDto.getTitle());
            putBook.setPublishingCompany(bookDto.getPublishingCompany());
            bookRepository.save(putBook);
            return putBook;
        }
        return null;
    }

    @Override
    public Book deleteBook(Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            bookRepository.delete(book.get());
        }
        return null;
    }
}
