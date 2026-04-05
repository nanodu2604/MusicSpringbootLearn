package music.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import music.player.exception.PlayerNotFoundException;
import music.playlist.exception.PlaylistNotFoundException;
import music.track.exception.EmptyTracksException;
import music.track.exception.SearchTrackNotFoundException;
import music.track.exception.TrackNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TrackNotFoundException.class)
    public ResponseEntity<String> handleTrackNotFound(TrackNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(SearchTrackNotFoundException.class)
    public ResponseEntity<String> handleSearchTrackNotFound(SearchTrackNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(PlaylistNotFoundException.class)
    public ResponseEntity<String> handlePlaylistNotFound(PlaylistNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handlePlayerNotFound(PlayerNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(EmptyTracksException.class)
    public ResponseEntity<String> handleEmptyTracks(EmptyTracksException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
