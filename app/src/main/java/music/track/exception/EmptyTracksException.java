package music.track.exception;

public class EmptyTracksException extends NullPointerException{
    public EmptyTracksException(String message){
        super("there is no match to your result based on "+ message);
    }
}
