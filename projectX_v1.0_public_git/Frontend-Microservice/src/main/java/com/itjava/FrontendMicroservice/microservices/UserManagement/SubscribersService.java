package com.itjava.FrontendMicroservice.microservices.UserManagement;

import com.itjava.FrontendMicroservice.microservices.Token;
import com.itjava.FrontendMicroservice.models.entities.Subscribers;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribersService {

    String base_uri = "http://localhost:8101/api/subs";

    @Autowired
    Token token;
    
    public void createSubscription(int user, int wantsToSub) {
        HttpResponse<Subscribers> res = Unirest
                .post(base_uri + "/{uid}/{fid}")
                .routeParam("uid", String.valueOf(user))
                .routeParam("fid", String.valueOf(wantsToSub))
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asObject(Subscribers.class);

        if (res.isSuccess()) {
            System.out.println("subscription added");
        } else System.out.println("error adding subscription");
    }

    public void deleteSubscription(int user, int wantsToUnSub) {
        HttpResponse res = Unirest
                .delete(base_uri + "/{uid}/{fid}")
                .routeParam("uid", String.valueOf(user))
                .routeParam("fid", String.valueOf(wantsToUnSub))
                .header("Authorization", "Bearer " + token.getAccessToken())
                .accept("application/json")
                .asEmpty();

        if (res.isSuccess()) {
            System.out.println("subscription removed");
        } else System.out.println("error removing subscription");
    }

}
