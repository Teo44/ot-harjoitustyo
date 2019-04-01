package noteplayer.player;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio  {
    
    private AudioInputStream stream;
    private Clip clip;
    private File audioFile;
    
    public void play(String file)  {
        
        // Stop any ongoing playback before starting the new one
        if (clip != null)   {
            clip.stop();
        }
        
        try {
            audioFile = new File(file);
            stream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
    
    public boolean isPlaying()  {
        return clip.isActive();
    }
    
    public void pausePlayback()  {
        clip.stop();
    }
    
    public void unpausePlayback()   {
        clip.start();
    }
    
    
    
    
    
}