package com.abigail.libraryapp.dto.library;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDto {
    private Integer rating;
    private String review;
    private String label;
}
