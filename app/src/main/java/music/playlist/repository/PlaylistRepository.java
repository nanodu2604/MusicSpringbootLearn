package music.playlist.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import music.playlist.domain.Playlist;

@Repository
public interface PlaylistRepository {
    void savePlaylist(Playlist playlist);
    Playlist getPlaylistById(String playlistId);
    void deletePlaylist(String playlistId);
    List<Playlist> findAllPlaylists();
    List<Playlist> playlistBatchRetrieval(List<String> playlistIds);
}
