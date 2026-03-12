package music.player.exception;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String userId){
        super(String.format("Player of user id %s not found.",userId));
    }
}
