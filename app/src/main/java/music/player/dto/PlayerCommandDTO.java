package music.player.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import music.player.domain.PlayerCommand;

public class PlayerCommandDTO {
    @NotBlank
    private String userId;

    @NotBlank
    private PlayerCommand command;
    
    @PositiveOrZero
    private Integer position; 
    private String trackId; 

    //getters and setters
    public void setUserId(String userId) {
        this.userId=userId;
    }
    public String getUserId(){
        return this.userId;
    }

    public void setCommand(PlayerCommand command) {
        this.command=command;
    }
    public PlayerCommand getCommand(){
        return this.command;
    }

    public Integer getPosition() {
        return this.position;
    }
    public void setPosition(Integer position){
        this.position=position;
    }
    
    public void setTrackId(String trackId){
        this.trackId=trackId;
    }
    public String getTrackId(){
        return this.trackId;
    }
    
}
