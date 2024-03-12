package com.abigail.libraryapp.entity.library;

import com.abigail.libraryapp.entity.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "library")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private Boolean isBookBorrowed;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Integer rating;
    @Size(min = 4, max = 30)
    private String review;
    @Column(length = 15)
    private String label;

}
