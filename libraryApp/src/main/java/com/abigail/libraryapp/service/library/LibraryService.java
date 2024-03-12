package com.abigail.libraryapp.service.library;


import com.abigail.libraryapp.dto.library.LibraryDto;
import com.abigail.libraryapp.entity.library.LibraryEntity;

import java.util.List;

public interface LibraryService  {
    LibraryEntity borrowBook( Long bookId, LibraryDto libraryDto);
    void returnBook(Long libraryId);
    List<LibraryEntity> getAllBooksBorrowedByUser();
}
