package music.playlist.repository;

import music.playlist.domain.Playlist;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository("memoryRepo")
public class InMemoryPlaylistRepository implements PlaylistRepository{
    private Map<String,Playlist> playlists=new HashMap<>();

    @Override
    public Playlist getPlaylistById(String playlistId){
        return this.playlists.get(playlistId);
    }

    @Override
    public void savePlaylist(Playlist playlist){
        String playlistId=playlist.getPlaylistId();
        this.playlists.put(playlistId,playlist);
    }

    @Override
    public void deletePlaylist(String playlistId){
        this.playlists.remove(playlistId);
    }

    @Override
    public List<Playlist> findAllPlaylists(){
        return new ArrayList<>(this.playlists.values());
    }

    @Override
    public List<Playlist> playlistBatchRetrieval(List<String> playlistIds){
        return playlistIds.stream().map(this.playlists::get).toList();
    }
}
