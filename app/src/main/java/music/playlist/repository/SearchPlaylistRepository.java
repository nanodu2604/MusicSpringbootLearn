package music.playlist.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import music.playlist.domain.Playlist;

@Repository
public interface SearchPlaylistRepository {
    List<String> searchPlaylistName(String keyword);
    void indexPlaylist(Playlist playlist);
    void removePlaylist(String playlistId);
    Map<String, Set<String>> getMapIndex();
}
