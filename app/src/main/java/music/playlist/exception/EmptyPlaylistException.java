package music.playlist.exception;

public class EmptyPlaylistException extends NullPointerException {
    public EmptyPlaylistException(String message){
        super("No result: "+message);
    }
}
