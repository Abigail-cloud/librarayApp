package com.abigail.libraryapp.service.library;

import com.abigail.libraryapp.dto.library.LibraryDto;
import com.abigail.libraryapp.entity.library.Book;
import com.abigail.libraryapp.entity.library.LibraryEntity;
import com.abigail.libraryapp.entity.user.UserEntity;
import com.abigail.libraryapp.exception.BookNotFoundException;
import com.abigail.libraryapp.repository.book.BookRepository;
import com.abigail.libraryapp.repository.libaries.LibraryRepository;
import com.abigail.libraryapp.repository.user.UserRepository;
import com.abigail.libraryapp.service.userservices.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService{
    private LibraryRepository libraryRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;
    public LibraryServiceImpl(LibraryRepository libraryRepository, UserRepository userRepository,
                              BookRepository bookRepository){
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.userRepository = userRepository;
    }
    @Override
    public LibraryEntity borrowBook( Long bookId, LibraryDto libraryDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found with ID: " + userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID: " + bookId));
        LibraryEntity library = new LibraryEntity();
        library.setUser(user);
        library.setBook(book);
        library.setIsBookBorrowed(true);
        library.setBorrowDate(LocalDate.now());
        library.setReturnDate(LocalDate.now().plusDays(14));
        library.setReview(libraryDto.getReview());
        library.setLabel(libraryDto.getLabel());
        libraryRepository.save(library);
        return library;
    }

    @Override
    public void returnBook(Long libraryId) {

    }

    @Override
    public List<LibraryEntity> getAllBooksBorrowedByUser() {
        return libraryRepository.findAll();
    }
}
