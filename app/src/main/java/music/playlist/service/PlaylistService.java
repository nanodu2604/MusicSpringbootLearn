package music.playlist.service;

import music.playlist.domain.Playlist;
import music.playlist.exception.PlaylistNotFoundException;
import music.playlist.repository.PlaylistRepository;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService{
    private final PlaylistRepository playlistRepository;
    public PlaylistService(@Qualifier("memoryPlaylistRepo") PlaylistRepository playlistRepository ){
        this.playlistRepository=playlistRepository;
    }
    //CRUD methods
    public Playlist createPlaylist(String playlistName){
        String playlistId=UUID.randomUUID().toString();
        Playlist playlist=new Playlist();
        playlist.setPlaylistId(playlistId);
        playlist.setPlaylistName(playlistName);
        this.playlistRepository.savePlaylist(playlist);
        return playlist;
    }
    public Playlist createPlaylist(){
        String playlistName="Untitle";
        String playlistId=UUID.randomUUID().toString();
        Playlist playlist=new Playlist();
        playlist.setPlaylistId(playlistId);
        playlist.setPlaylistName(playlistName);
        this.playlistRepository.savePlaylist(playlist);
        return playlist;
    }

    public Playlist getPlaylistById(String playlistId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        if(playlist==null){
            throw new PlaylistNotFoundException(playlistId);
        }
        return playlist;
    }

    public void renamePlaylist(String playlistId,String newName){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        playlist.setPlaylistName(newName);
        this.playlistRepository.savePlaylist(playlist);
    } 

    public void deletePlaylist(String playlistId){
        this.playlistRepository.deletePlaylist(playlistId);
    }

    //Track functions
    public void addTrack(String playlistId, String trackId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        HashSet<String> trackIds=playlist.getTrackIds();
        trackIds.add(trackId);
        playlist.setTrackIds(trackIds);
        this.playlistRepository.savePlaylist(playlist);
    }

    public void removeTrack(String playlistId,String trackId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        HashSet<String> trackIds=playlist.getTrackIds();
        trackIds.remove(trackId);
        playlist.setTrackIds(trackIds);
        this.playlistRepository.savePlaylist(playlist);
    }

    public HashSet<String> loadTrackIds(String playlistId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        return playlist.getTrackIds();
    }

}