package com.github.mike4216.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String caption;
    private String title;
    private String location;
    private String username;
    private String likes;
    private Set<String> userlikes;
}
