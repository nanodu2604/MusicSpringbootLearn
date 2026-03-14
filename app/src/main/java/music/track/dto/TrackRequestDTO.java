package music.track.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Past;

public class TrackRequestDTO {
    private String genre;

    @NotBlank(message = "Artist must not be empty")
    private String artist;

    @NotBlank(message = "title cannot be empty")
    private String trackTitle;

    @Positive(message="There is no way duration is not 0")
    private int duration;

    @Past(message="The date must be in the past")
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
