package com.itjava.FrontendMicroservice.controllers;

import com.itjava.FrontendMicroservice.forms.UserRegistration;
import com.itjava.FrontendMicroservice.microservices.MusicManagement.PlaylistService;
import com.itjava.FrontendMicroservice.microservices.MusicManagement.TrackService;
import com.itjava.FrontendMicroservice.microservices.UserManagement.PostService;
import com.itjava.FrontendMicroservice.microservices.UserManagement.SubscribersService;
import com.itjava.FrontendMicroservice.microservices.UserManagement.UserService;
import com.itjava.FrontendMicroservice.models.CreatePlaylist;
import com.itjava.FrontendMicroservice.models.CreateUser;
import com.itjava.FrontendMicroservice.models.UserCreationResponse;
import com.itjava.FrontendMicroservice.models.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class MainController {
    @Autowired
    UserService userService;
    @Autowired
    SubscribersService subscribersService;
    @Autowired
    PostService postService;
    @Autowired
    TrackService trackService;
    @Autowired
    PlaylistService playlistService;

    // GET - Registration Form
    @GetMapping("/register")
    public ModelAndView registrationForm(Model model) {
        model.addAttribute("registrationForm", new UserRegistration());
        return new ModelAndView("register"); // tu ide naziv template-a
    }

    // POST - Registration Form
    @PostMapping("/register")
    public ModelAndView registrationFormSubmission(Model model, @ModelAttribute("register") UserRegistration registrationForm, HttpServletResponse response) {
        if (!registrationForm.isValid()) {
            model.addAttribute("msg", "Passwords not matched!");
            model.addAttribute("registrationForm", new UserRegistration());
            return new ModelAndView("register");
        }
        UserCreationResponse newUser = userService.createNewUser(new CreateUser(
                registrationForm.getEmail(),
                registrationForm.getPassword(),
                registrationForm.getFirst_name(),
                registrationForm.getLast_name(),
                registrationForm.getUsername()
        ));
        if (newUser.getUser() == null) {
            model.addAttribute("msg", newUser.getError_message());
            model.addAttribute("registrationForm", new UserRegistration());
            return new ModelAndView("register");
        }
        return new ModelAndView("redirect:/");
    }

    // GET - loggedIn Users profile
    // !!! /profile je zauzet od strane springa
    // za sada naci alternativni path
    @GetMapping("/myprofile")
    public ModelAndView usersProfile(@AuthenticationPrincipal OidcUser principal, Model model) {
        User currentUser = userService.getUserByAuth0Id(principal.getSubject());
        model.addAttribute("user", currentUser);
        List<User> following = new ArrayList<>();
        for (Subscribers sub: currentUser.getSubscribers_user_follows()) {
            following.add(userService.getUserById(String.valueOf(sub.getUser())));
        }
        int numb_tracks = trackService.getAllTracksFromUser(currentUser.getId()).size();
        System.out.println(numb_tracks);
        model.addAttribute("numb_tracks", numb_tracks);
        int num_playlists = playlistService.getAllPlaylistsFromUser(currentUser.getId()).size();
        model.addAttribute("num_playlists", num_playlists);
        model.addAttribute("myprofile", "yes");
        model.addAttribute("following", following);
        model.addAttribute("posts", postService.getAllPostsByUserId(String.valueOf(currentUser.getId())));
        model.addAttribute("tracks", trackService.getAllTracksFromUser(currentUser.getId()));
        model.addAttribute("playlists", playlistService.getAllPlaylistsFromUser(currentUser.getId()));
        return new ModelAndView("artist_detail");
    }

    // GET - Profile of User with specified username
    @GetMapping("/artist/{username}")
    public ModelAndView artistProfile(@AuthenticationPrincipal OidcUser principal, Model model, @PathVariable("username") String username) {
        User loggedInUser = userService.getUserByAuth0Id(principal.getSubject());
        User requestedUser = userService.getUserByUsername(username);

        if (loggedInUser.getId() == requestedUser.getId()) {
            return new ModelAndView("redirect:/myprofile");
        }

        model.addAttribute("user", requestedUser);
        List<User> following = new ArrayList<>();
        for (Subscribers sub: requestedUser.getSubscribers_user_follows()) {
            following.add(userService.getUserById(String.valueOf(sub.getUser())));
        }

        int numb_tracks = trackService.getAllTracksFromUser(loggedInUser.getId()).size();
        System.out.println(numb_tracks);
        model.addAttribute("numb_tracks", numb_tracks);
        int num_playlists = playlistService.getAllPlaylistsFromUser(loggedInUser.getId()).size();
        model.addAttribute("num_playlists", num_playlists);

        boolean amISubed = false;
        for (Subscribers sub: loggedInUser.getSubscribers_user_follows()) {
            if (sub.getUserFollows() == requestedUser.getId()) {
                System.out.println(sub.getUser());
                System.out.println(requestedUser.getId());
                amISubed = true;
                break;
            }
        }

        model.addAttribute("amISubed", amISubed);
        model.addAttribute("following", following);
        model.addAttribute("posts", postService.getAllPostsByUserId(String.valueOf(requestedUser.getId())));
        model.addAttribute("tracks", trackService.getAllTracksFromUser(requestedUser.getId()));
        model.addAttribute("playlists", playlistService.getAllPlaylistsFromUser(requestedUser.getId()));
        return new ModelAndView("artist_detail");
    }

    // POST - Profile of User with specified username - start subscription
    @PostMapping("/artist/{username}")
    public ModelAndView artistProfileSub(@AuthenticationPrincipal OidcUser principal, Model model, @PathVariable("username") String username) {
        User loggedInUser = userService.getUserByAuth0Id(principal.getSubject());
        User userIWantToFollow = userService.getUserByUsername(username);

        // add subscription
        subscribersService.createSubscription(loggedInUser.getId(), userIWantToFollow.getId());

        model.addAttribute("user", userIWantToFollow);
        List<User> following = new ArrayList<>();
        for (Subscribers sub: userIWantToFollow.getSubscribers_user_follows()) {
            following.add(userService.getUserById(String.valueOf(sub.getUser())));
        }
        model.addAttribute("following", following);
        model.addAttribute("posts", postService.getAllPostsByUserId(String.valueOf(userIWantToFollow.getId())));
        model.addAttribute("tracks", trackService.getAllTracksFromUser(userIWantToFollow.getId()));
        model.addAttribute("playlists", playlistService.getAllPlaylistsFromUser(userIWantToFollow.getId()));
        return new ModelAndView("artist_detail");
    }

    // POST - Profile of User with specified username - start subscription
    @DeleteMapping("/artist/{username}")
    public ModelAndView artistProfileUnSub(@AuthenticationPrincipal OidcUser principal, Model model, @PathVariable("username") String username) {
        User loggedInUser = userService.getUserByAuth0Id(principal.getSubject());
        User userIWantToUnFollow = userService.getUserByUsername(username);

        // remove subscription
        subscribersService.deleteSubscription(loggedInUser.getId(), userIWantToUnFollow.getId());

        model.addAttribute("user", userIWantToUnFollow);
        List<User> following = new ArrayList<>();
        for (Subscribers sub: userIWantToUnFollow.getSubscribers_user_follows()) {
            following.add(userService.getUserById(String.valueOf(sub.getUser())));
        }
        model.addAttribute("following", following);
        model.addAttribute("posts", postService.getAllPostsByUserId(String.valueOf(userIWantToUnFollow.getId())));
        model.addAttribute("tracks", trackService.getAllTracksFromUser(userIWantToUnFollow.getId()));
        model.addAttribute("playlists", playlistService.getAllPlaylistsFromUser(userIWantToUnFollow.getId()));
        return new ModelAndView("artist_detail");
    }

    // GET - Search Users
    @GetMapping("/search/{searchString}")
    public ModelAndView searchUsers(Model model, @PathVariable("searchString") String searchString) {
        List<User> searchResult = userService.searchUsers(searchString);
        //model.addAttribute("users", searchResult);
        model.addAttribute("artists", searchResult);
        model.addAttribute("numb_artists", searchResult.size());
        return new ModelAndView("search_artists");
    }

    // GET - Track by Id
    @GetMapping("/track/{id}")
    public ModelAndView getTrack(@AuthenticationPrincipal OidcUser principal, Model model, @PathVariable("id") int id) {
        User loggedInUser = userService.getUserByAuth0Id(principal.getSubject());
        Track tr = trackService.getTrackById(id);
        model.addAttribute("track", tr);
        model.addAttribute("user", userService.getUserById(String.valueOf(tr.getUser())));
        model.addAttribute("loggedInUser", loggedInUser);
        List<Playlist> playlists = playlistService.getAllPlaylistsFromUser(loggedInUser.getId());

        List<Playlist> playlists2 = new ArrayList<>();
        for (Playlist pl: playlists) {
            boolean flag = true;
            for (TrackPlaylist tp: pl.getTracks()) {
                if (tp.getTrack() == id) {
                    flag = false;
                }
            }
            if (flag) {
                playlists2.add(pl);
            }
        }

        //model.addAttribute("playlists", playlists);
        model.addAttribute("playlists", playlists2);

        System.out.println("broj playlist logiranog usera " + playlists.size());

        return new ModelAndView("track");
    }

    // POST - Add track to selected Playlist
    @PostMapping("/track/{trackId}")
    public ModelAndView addTrackToPlaylist(@AuthenticationPrincipal OidcUser principal, Model model, @PathVariable("trackId") int trackId, @RequestParam("playlistId") int playlistId) {
        User loggedInUser = userService.getUserByAuth0Id(principal.getSubject());
        Track tr = trackService.getTrackById(trackId);
        List<Playlist> playlists = playlistService.getAllPlaylistsFromUser(loggedInUser.getId());

        System.out.println("broj playlista logiranog usera " + playlists.size());
        System.out.println("hidden input playlist id: " + playlistId);

        boolean trackAlreadyAddedToSelectedPlaylist = false;
        for (Playlist pl: playlists) {
            for (TrackPlaylist tp: pl.getTracks()) {
                if (trackId == tp.getTrack() && tp.getPlaylist() == playlistId) {
                    trackAlreadyAddedToSelectedPlaylist = true;
                    break;
                }
            }
        }
        if (trackAlreadyAddedToSelectedPlaylist) {
            System.out.println("track already added to playlist");
            return new ModelAndView("redirect:/track/" + trackId);
        }
        Playlist playlistToAddTrackTo = playlistService.getPlaylistById(playlistId);
        playlistService.addTrackToPlaylist(trackId, playlistId);
        System.out.println("broj playlista logiranog usera " + playlists.size());
        return new ModelAndView("redirect:/track/" + trackId);
    }

    @GetMapping("/playlist/create")
    public ModelAndView createPlaylistForm() {
        return new ModelAndView("new_playlist");
    }

    // POST - create new Playlist
    @PostMapping("/playlist/create")
    public ModelAndView createPlaylist(@AuthenticationPrincipal OidcUser principal, Model model, @RequestParam("playlistName") String playlistName) {
        if (playlistName == "") {
            return new ModelAndView("redirect:/myprofile");
        }
        User loggedInUser = userService.getUserByAuth0Id(principal.getSubject());
        playlistService.createNewPlaylist(new CreatePlaylist(playlistName, loggedInUser.getId()));
        return new ModelAndView("redirect:/myprofile");
    }

    // GET - Playlist by Id
    @GetMapping("/playlist/{id}")
    public ModelAndView getPlaylist(Model model, @PathVariable("id") int id) {
        Playlist pl = playlistService.getPlaylistById(id);
        model.addAttribute("playlist", pl);
        model.addAttribute("user", userService.getUserById(String.valueOf(pl.getUser())));
        List<Track> tracks = playlistService.getAllTracksFromPlaylist(pl.getId());
        model.addAttribute("tracks", tracks);
        model.addAttribute("no_of_tracks", tracks.size());
        return new ModelAndView("playlist");
    }

    // !!! forma ne moze imati meyhod DELETE -> alternativa koristiti POST
    // DELETE - Remove tracks from selected Playlist
    @PostMapping("/playlist/{id}")
    public ModelAndView removeTrackFromPlaylist(@AuthenticationPrincipal OidcUser principal, Model model, @PathVariable("id") int playlistsId, @RequestParam("trackId") int trackId) {
        Playlist pl = playlistService.getPlaylistById(playlistsId);
        User loggedInUser = userService.getUserByAuth0Id(principal.getSubject());
        if (pl.getUser() != loggedInUser.getId()) {
            System.out.println("nije tvoja playlista, ne smijes brisati glazbu");
            return new ModelAndView("redirect:/playlist/" + playlistsId);
        }
        List<Track> tracks = playlistService.getAllTracksFromPlaylist(pl.getId());
        System.out.println("hidden input track id: " + trackId);
        playlistService.removeTrackFromPlaylist(trackId, playlistsId);
        System.out.println("track removed from playlist - controller");
        return new ModelAndView("redirect:/playlist/" + playlistsId);
    }

}
