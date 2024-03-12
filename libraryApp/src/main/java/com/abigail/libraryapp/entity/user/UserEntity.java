package com.abigail.libraryapp.entity.user;


import com.abigail.libraryapp.entity.library.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 23)
    private String userName;
    @NotBlank
    @Size(min = 4, max = 25)
    @Email
    private String email;
    @NotBlank
    private String password;
    private boolean isPremium;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role>roles = new ArrayList<>();

    public UserEntity(String userName, String email, String encode) {
        this.userName = userName;
        this.email = email;
        this.password = encode;
    }
}
