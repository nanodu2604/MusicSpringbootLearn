package music.track.service;

import music.track.domain.Track;
import music.track.dto.TrackResponseDTO;
import music.track.dto.TrackUpdateDTO;
import music.track.dto.TrackRequestDTO;
import music.track.repository.SearchTrackRepository;
import music.track.repository.TrackRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
public class TrackService {
    private final SearchTrackRepository searchTrackRepository;
    private final TrackRepository trackRepository;
    
    public TrackService(@Qualifier("memoRepo") TrackRepository trackRepository,
    @Qualifier("memoSRepo") SearchTrackRepository searchTrackRepository){
        this.trackRepository=trackRepository;
        this.searchTrackRepository=searchTrackRepository;
    }
    
    public Track createTrack(TrackRequestDTO trackRequestDTO){
        Track track=new Track();
        String trackId=UUID.randomUUID().toString();
        track.setId(trackId);
        String artist=trackRequestDTO.getArtist();
        track.setArtist(artist);
        String trackTitle=trackRequestDTO.getTrackTitle();
        track.setTrackTitle(trackTitle);
        String genre=trackRequestDTO.getGenre();
        track.setGenre(genre);
        int duration=trackRequestDTO.getDuration();
        track.setDuration(duration);
        Instant createdAt=Instant.now();
        track.setCreatedAt(createdAt);
        LocalDate releaseDate=trackRequestDTO.getReleaseDate();
        if (releaseDate!=null){
            track.setReleaseDate(releaseDate);
        }
        trackRepository.saveTrack(track);
        searchTrackRepository.indexTrack(track);
        return track;
    }

    //response back to TrackResponseDTO
    public TrackResponseDTO fromTrack(Track track){
        TrackResponseDTO responseDTO=new TrackResponseDTO();
        responseDTO.setArtist(track.getArtist());
        responseDTO.setTrackTitle(track.getTrackTitle());
        responseDTO.setGenre(track.getGenre());
        responseDTO.setBitRate(track.getBitRate());
        responseDTO.setCreatedAt(track.getCreatedAt());
        responseDTO.setDuration(track.getDuration());
        responseDTO.setFileSize(track.getFileSize());
        responseDTO.setReleaseDate(track.getReleaseDate());
        responseDTO.setTrackId(track.getId());
        responseDTO.setUpdatedAt(track.getUpdatedAt());
        responseDTO.setUrl(track.getUrl());
        return responseDTO;
    }

    public void deleteTrack(String trackId){
        this.trackRepository.deleteTrack(trackId);
        this.searchTrackRepository.removeTrack(trackId);
    }

    //Update DTO
    public TrackResponseDTO updateTrack(String trackId,TrackUpdateDTO updateDTO){
        Track track=this.trackRepository.getTrackById(trackId);
        if (updateDTO.getTitle() != null) {
            this.searchTrackRepository.removeTrack(trackId);
            track.setTrackTitle(updateDTO.getTitle());
        }
    
        if (updateDTO.getArtist() != null) {
            track.setArtist(updateDTO.getArtist());
        }
    
        if (updateDTO.getGenre() != null) {
            track.setGenre(updateDTO.getGenre());
        }
    
        if (updateDTO.getDuration() != null) {
            track.setDuration(updateDTO.getDuration());
        }
    
        if (updateDTO.getReleaseDate() != null) {
            track.setReleaseDate(updateDTO.getReleaseDate());
        }
        Instant updatedAt=Instant.now();
        track.setUpdatedAt(updatedAt);
        this.trackRepository.saveTrack(track);
        this.searchTrackRepository.indexTrack(track);
        return fromTrack(track);
    } 
    
    //Search engine
    public List<Track> searchByTitle(String keyword){
        List<String> ids=this.searchTrackRepository.searchTitle(keyword);
        return this.trackRepository.trackBatchRetrieval(ids);
                    
    }

    public List<Track> getTracksByArtist(String artist){
        return this.trackRepository.getTracksByArtist(artist);
    }

    public List<Track> getTracksByGenre(String genre){
        return this.trackRepository.getTracksByGenre(genre);
    }
    public Track getTrackById(String trackId) {
        return this.trackRepository.getTrackById(trackId);
    }
    
    //EXTENSION: Page<Track> search(TrackSearchCriteria criteria);
    
}
