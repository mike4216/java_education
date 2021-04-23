package com.github.mike4216.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.mike4216.demo.entity.enums.ERole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, updatable = true)
    private String username;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text")
    private String bio;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> role = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
    @JsonFormat(pattern = "yyy-mmm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @PrePersist
    protected void onCreate(){
        this.createDate = LocalDateTime.now();
    }


}
