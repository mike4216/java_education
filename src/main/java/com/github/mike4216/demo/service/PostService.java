package com.github.mike4216.demo.service;

import com.github.mike4216.demo.dto.PostDTO;
import com.github.mike4216.demo.entity.Image;
import com.github.mike4216.demo.entity.Post;
import com.github.mike4216.demo.entity.User;
import com.github.mike4216.demo.exceptions.PostNotFoundException;
import com.github.mike4216.demo.repository.ImageRepository;
import com.github.mike4216.demo.repository.PostRepository;
import com.github.mike4216.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal){
        User user  = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);
        LOG.info("Saving Post for User{}", user.getName());
        return postRepository.save(post);
    }

    public List<Post> getAllPost(){
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getById(Long postId, Principal principal){
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(()-> new PostNotFoundException("Post cannot be fount for username:" + user.getEmail()));
    }

    public List<Post> getAllPostForUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Post likePost(long postId, String username){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        //check if exists user who is trying to like post
        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username)).findAny();

        if(userLiked.isPresent()){
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        }else{
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }

        return  postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal){
        Post post = getById(postId, principal);
        Optional<Image> image = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        image.ifPresent(imageRepository::delete);
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found with username" + username));
    }
}
