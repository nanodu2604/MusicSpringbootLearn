package music.track.repository;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import music.track.domain.Track;

@Repository("memoryRepo")
public class InMemoryTrackRepository implements TrackRepository {
    private Map<String, Track>tracks=new HashMap<>();
    private Map<String, Set<String>> artistIndices=new HashMap<>();
    private Map<String,Set<String>> genreIndices=new HashMap<>();

    @Override
    public List<Track> findAllTracks(){
        return new ArrayList<>(this.tracks.values());
    }

    @Override
    public void saveTrack(Track track){
        String trackId=track.getId();
        this.tracks.put(trackId,track);
        this.artistIndices.computeIfAbsent(track.getArtist(),(k)->new HashSet<>())
                          .add(trackId);
        this.genreIndices.computeIfAbsent(track.getGenre(),(k)->new HashSet<>())
                          .add(trackId);
    }

    @Override
    public void deleteTrack(String trackId){
        this.tracks.remove(trackId);
    }

    @Override
    public Track getTrackById(String trackId){
        return this.tracks.get(trackId);
    }

    @Override
    public List<Track> trackBatchRetrieval(List<String> trackIds){
        return trackIds.stream().map(this.tracks::get).toList();
    }

    @Override
    public List<Track> getTracksByArtist(String artist){
        return this.artistIndices.getOrDefault(artist,Set.of())
                .stream()
                .map(this.tracks::get)
                .toList();
    }

    @Override
    public List<Track> getTracksByGenre(String genre){
        return this.genreIndices.getOrDefault(genre,Set.of())
        .stream()
        .map(this.tracks::get)
        .toList();
    }
}
