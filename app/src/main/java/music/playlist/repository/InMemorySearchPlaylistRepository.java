package music.playlist.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import music.playlist.domain.Playlist;

@Repository("memorySearchPlaylistRepo")
public class InMemorySearchPlaylistRepository implements SearchPlaylistRepository {
    private final Map<String, Set<String>> playlistIndex=new HashMap<>();
    @Override
    public List<String> searchPlaylistName(String keyword) {
        String[] keyWordList=keyword.split(" ");
        Set<String>result= this.playlistIndex
                    .getOrDefault(keyWordList[0].toLowerCase(), Set.of());
        int N=keyWordList.length;
        for(int i=1;i<N;i++){
            Set<String> l=this.playlistIndex
                    .getOrDefault(keyWordList[i].toLowerCase(), Set.of());
            result.retainAll(l);
        }
        return new ArrayList<>(result);
    }
    
    @Override
    public void indexPlaylist(Playlist playlist) {
        String playlistName=playlist.getPlaylistName();
        String playlistId=playlist.getPlaylistId();
        for(String word:playlistName.split(" ")){
            this.playlistIndex
                    .computeIfAbsent(word.toLowerCase(), k->new HashSet<>())
                    .add(playlistId);
        }
    }

    @Override
    public void removePlaylist(String playlistId) {
        for (Set<String> ids : playlistIndex.values()) {
            ids.remove(playlistId);
        }
    }

    @Override
    public Map<String, Set<String>> getMapIndex() {
        return this.playlistIndex;
    }
}
