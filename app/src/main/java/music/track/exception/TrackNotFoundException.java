package music.track.exception;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(String trackId){
        super(String.format("Track with id %s not found.",trackId));
    }
}
