package music.playlist.controller;
import music.playlist.service.PlaylistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/playlists") //base path for playlist related endpoints
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    //TODO: Fill out api endpoints
    // @GetMapping("/{playlistId}")
    //@PostMapping("/")
    //@PutMapping("/{playlistId}")
    //@DeleteMapping("/{playlistId}")
    //@GetMapping("/search")
    //@Do some track management endpoints: add,delete,get

}
