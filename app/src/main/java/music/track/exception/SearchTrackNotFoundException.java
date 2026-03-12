package music.track.exception;

public class SearchTrackNotFoundException extends RuntimeException {
    public SearchTrackNotFoundException(String message){
        super(message);
    }
}
