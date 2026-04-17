package music.track.service;

import music.track.domain.Track;
import music.track.dto.TrackResponseDTO;
import music.track.dto.TrackUpdateDTO;
import music.track.dto.TrackRequestDTO;
import music.track.repository.SearchTrackRepository;
import music.track.repository.TrackRepository;
import music.track.exception.EmptyTracksException;
import music.track.exception.SearchTrackNotFoundException;
import music.track.exception.TrackNotFoundException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
    private final SearchTrackRepository searchTrackRepository;
    private final TrackRepository trackRepository;
    
    public TrackService(@Qualifier("memoryTrackRepo") TrackRepository trackRepository,
    @Qualifier("memorySearchTrackRepo") SearchTrackRepository searchTrackRepository){
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
        if(track==null){
            throw new TrackNotFoundException(trackId);
        }
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
        if(updateDTO!=null){
            track.setUpdatedAt(updatedAt);
        }
        this.trackRepository.saveTrack(track);
        this.searchTrackRepository.indexTrack(track);
        return fromTrack(track);
    } 
    
    //Search engine
    public List<Track> searchByTitle(String keyword){
        List<String> ids=this.searchTrackRepository.searchTitle(keyword);
        if(ids==null || ids.size()==0){
            throw new SearchTrackNotFoundException(String.format("Your keyword %s not found", keyword));
        }
        return this.trackRepository.trackBatchRetrieval(ids);
    }

    //TODO: for consistency, should be changed to match to search by title controller
    public List<Track> getTracksByArtist(String artist){
        List <Track> tracks=this.trackRepository.getTracksByArtist(artist);
        if (tracks.isEmpty()|| tracks==null){
            throw new EmptyTracksException("artist search "+artist);
        }
        return tracks;
    }

    public List<Track> getTracksByGenre(String genre){
        List<Track> tracks=this.trackRepository.getTracksByGenre(genre);
        if(tracks.isEmpty()||tracks==null){
            throw new EmptyTracksException("genre search "+genre);
        }
        return tracks;
    }
    public Track getTrackById(String trackId) {
        Track track=this.trackRepository.getTrackById(trackId);
        if(track==null){
            throw new TrackNotFoundException(trackId);
        }
        return track;
    }
    
    //EXTENSION: Page<Track> search(TrackSearchCriteria criteria);
    
}
