package music.playlist.controller;

import music.playlist.dto.PlaylistResponseDTO;
import music.playlist.service.PlaylistService;
import music.playlist.domain.Playlist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Validated
@RequestMapping("/playlists") //base path for playlist related endpoints
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/{playlistId}")
    public PlaylistResponseDTO getPlaylist(@PathVariable String playlistId){
        Playlist playlist=this.playlistService.getPlaylistById(playlistId);
        PlaylistResponseDTO responseDTO=this.playlistService.fromPlaylist(playlist);
        return responseDTO;
    }

    @PostMapping("/")
    public PlaylistResponseDTO createtPlaylist(@RequestParam(required = false) String playlistName){
        Playlist playlist=(playlistName==null)?this.playlistService.createPlaylist():this.playlistService.createPlaylist(playlistName);
        PlaylistResponseDTO responseDTO=this.playlistService.fromPlaylist(playlist);
        return responseDTO;
    }

    @PutMapping("/{playlistId}")
    public PlaylistResponseDTO renamePlaylist(@PathVariable String playlistId,@RequestParam String newName){
        Playlist playlist=this.playlistService.renamePlaylist(playlistId, newName);
        return this.playlistService.fromPlaylist(playlist);
    }

    @GetMapping("/search")
    public List<PlaylistResponseDTO> searchPlaylistByName(@RequestParam String keyword){
        List<PlaylistResponseDTO> responseDTOs=new ArrayList<>();
        List<Playlist> playlists=this.playlistService.searchPlaylistByName(keyword);
        for(Playlist playlist:playlists){
            responseDTOs.add(this.playlistService.fromPlaylist(playlist));
        }
        return responseDTOs;
    }

    @DeleteMapping("/{playlistId}")
     public void deletePlaylist(@PathVariable String playlistId){
         this.playlistService.deletePlaylist(playlistId);
    }
    
    //Do some track management endpoints: add,delete,get
    @PostMapping("/{playlistId}/tracks/")
    public void addTracks(@PathVariable String playlistId,@RequestBody List<String> trackIds){
        for(String trackId:trackIds){
            this.playlistService.addTrack(playlistId, trackId);
        }
    }

    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public void removeTrack(@PathVariable String playlistId,@PathVariable String trackId){
        this.playlistService.removeTrack(playlistId, trackId);
    }

    @GetMapping("/{playlistId}/tracks/")
    public HashSet<String> getTracks(@PathVariable String playlistId){
        return this.playlistService.loadTrackIds(playlistId);
    }
}
