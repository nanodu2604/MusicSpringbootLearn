package music.track.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import music.track.domain.Track;

@Repository("memorySearchTrackRepo")
public class InMemorySearchTrackRepository implements SearchTrackRepository{
    private Map<String, Set<String>> titleIndex = new HashMap<>(); 

    @Override
    public void indexTrack(Track track) {

        String[] words = track.getTrackTitle().toLowerCase().split(" ");

        for (String word : words) {
            this.titleIndex
                .computeIfAbsent(word, k -> new HashSet<>())
                .add(track.getId());
        }
    }

    /**
    *Find all track ids for keyword.
    * <p>
    * @param keyword The keyword to search
    * @return ids: List of all id
    */
    @Override
    public List<String> searchTitle(String keyword) {
        String[] keyWordList=keyword.split(" ");
        Set<String>result= this.titleIndex.getOrDefault(keyWordList[0].toLowerCase(), Set.of());
        int N=keyWordList.length;
        for(int i=1;i<N;i++){
            Set<String> l=this.titleIndex.getOrDefault(keyWordList[i].toLowerCase(), Set.of());
            result.retainAll(l);
        }
        return new ArrayList<>(result);
    }

    @Override
    public void removeTrack(String trackId) {
        for (Set<String> ids : titleIndex.values()) {
            ids.remove(trackId);
        }
    }

    @Override
    public Map<String, Set<String>> getMapIndex(){
        return this.titleIndex;
    }    
}
