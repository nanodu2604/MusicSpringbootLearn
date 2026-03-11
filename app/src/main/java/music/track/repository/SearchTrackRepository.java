package music.track.repository;
import music.track.domain.Track;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public interface SearchTrackRepository {
    List<String> searchTitle(String keyworld);
    void indexTrack(Track track);
    void removeTrack(String trackId);
    Map<String, Set<String>> getMapIndex();
}
