package music.track.domain;

import java.time.Instant;
import java.time.LocalDate;

public class Track {
    private String id;
    private String trackTitle;
    private String artist;
    private int duration;
    private String genre;
    private String url;
    private long fileSize;
    private int bitRate;
    private LocalDate releaseDate;
    private Instant createdAt;
    private Instant updatedAt;

    //getter, setter methods
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id=id;
    }

    public String getTrackTitle(){
        return this.trackTitle;
    }
    public void setTrackTitle(String trackTitle){
        this.trackTitle=trackTitle;
    }

    public String getArtist(){
        return this.artist;
    }
    public void setArtist(String artist){
        this.artist=artist;
    }

    public Integer getDuration(){
        return this.duration;
    }
    public void setDuration(int duration){
        this.duration=duration;
    }

    public String getGenre(){
        return this.genre;
    }
    public void setGenre(String genre){
        this.genre=genre;
    }

    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){
        return this.url;
    }

    public void setFileSize(long fileSize){
        this.fileSize=fileSize;
    }
    public long getFileSize(){
        return this.fileSize;
    }

    public void setBitRate(int bitRate){
        this.bitRate=bitRate;
    }
    public int getBitRate(){
        return this.bitRate;
    }

    public void setReleaseDate(LocalDate releaseDate){
        this.releaseDate=releaseDate;
    }
    public LocalDate getReleaseDate(){
        return this.releaseDate;
    }

    public void setCreatedAt(Instant createdAt){
        this.createdAt=createdAt;
    }
    public Instant getCreatedAt(){
        return this.createdAt;
    }

    public void setUpdatedAt(Instant updatedAt){
        this.updatedAt=updatedAt;
    }
    public Instant getUpdatedAt(){
        return this.updatedAt;
    }
}
