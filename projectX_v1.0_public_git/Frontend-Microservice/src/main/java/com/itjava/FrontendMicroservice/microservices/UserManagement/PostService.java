package com.itjava.FrontendMicroservice.microservices.UserManagement;

import com.itjava.FrontendMicroservice.microservices.Token;
import com.itjava.FrontendMicroservice.models.CreatePost;
import com.itjava.FrontendMicroservice.models.entities.Post;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    String base_uri = "http://localhost:8101/api/post";

    @Autowired
    Token token;

    // CREATE - new Post
    public Post creteNewPost(CreatePost post) {
        HttpResponse<Post> res = Unirest
                .post(base_uri + "/create")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType("application/json")
                .body(post)
                .accept("application/json")
                .asObject(Post.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }

    // READ - Post by id
    public Post getPostById(String id) {
        HttpResponse<Post> res = Unirest
                .get(base_uri + "/{id}")
                .routeParam("id", id)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asObject(Post.class);

        if (res.isSuccess()) {
            return res.getBody();
        } else return null;
    }


    // READ - all User posts
    public List getAllPostsByUserId(String userId) {
        HttpResponse<JsonNode> res = Unirest
                .get("http://localhost:8101/api/post/user/{id}")
                .routeParam("id", userId)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asJson();

        if (res.isSuccess()) {
            return res.getBody().getArray().toList();
        } else {
            System.out.println("neki je error");
            return null;
        }
    }

    // READ - all User posts - paginated
    public List<Post> getPostsByUserId(String userId, int page) {
        HttpResponse<JsonNode> res = Unirest
                .get("http://localhost:8101/api/post/user/{id}/sp/{page}")
                .routeParam("id", userId)
                .routeParam("page", String.valueOf(page))
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asJson();

        if (res.isSuccess()) {
            return (List<Post>) res.getBody().getObject().get("content");
        } else return null;
    }

    // DELETE - Post by id
    public int deletePostById(String id) {
        HttpResponse res = Unirest
                //.post(baseUri + "")
                .delete("http://localhost:8101/api/post/{id}")
                .routeParam("id", id)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asEmpty();

        if (res.isSuccess()) {
            return res.getStatus();
        } else return 0;
    }

}
