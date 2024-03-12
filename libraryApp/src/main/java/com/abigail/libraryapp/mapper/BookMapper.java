package com.abigail.libraryapp.mapper;

import com.abigail.libraryapp.dto.library.BookDto;
import com.abigail.libraryapp.entity.library.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBookEntity (BookDto bookDto);
    BookDto toBookDto(Book book);
}
