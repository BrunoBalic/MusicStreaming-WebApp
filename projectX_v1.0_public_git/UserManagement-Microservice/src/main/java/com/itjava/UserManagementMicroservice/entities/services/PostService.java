package com.itjava.UserManagementMicroservice.entities.services;

import com.itjava.UserManagementMicroservice.entities.Post;
import com.itjava.UserManagementMicroservice.entities.repositories.PostRepository;
import com.itjava.UserManagementMicroservice.models.dto.CreatePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Post createNewPost(CreatePost post) {
        return postRepository.save(new Post(post.getContent(), post.getUser()));
    }

    public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getAllPostsFromUserId(int userId) {
        return postRepository.findByUser(userId);
    }

    public Page<Post> getAllPostsFromUserIdSortedAndPaginated(int userid, int page, int pageSize) {
        Sort sort = Sort.by("published").descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return postRepository.findAllByUser(userid, pageable);
    }

    /*
    public Page<Post> testpagingandsorting() {

        // ovo ne radi
        // kaze da ne moze naci publish_date u Post
        //Sort sort = Sort.by("publish_date").descending(); // "publish_date" field in entity class
        //Pageable pageable = PageRequest.of(0, 10, sort); // fisrt page with 10 elements
        //Page<Post> page = postRepository.findAll(pageable);
        //


        // ovo radi
        //Pageable pageable = PageRequest.of(0, 10); // fisrt page with 10 elements
        //Page<Post> page = postRepository.findAll(pageable);
        //


        // ovo radi
        //Sort sort = Sort.by("id").descending(); // "publish_date" field in entity class
        //Pageable pageable = PageRequest.of(0, 10, sort); // fisrt page with 10 elements
        //Page<Post> page = postRepository.findAll(pageable);
        //


        //Sort sort = Sort.by("user").descending(); // radi
        //Sort sort = Sort.by("content").descending(); // radi
        Sort sort = Sort.by("published").descending(); // radi -> otkrio sam bug u springu
        // --> Sort.by("entity_filed_name") ne radi ako fileld u nazivu ima _ underscore
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Post> page = postRepository.findAll(pageable);


        return page;
    }
     */

    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }

}
