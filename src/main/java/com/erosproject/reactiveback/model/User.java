package com.erosproject.reactiveback.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Document(collection = "user")
@Getter
@Setter
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String nickname;

    @NotBlank
    @Size(max = 150)
    private String email;

    @NotBlank
    private String cpf;

    private Boolean isAdmin;

    private LocalDate createdAt;
    private LocalDate UpdatedAt;
    private Boolean deleted;
}
