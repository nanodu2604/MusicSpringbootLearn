package music.track.dto;

import java.time.Instant;
import java.time.LocalDate;

public class TrackResponseDTO {
    private String id;
    private String title;
    private String artist;
    private String genre;
    private int duration;
    private String url;
    private int bitRate;
    private long fileSize;
    private LocalDate releaseDate;
    private Instant createdAt;
    private Instant updatedAt;

    public void setTrackId(String trackId){
        this.id=trackId;
    }
    public String getTrackId(){
        return this.id;
    }

    public String getTrackTitle(){
        return this.title;
    }
    public void setTrackTitle(String trackTitle){
        this.title=trackTitle;
    }

    public String getArtist(){
        return this.artist;
    }
    public void setArtist(String artist){
        this.artist=artist;
    }

    public String getGenre(){
        return this.genre;
    }
    public void setGenre(String genre){
        this.genre=genre;
    }

    public int getDuration(){
        return this.duration;
    }
    public void setDuration(int duration){
        this.duration=duration;
    }
    
    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){
        return this.url;
    }

    public int getBitRate(){
        return this.bitRate;
    }
    public void setBitRate(int bitRate){
        this.bitRate=bitRate;
    }

    public long getFileSize(){
        return this.fileSize;
    }
    public void setFileSize(long fileSize){
        this.fileSize=fileSize;
    }
    
    public void setReleaseDate(LocalDate releaseDate){
        this.releaseDate=releaseDate;
    }
    public LocalDate getReleaseDate(){
        return this.releaseDate;
    }

    public Instant getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(Instant createdAt){
        this.createdAt=createdAt;
    }

    public Instant getUpdatedAt(){
        return this.updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt){
        this.updatedAt=updatedAt;
    }
}

