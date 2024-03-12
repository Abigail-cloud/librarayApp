package com.abigail.libraryapp.validation.book;

import com.abigail.libraryapp.dto.library.BookDto;
import com.abigail.libraryapp.entity.library.Book;
import org.springframework.stereotype.Component;

@Component
public class BookValidationImpl  implements BookValidation{

    @Override
    public void bookValidation(BookDto bookDto) {
        String book = bookDto.getTitle();
        if (book == null || book.isEmpty()){
            throw new RuntimeException("Book title not found");
        }

    }
}
