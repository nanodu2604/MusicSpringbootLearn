package music.track.repository;

import music.track.domain.Track;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository {
    List<Track> findAllTracks();
    void saveTrack(Track track);
    void deleteTrack(String trackId);
    Track getTrackById(String trackId);
    List<Track> trackBatchRetrieval(List<String> trackIds);
    List<Track> getTracksByArtist(String artist);
    List<Track> getTracksByGenre(String genre);
}
