package noteplayer.player;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio  {
    
    private AudioInputStream stream;
    private Clip clip;
    private File audioFile;
    
    // This play-method can be given the current directory
    // as a string, and it will append the given filepath 
    // to that
    public void play(String file, String curDir)  {
        
        // Stop any ongoing playback before starting the new one
        if (clip != null)   {
            clip.stop();
        }
        
        try {
            audioFile = new File(curDir + "/" + file);
            stream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
    
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
        if (clip == null) return;
        clip.stop();
    }
    
    public void unpausePlayback()   {
        clip.start();
    }
    
    
    
    
    
}