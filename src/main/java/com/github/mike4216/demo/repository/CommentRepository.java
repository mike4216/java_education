package com.github.mike4216.demo.repository;

import com.github.mike4216.demo.entity.Comment;
import com.github.mike4216.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    Comment findByIdAndUserId (Long commentId, Long userId);
}
