package com.github.mike4216.demo.repository;

import com.github.mike4216.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUserId(Long userId);
    Optional<Image> findByPostId(Long postId);

}
