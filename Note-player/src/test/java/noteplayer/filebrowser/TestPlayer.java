package noteplayer.filebrowser;

import java.io.File;
import noteplayer.player.FxPlayer;

public class TestPlayer extends FxPlayer {
    
    private String currentlyPlaying;
    
    public void setCurrentlyPlaying(String s)   {
        currentlyPlaying = s;
    }
    
    @Override
    public String getCurrentlyPlayingString() {
        return currentlyPlaying;
    }
    
    @Override 
    public void play(File file) {
        currentlyPlaying = file.toString();
    }
    
    @Override
    public void play(String file)   {
        currentlyPlaying = file;
    }
    
    @Override
    public boolean prev()   {
        return true;
    }
    
    
}
