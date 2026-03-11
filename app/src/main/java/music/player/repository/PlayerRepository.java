package music.player.repository;

import music.player.domain.PlayerState;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository {
    PlayerState getPlayerStateById(String userId);
    void savePlayerState(PlayerState state);
    void removeUser(String userId);
    List<PlayerState> findAllPlayerStates();
    List<PlayerState> playerBatchRetrieval(List<String>userIds);
}
