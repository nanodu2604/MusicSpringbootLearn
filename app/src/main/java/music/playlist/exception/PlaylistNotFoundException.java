package music.playlist.exception;

public class PlaylistNotFoundException extends RuntimeException {
    public PlaylistNotFoundException(String playlistId){
        super(String.format("Playlist with id %s not found. ",playlistId));
    }
}
