package music.player.repository;
import music.player.domain.PlayerState;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository("memoryPlayerRepo")
public class InMemoryPlayerRepository implements PlayerRepository{
    private final Map<String,PlayerState> players=new HashMap<>();
    @Override
    public PlayerState getPlayerStateById(String userId){
        return players.get(userId);
    }

    @Override
    public void savePlayerState(PlayerState state){
        String userId=state.getUserId();
        players.put(userId,state);
    }

    @Override
    public List<PlayerState> findAllPlayerStates(){
        return new ArrayList<>(this.players.values());
    }

    @Override
    public void removeUser(String userId){
        this.players.remove(userId);
    }

    @Override 
    public List<PlayerState> playerBatchRetrieval(List<String> userIds){
        return userIds.stream().map(this.players::get).toList();
    }
}
