package com.abigail.libraryapp.controller;

import com.abigail.libraryapp.dto.library.BookDto;
import com.abigail.libraryapp.entity.library.Book;
import com.abigail.libraryapp.service.library.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/book/")
public class BookController {
    private BookServiceImpl bookService;
    public BookController(BookServiceImpl bookService){
        this.bookService = bookService;
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('AUTHOR')")
    public HttpEntity<Book> createBook(@Valid @RequestBody BookDto bookDto){
        Book book = bookService.createBook(bookDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "created");
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }
    @GetMapping(value = "/books")
    public HttpEntity <List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "gotten");
        return new ResponseEntity<>(books, headers, HttpStatus.FOUND);
    }

    @GetMapping(value = "/aBook/{id}")
    public HttpEntity<Book> getBookById(@PathVariable(value = "id") long id){
        Book book = bookService.getSingleBook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "book");
        return new ResponseEntity<>(book, headers, HttpStatus.FOUND);
    }
    @PutMapping (value = "/updateBook/{id}")
    public HttpEntity<Book> updateBookById(@PathVariable(value = "id") long id, @RequestBody BookDto bookDto){
        Book book = bookService.updateBook(id, bookDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "created");
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }
    @DeleteMapping (value = "/deleteBook/{id}")
    public HttpEntity<Book> deleteBookById(@PathVariable(value = "id") long id){
        Book book = bookService.deleteBook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "created");
        return new ResponseEntity<>(book, headers, HttpStatus.ACCEPTED);
    }

}
