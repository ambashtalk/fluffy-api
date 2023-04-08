package com.ambashtalk.devops.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person extends AbstractEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "username cannot be blank")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "default_person_email_id")
    private PersonEmail defaultPersonEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "person_roles",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    @org.hibernate.annotations.Generated(value = GenerationTime.INSERT)
    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN default TRUE")
    private Boolean enabled = true;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @org.hibernate.annotations.Generated(value = GenerationTime.INSERT)
    @Column(name = "created_at", nullable = false, columnDefinition = "datetime default NOW()")
    private Timestamp createdAt;

    @org.hibernate.annotations.Generated(value = GenerationTime.ALWAYS)
    @Column(name = "updated_at", nullable = false, columnDefinition = "datetime default NOW() on update NOW()")
    private Timestamp updatedAt;

}