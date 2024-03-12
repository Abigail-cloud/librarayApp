package com.abigail.libraryapp.repository.libaries;

import com.abigail.libraryapp.entity.library.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
