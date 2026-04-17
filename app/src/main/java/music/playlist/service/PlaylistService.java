package music.playlist.service;

import music.playlist.domain.Playlist;
import music.playlist.dto.PlaylistResponseDTO;
import music.playlist.exception.EmptyPlaylistException;
import music.playlist.exception.PlaylistNotFoundException;
import music.playlist.repository.PlaylistRepository;
import music.playlist.repository.SearchPlaylistRepository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService{
    private final PlaylistRepository playlistRepository;
    private final SearchPlaylistRepository searchPlaylistRepository;
    public PlaylistService(@Qualifier("memoryPlaylistRepo") PlaylistRepository playlistRepository,
            @Qualifier("memorySearchPlaylistRepo") SearchPlaylistRepository searchPlaylistRepository){
        this.playlistRepository=playlistRepository;
        this.searchPlaylistRepository=searchPlaylistRepository;         
    }
    //CRUD methods
    public Playlist createPlaylist(String playlistName){
        String playlistId=UUID.randomUUID().toString();
        Playlist playlist=new Playlist();
        playlist.setPlaylistId(playlistId);
        playlist.setPlaylistName(playlistName);
        this.playlistRepository.savePlaylist(playlist);
        this.searchPlaylistRepository.indexPlaylist(playlist);
        return playlist;
    }
    public Playlist createPlaylist(){
        String playlistName="Untitle";
        String playlistId=UUID.randomUUID().toString();
        Playlist playlist=new Playlist();
        playlist.setPlaylistId(playlistId);
        playlist.setPlaylistName(playlistName);
        this.playlistRepository.savePlaylist(playlist);
        this.searchPlaylistRepository.indexPlaylist(playlist);
        return playlist;
    }

    public Playlist getPlaylistById(String playlistId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        if(playlist==null){
            throw new PlaylistNotFoundException(playlistId);
        }
        return playlist;
    }

    public Playlist renamePlaylist(String playlistId,String newName){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        if(playlist==null){
            throw new PlaylistNotFoundException(playlistId);
        }
        searchPlaylistRepository.removePlaylist(playlistId);
        playlist.setPlaylistName(newName);
        this.playlistRepository.savePlaylist(playlist);
        this.searchPlaylistRepository.indexPlaylist(playlist);
        return playlist;
    } 

    public void deletePlaylist(String playlistId){
        Playlist playlist=this.getPlaylistById(playlistId);
        if(playlist==null){
            throw new PlaylistNotFoundException(playlistId);
        }
        this.playlistRepository.deletePlaylist(playlistId);
        this.searchPlaylistRepository.removePlaylist(playlistId);
    }

    //Search method
    public List<Playlist> searchPlaylistByName(String keyword){
        List<String> playlistIds=this.searchPlaylistRepository.searchPlaylistName(keyword);
        if(playlistIds.isEmpty() || playlistIds==null){
            throw new EmptyPlaylistException("playlist name "+keyword);
        }
        return this.playlistRepository.playlistBatchRetrieval(playlistIds);
    }
    
    //Track functions
    public void addTrack(String playlistId, String trackId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        if(playlist==null){
            throw new PlaylistNotFoundException(playlistId);
        }
        HashSet<String> trackIds=playlist.getTrackIds();
        trackIds.add(trackId);
        playlist.setTrackIds(trackIds);
        this.playlistRepository.savePlaylist(playlist);
    }

    public void removeTrack(String playlistId,String trackId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
        if(playlist==null){
            throw new PlaylistNotFoundException(playlistId);
        }
        HashSet<String> trackIds=playlist.getTrackIds();
        
        trackIds.remove(trackId);
        playlist.setTrackIds(trackIds);
        this.playlistRepository.savePlaylist(playlist);
    }

    public HashSet<String> loadTrackIds(String playlistId){
        Playlist playlist=this.playlistRepository.getPlaylistById(playlistId);
            if(playlist==null){
                throw new PlaylistNotFoundException(playlistId);
            }
            if(playlist.getTrackIds()==null){
                playlist.setTrackIds(new HashSet<>());
                this.playlistRepository.savePlaylist(playlist);
            }
        return playlist.getTrackIds();
    }

    public PlaylistResponseDTO fromPlaylist(Playlist playlist){
        PlaylistResponseDTO responseDTO=new PlaylistResponseDTO(playlist.getPlaylistId(),playlist.getPlaylistName());
        responseDTO.setTrackIds(playlist.getTrackIds());
        return responseDTO;
    }
}