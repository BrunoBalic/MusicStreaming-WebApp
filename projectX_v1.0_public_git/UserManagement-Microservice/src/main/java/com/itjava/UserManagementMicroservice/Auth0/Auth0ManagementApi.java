package com.itjava.UserManagementMicroservice.Auth0;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Auth0ManagementApi {

    @Autowired
    Auth0ManagementApiToken token;

    @Value("${spring.security.client.provider.auth0.issuer-uri}")
    private String issuer_uri;

    @Value("${auth0.my-audience-management-api}")
    private String api_identifier;

    @Value("${auth0.my-connection}")
    private String auth0_connection;

    Auth0ManagementApi() {
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class BodyObj {
        String email;
        String password;
        String username;
        String connection;
    }

    public String createNewUser_NOT_USING(String email, String password, String username) { // vraca auth0 id
        BodyObj bodyObj = new BodyObj(email, password, username, auth0_connection);

        try {
            System.out.println(new ObjectMapper().writeValueAsString(bodyObj));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        HttpResponse<JsonNode> response = Unirest.post(api_identifier + "users")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType("application/json")
                .body(bodyObj)
                .asJson();
        
        try {
            String message_2 = response.getBody().getObject().getString("message"); //
            System.out.println(message_2);
        } catch (JSONException e) {
            e.getMessage();
        }


        if (response.isSuccess()) {
            return response.getBody().getObject().getString("user_id");
        }

        else return null;
    }


    public Map<String, String> createNewUser(String email, String password, String username) {
        BodyObj bodyObj = new BodyObj(email, password, username, auth0_connection);
        HttpResponse<JsonNode> response = Unirest.post(api_identifier + "users")
                .header("Authorization", "Bearer " + token.getAccessToken())
                .contentType("application/json")
                .body(bodyObj)
                .asJson();

        Map<String, String> map = new HashMap<>();
        if (response.isSuccess()) {
            map.put("user_id", response.getBody().getObject().getString("user_id"));
        } else {
            map.put("error_message", response.getBody().getObject().getString("message"));
        }
        return map;
    }

    private String getRoleId(String roleName) {
        HttpResponse<JsonNode> response = Unirest.get(api_identifier + "roles?name_filter={role_name}")
                .routeParam("role_name", roleName)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .asJson();

        if (response.isSuccess()) {
            String roleId = response.getBody().getArray().getJSONObject(0).getString("id");

            System.out.println(roleId);
            return roleId;
        }
        else {
            System.out.println("Error in gettint role ID");
            return null;
        }

    }

    public int assignRolesToUser(String userId, String roleName) {
        String roleId = getRoleId(roleName);
        List<String> rolesIds = new ArrayList<>();
        rolesIds.add(roleId);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.putArray("roles").add(roleId);

        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.post(api_identifier + "users/{userId}/roles")
                    .routeParam("userId", userId)
                    .header("Authorization", "Bearer " + token.getAccessToken())
                    .contentType("application/json")
                    .body(mapper.writeValueAsString(node))
                    .asJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(response.getStatus() + " - assign roles http status");

        if (response.getStatus() / 100 == 4) {
            String message_2 = response.getBody().getObject().getString("message"); //
            System.out.println(message_2);
        }

        return response.getStatus();
    }



}
