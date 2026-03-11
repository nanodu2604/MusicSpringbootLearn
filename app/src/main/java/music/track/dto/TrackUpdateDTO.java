package music.track.dto;

import java.time.LocalDate;

public class TrackUpdateDTO {
    private String title;
    private String artist;
    private String genre;
    private Integer duration;
    private LocalDate releaseDate;
    
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title=title;
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

    public Integer getDuration(){
        return this.duration;
    }
    public void setDuration(int duration){
        this.duration=duration;
    }

    public LocalDate getReleaseDate(){
        return this.releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate){
        this.releaseDate=releaseDate;
    }
}
