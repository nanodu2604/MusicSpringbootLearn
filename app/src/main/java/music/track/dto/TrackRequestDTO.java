package music.track.dto;

import java.time.LocalDate;

public class TrackRequestDTO {
    private String genre;
    private String artist;
    private String trackTitle;
    private int duration;
    private LocalDate releaseDate;
    
    public String getGenre(){
        return this.genre;
    }
    public void setGenre(String genre){
        this.genre=genre;
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

    public int getDuration(){
        return this.duration;
    }
    public void setDuration(int duration){
        this.duration=duration;
    }

    public void setReleaseDate(LocalDate releaseDate){
        this.releaseDate=releaseDate;
    }
    public LocalDate getReleaseDate(){
        return this.releaseDate;
    }
}
