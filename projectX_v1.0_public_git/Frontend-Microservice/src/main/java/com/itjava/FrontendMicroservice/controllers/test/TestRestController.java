package com.itjava.FrontendMicroservice.controllers.test;

import com.itjava.FrontendMicroservice.microservices.UserManagement.PostService;
import com.itjava.FrontendMicroservice.microservices.UserManagement.UserService;
import com.itjava.FrontendMicroservice.models.CreatePost;
import com.itjava.FrontendMicroservice.models.entities.Post;
import com.itjava.FrontendMicroservice.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/test/rest")
public class TestRestController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @GetMapping(value = "/list-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> list_json(Model model) {
        List<String> x = new ArrayList<>();
        x.add("token_id");
        return x;
    }

    @GetMapping(value = "/map-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<String>> map_json(Model model) {
        Map<String, List<String>> y = new HashMap<>();
        y.put("roles", new ArrayList<>());
        y.get("roles").add("role_id_1234");
        y.get("roles").add("role_id_5678");
        return y;
    }

    @GetMapping(value = "/t1")
    public User t1() {
        return userService.getUserById("1");
    }

    @GetMapping(value = "/t2")
    public Post t2() {
        return postService.creteNewPost(new CreatePost("some new text", 2));
    }

    @GetMapping(value = "/t3")
    public Post t3() {
        return postService.getPostById("6");
    }

    @GetMapping(value = "/t4")
    public List<User> t4() {
        return userService.searchUsers("b");
    }

}
