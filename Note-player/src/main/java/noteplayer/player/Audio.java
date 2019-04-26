package noteplayer.player;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Used for audio playback using java clips and streams, as 
 * opposed to the JavaFX media player. Doesn't support MP3. 
 * Kept around just in case as JavaFX is rather temperamental.
 * 
 * @author teo
 */
public class Audio  {
    
    private AudioInputStream stream;
    private Clip clip;
    private File audioFile;
    final private String DS;
    final private String regexDS;
    
    public Audio()  {
        if (System.getProperty("os.name").contains("Windows"))   {
            DS = "\\";
            regexDS = "\\\\";
        } else  {
            DS = "/";
            regexDS = "/";
        }
    }
    
    /**
     * Attempts to play a file with the given name, located in the given
     * directory. Combines the file and current directory strings to get 
     * the full, usable path to the file.
     * 
     * @param file the file to play, as file.toString()
     * @param curDir the current directory, as directory.toString()
     */
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
    
    /**
     * Attempts to play the given file.
     * 
     * @param file audio file as a java File-object
     */
    public void play(File file)  {
        
        // Stop any ongoing playback before starting the new one
        if (clip != null)   {
            clip.stop();
        }
        
        try {
            audioFile = file;
            stream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
    
    public File getCurrentlyPlayingFile()   {
        return audioFile;
    }
    
    /**
     * Returns the name of the currently playing audio, without the
     * preceding file path.
     * 
     * @return the currently playing audio.
     */
    public String getCurrentlyPlayingFormattedString()  {
        if (audioFile == null)  {
            return null;
        }
        String song = audioFile.toString();
        String[] split = song.split(regexDS);
        return split[split.length - 1];
    }
    
    public String getCurrentlyPlayingString()   {
        if (audioFile == null)  {
            return null;
        }
        return audioFile.toString();
    }
    
    /**
     * Attempts to play a file at the given file path. 
     * 
     * @param file full file path as file.toString()
     */
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
    
    /**
     * Resumes the playback if paused, and vice versa.
     */
    public void togglePause()   {
        if (clip == null)   {
            return;
        }
        if (clip.isActive())    {
            clip.stop();
        } else  {
            clip.start();
        }
    }
    
    /**
     * Pauses audio playback.
     */
    public void pausePlayback()  {
        if (clip == null)   {
            return;
        }
        clip.stop();
    }
    
    /**
     * Resumes audio playback.
     */
    public void unpausePlayback()   {
        clip.start();
    }
}