package music.playlist.dto;

import java.util.HashSet;

public class PlaylistResponseDTO {
    private String playlistId;
    private String playlistName;
    private HashSet<String> trackIds;

    public PlaylistResponseDTO(String playlistId, String playlistName){
        this.playlistId=playlistId;
        this.playlistName=playlistName;
    }

    public String getPlaylistId(){
        return this.playlistId;
    }
    public void setPlaylistId(String playlistId){
        this.playlistId=playlistId;
    }

    public String getPlaylistName(){
        return this.playlistName;
    }
    public void setPlaylistName(String playlistName){
        this.playlistName=playlistName;
    }

    public HashSet<String> getTrackIds(){
        return this.trackIds;
    }
    public void setTrackIds(HashSet<String> trackIds){
        this.trackIds=trackIds;
    }
}
