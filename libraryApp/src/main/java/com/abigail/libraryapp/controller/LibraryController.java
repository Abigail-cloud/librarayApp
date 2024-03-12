package com.abigail.libraryapp.controller;

import com.abigail.libraryapp.dto.library.LibraryDto;
import com.abigail.libraryapp.entity.library.LibraryEntity;
import com.abigail.libraryapp.service.library.LibraryServiceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/lib")
public class LibraryController {
    private LibraryServiceImpl libraryService;
    public LibraryController(LibraryServiceImpl libraryService){
        this.libraryService = libraryService;
    }

    @GetMapping( "/public")
    public String publicAccess(){
        return "Public access for all";
    }

    @PostMapping("/borrowBook/{id}")
    public HttpEntity<LibraryEntity> bookFromLibrary(@PathVariable(value = "id")Long bookId,
                                                     @RequestBody LibraryDto libraryDto){
        LibraryEntity bookBorrowed = libraryService.borrowBook(bookId, libraryDto );
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "borrowed");
        return new ResponseEntity<>(bookBorrowed, headers, HttpStatus.ACCEPTED);
    }
    @GetMapping("/borrowBooks")
    public HttpEntity <List<LibraryEntity>> allBooksFromLibrary(){
        List<LibraryEntity> bookBorrowed = libraryService.getAllBooksBorrowedByUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "borrowed");
        return new ResponseEntity<>(bookBorrowed, headers, HttpStatus.ACCEPTED);
    }
}
