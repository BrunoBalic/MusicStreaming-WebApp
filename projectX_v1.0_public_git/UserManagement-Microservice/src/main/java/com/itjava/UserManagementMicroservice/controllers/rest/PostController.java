package com.itjava.UserManagementMicroservice.controllers.rest;

import com.itjava.UserManagementMicroservice.entities.Post;
import com.itjava.UserManagementMicroservice.entities.services.PostService;
import com.itjava.UserManagementMicroservice.models.dto.CreatePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/post")
public class PostController {

    @Autowired
    PostService postService;

    // CREATE POST
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Post createNewPosts(@RequestBody CreatePost post) {
        return postService.createNewPost(post);
    }

    // READ POST BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    // GET ALL POSTS FROM USER
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public List<Post> getAllPostsFromUser(@PathVariable int id) {
        return postService.getAllPostsFromUserId(id);
    }

    // GET ALL POSTS FROM USER - sorted and paginated
    @RequestMapping(value = {"/user/{userId}/sp", "/user/{userId}/sp/{page}"}, method = RequestMethod.GET)
    public Page<Post> getAllPostsFromUserSortedAndPaginated(@PathVariable(value = "userId") int id, @PathVariable(value = "page") Optional<Integer> page) {
        int pageSize = 10;
        return postService.getAllPostsFromUserIdSortedAndPaginated(id, page.orElse(0), pageSize);
    }

    // DELETE POST BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePostById(@PathVariable int id) {
        postService.deletePostById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
