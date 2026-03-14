package music.player.service;
import music.playlist.service.PlaylistService;
import music.player.domain.PlayerState;
import music.player.domain.PlayerStatus;
import music.player.domain.RepeatMode;
import music.player.dto.PlayerCommandDTO;
import music.player.repository.PlayerRepository;
import music.track.domain.Track;
import music.track.service.TrackService;

import music.player.exception.PlayerNotFoundException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PlayerService {
    //Early state: no database so that will store user so far
    private PlaylistService playlistService;
    private TrackService trackService;

    //Construction
    private final PlayerRepository playerRepository;
    public PlayerService(@Qualifier("memoryPlayerRepo") PlayerRepository playerRepository){
        this.playerRepository=playerRepository;
    }

    public void removeUser(String userId){
        this.playerRepository.removeUser(userId);
    }
    //

    //this testing and controller GET only
    public PlayerState getPlayerState(String userId){
        PlayerState player=this.playerRepository.getPlayerStateById(userId);
        if(player==null){
            throw new PlayerNotFoundException(userId);
        }
        return player;
    }
    //

    public PlayerState applyCommand(PlayerCommandDTO commandDTO){
        PlayerState state= playerRepository.getPlayerStateById(commandDTO.getUserId());
        if(state==null){
            state=new PlayerState();
            state.setUserId(commandDTO.getUserId());
            state.setStatus(PlayerStatus.PAUSED);
        }

        switch (commandDTO.getCommand()) {
            case PLAY -> state.setStatus(PlayerStatus.PLAYING);
            case PAUSE -> state.setStatus(PlayerStatus.PAUSED);
            case RESTART -> state.setPosition(0);
            case SEEK_FORWARD -> handleSeekForward(state,commandDTO);
            case SEEK_BACKWARD -> handleSeekBackward(state, commandDTO);
            case NEXT ->handleNext(state);
            case PREVIOUS ->handlePrevious(state);
            case SHUFFLE_TOGGLE -> handleShuffle(state);
            case REPEAT_TOGGLE -> handleRepeat(state);
            case MUTE-> state.setMute(!state.getMute());
        }   
        this.playerRepository.savePlayerState(state);
        return state;
    }
        
    //all function handling
    public void handleSeekForward(PlayerState state,PlayerCommandDTO commandDTO){
        if (commandDTO.getPosition() == null) {
            throw new IllegalArgumentException("Position offset is required");
        }
    
        int newPosition = state.getPosition() + commandDTO.getPosition();
    
        if (newPosition > state.getDuration()) {
            newPosition = state.getDuration();
        }
    
        state.setPosition(newPosition);
    
    }

    public void handleSeekBackward(PlayerState state,PlayerCommandDTO commandDTO){
        if (commandDTO.getPosition() == null) {
            throw new IllegalArgumentException("Position offset is required");
        }
    
        int newPosition = state.getPosition() - commandDTO.getPosition();
    
        if (newPosition < 0) {
            newPosition = 0;
        }
    
        state.setPosition(newPosition);
    }

    public void handleNext(PlayerState state){
        List<String> trackIds=state.getTrackIds();
        int size=trackIds.size();
        if(size==0) return;

        //Handle Repeat
        // Repeat ONE
        if (state.getRepeatMode() == RepeatMode.ONE) {
            state.setPosition(0);
            state.setStatus(PlayerStatus.PLAYING);
            return;
        }
        int nextIndex;
        if(state.isShuffle()){
            int pointer = state.getshufflePointer() + 1;

            if (pointer >= state.getShuffledOrder().size()) {
                if (state.getRepeatMode() == RepeatMode.ALL) {
                    pointer = 0;
                } else {
                    return;
                }
            }
            state.setShufflePointer(pointer);
            nextIndex = state.getShuffledOrder().get(pointer);
        }else{
            nextIndex = state.getCurrentTrackIndex() + 1;

            if (nextIndex >= size) {
                if (state.getRepeatMode() == RepeatMode.ALL) {
                    nextIndex = 0;
                } else {
                    return;
                }
            }
        }
        
        applyTrackChange(state, trackIds, nextIndex);
    }
    
    //Updated latest: check out the track ini in apply command
    public void handlePrevious(PlayerState state){
        List<String> trackIds=state.getTrackIds();
        int size=trackIds.size();
        if(size==0){
            return; 
        }
        if(state.getRepeatMode()==RepeatMode.ONE){
            state.setPosition(0);
            state.setStatus(PlayerStatus.PLAYING);
            return;
        }
        //shuffle check
        int prevIndex;
        if(state.isShuffle()){
            int pointer=state.getshufflePointer()-1;
            if(pointer<0){
                if(state.getRepeatMode()==RepeatMode.ALL){
                    pointer=state.getShuffledOrder().size()-1;
                }
                else{
                    return;
                }
            }

            state.setShufflePointer(pointer);
            prevIndex=state.getShuffledOrder().get(pointer);
        }
        else{
            prevIndex = (state.getCurrentTrackIndex() - 1);
            if(prevIndex<0){
                if(state.getRepeatMode()==RepeatMode.ALL){
                    prevIndex=state.getTrackIds().size()-1;
                }
                else{
                    return;
                }
            } 
        }

        applyTrackChange(state, trackIds, prevIndex); 
    }

    //helper
    public void applyTrackChange(PlayerState state, List<String>trackIds,int index){
        state.setCurrentTrackIndex(index);
        state.setPosition(0);
        state.setStatus(PlayerStatus.PLAYING);
    }

    public void handleShuffle(PlayerState state){
        int trackSize=state.getTrackIds().size();
        state.setShuffle(!state.isShuffle());

        if (state.isShuffle()) {
            //Pre-shuffle step set up.
            List<Integer> order = IntStream.range(0, trackSize)
                    .boxed()
                    .collect(Collectors.toList());

            Collections.shuffle(order);
            
            //Exception: remove the current index in case song play again
            order.remove(Integer.valueOf(state.getCurrentTrackIndex()));
            order.add(0, state.getCurrentTrackIndex());

            state.setShuffledOrder(order);
            state.setShufflePointer(0);
        } else {
            state.setShuffledOrder(null);
            state.setShufflePointer(0);
        }
    }
    public void handleRepeat(PlayerState state){
        RepeatMode next = switch (state.getRepeatMode()) {
        case OFF -> RepeatMode.ALL;
        case ALL -> RepeatMode.ONE;
        case ONE -> RepeatMode.OFF;
    };

    state.setRepeatMode(next);
    }

    //load playlist
    public PlayerState loadPlaylist(String urserId,String playlistId) {
        PlayerState state=this.playerRepository.getPlayerStateById(urserId);
        List<String> trackIds=new ArrayList<>(playlistService.loadTrackIds(playlistId));//Borrow playlist service
        state.setPlaylistId(playlistId);
        state.setTrackIds(trackIds);
        state.setCurrentTrackIndex(0);
        state.setPosition(0);
        state.setStatus(PlayerStatus.PLAYING);
        String firstTrackId=trackIds.get(0);
        Track firstTrack=trackService.getTrackById(firstTrackId);
        state.setDuration(firstTrack.getDuration());
        this.playerRepository.savePlayerState(state);
        return state;
    }
}

