package com.github.mike4216.demo.repository;

import com.github.mike4216.demo.entity.Post;
import com.github.mike4216.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreatedDateDesc(User user);
    List<Post> findAllByOrderByCreatedDateDesc(User user);
    Optional<Post> findPostByIdAndUser(Long id, User user);
}
