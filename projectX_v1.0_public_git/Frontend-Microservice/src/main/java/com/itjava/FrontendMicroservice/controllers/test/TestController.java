package com.itjava.FrontendMicroservice.controllers.test;

import kong.unirest.HttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/test")
public class TestController {


    @GetMapping("/t1")
    public String t1(@AuthenticationPrincipal OidcUser principal, Model model) {
        return "test/t1";
    }

    @GetMapping("/t2")
    public String t2(@AuthenticationPrincipal OidcUser principal, Model model) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());

            principal.getClaims().forEach((key, value) -> {
                System.out.println(key + " -- " + value);
            } );


        }
        return "test/t2";
    }

}
