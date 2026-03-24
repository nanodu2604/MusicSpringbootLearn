package music.track.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import music.track.dto.TrackRequestDTO;
import music.track.dto.TrackResponseDTO;
import music.track.dto.TrackUpdateDTO;
import music.track.service.TrackService;
import music.track.domain.Track;


@Validated
@RestController
@RequestMapping("/tracks")  //track base path
public class TrackController {
    @Autowired
    private TrackService trackService;

    @GetMapping("/search")
    public List<TrackResponseDTO> searchTrack(@RequestParam String keyword) {
        List<TrackResponseDTO> ret=new ArrayList<>();
        List<Track> tracks=this.trackService.searchByTitle(keyword);
        for(Track track:tracks){
            ret.add(this.trackService.fromTrack(track));
        }
        return ret;
    }

    @PutMapping("/{trackId}")
    public TrackResponseDTO updateTrack(
            @PathVariable String trackId,
            @Valid @RequestBody TrackUpdateDTO updateDTO) {

        return trackService.updateTrack(trackId,updateDTO);
    }

    @GetMapping("/search/artist")
    public List<TrackResponseDTO> searchTrackByArtist(@RequestParam String artist) {
        List<TrackResponseDTO> ret=new ArrayList<>();
        List<Track> tracks=trackService.getTracksByArtist(artist);
        for(Track track:tracks){
            ret.add(trackService.fromTrack(track));
        }
        return ret;
    }
    
    @GetMapping("/search/genre")
    public List<TrackResponseDTO> searchTrackByGenre(@RequestParam String genre) {
        List<TrackResponseDTO> ret=new ArrayList<>();
        List<Track> tracks=trackService.getTracksByGenre(genre);
        for(Track track:tracks){
            ret.add(trackService.fromTrack(track));
        }
        return ret;
    }

    @PostMapping("/")
    public TrackResponseDTO createTrack(
        @Valid @RequestBody TrackRequestDTO requestDTO) {
        Track track=this.trackService.createTrack(requestDTO);
        return trackService.fromTrack(track);
    }
    

    //TODO: Add the delete track endpoints
    //TODO: Pagination track
}
