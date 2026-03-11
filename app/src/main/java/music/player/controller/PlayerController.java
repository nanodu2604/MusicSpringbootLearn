package music.player.controller;

import music.player.domain.PlayerState;
import music.player.dto.PlayerCommandDTO;
import music.player.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/state")
    public PlayerState getPlayerState(@RequestParam String userId){
        return playerService.getPlayerState(userId);
    }

    @PostMapping("/command")
    public PlayerState applyCommand(@RequestBody PlayerCommandDTO commandDTO){
        return playerService.applyCommand(commandDTO);
    }

    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable String userId){
        playerService.removeUser(userId);
    }

    @PostMapping("/{userId}/playlist/{playlistId}")
    public PlayerState loadPlaylist(@PathVariable String userId,@PathVariable String playlistId ) {
        return playerService.loadPlaylist(userId, playlistId);
    }
    
}
