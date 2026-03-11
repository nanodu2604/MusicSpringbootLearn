package music.playlist.domain;
import java.util.HashSet;

public class Playlist {
    private String playlistId;
    private String playlistName;
    private HashSet<String> trackIds;

    public Playlist(){
        this.trackIds=new HashSet<>();
    }
    //Setter,getter
    public HashSet<String> getTrackIds(){
        return this.trackIds;
    }
    public void setTrackIds(HashSet<String> trackIds){
        this.trackIds=trackIds;
    }

    public String getPlaylistName(){
        return this.playlistName;
    }
    public void setPlaylistName(String playlistName){
        this.playlistName=playlistName;
    }

    public String getPlaylistId(){
        return this.playlistId;
    }
    public void setPlaylistId(String playlistId){
        this.playlistId=playlistId;
    }
}
