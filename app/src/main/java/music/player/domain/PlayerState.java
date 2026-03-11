package music.player.domain;

import java.util.List;

public class PlayerState {
    private String userId;
    private String playlistId; //will use for the database reference 
    
    //track information  
    private int currentTrackIndex;   //navigation
    private List<String> trackIds;  //This is the queue status (not all in trackId)
    
    //playback modes
    private PlayerStatus status; //state: playing or paused
    private RepeatMode repeat; //repeat control
    private boolean shuffle; 
    private boolean muted;

    //shuffle attributes
    private List<Integer> shuffledOrder;
    private int shufflePointer;

    //Time tracking
    private int position; //calculate in samples for int()
    private int duration; // samples: time=samples*sample/sec
    
    
    //getters and setter
    public String getUserId(){
        return this.userId;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }

    public PlayerStatus getStatus(){
        return this.status;
    }
    public void setStatus(PlayerStatus status){
        this.status=status;
    }
    
    public void setPosition(int position){
        this.position=position;
    }
    public int getPosition(){
        return this.position;
    }

    public int getDuration(){
        return this.duration;
    }
    public void setDuration(int duration){
        this.duration=duration;
    }

    public int getCurrentTrackIndex(){
        return this.currentTrackIndex;
    }
    public void setCurrentTrackIndex(int currentTrackIndex){
        this.currentTrackIndex=currentTrackIndex;
    }


    public void setShuffle(boolean shuffle){
        this.shuffle=shuffle;
    }
    public boolean isShuffle(){
        return this.shuffle;
    }

    public void setShuffledOrder(List<Integer> order){
        this.shuffledOrder=order;
    }
    public List<Integer> getShuffledOrder(){
        return this.shuffledOrder;
    }

    public void setShufflePointer(int shufflePointer){
        this.shufflePointer=shufflePointer;
    }
    public int getshufflePointer(){
        return this.shufflePointer;
    }

    public RepeatMode getRepeatMode(){
        return this.repeat;
    }
    public void setRepeatMode(RepeatMode repeat){
        this.repeat=repeat;
    }
    
    public void setPlaylistId(String playlistId){
        this.playlistId=playlistId;
    }
    public String getPlaylistId(){
        return this.playlistId;
    }

    public void setTrackIds(List<String> trackIds){
        this.trackIds=trackIds;
    }
    public List<String> getTrackIds(){
        return this.trackIds;
    }

    public void setMute(boolean muted){
        this.muted=muted;
    }
    public boolean getMute(){
        return this.muted;
    }
}
