package noteplayer.player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * Used for audio playback with the JavaFx media player
 * @author teo
 */
public class FxPlayer {
    
    final private String directorySeparator;
    final private String regexDS;
    private Media media;
    private MediaPlayer mediaPlayer;
    private String currentlyPlaying;
    private Boolean isPlaying;
    private Boolean repeat;
    
    /**
     * Sets the directory separator final variables depending
     * on the OS, as Windows uses "\" and Unix "/".
     * 
     * Also sets isPlaying and repeat to the default false.
     */
    public FxPlayer()  {
        if (System.getProperty("os.name").contains("Windows"))   {
            directorySeparator = "\\";
            regexDS = "\\\\";
        } else  {
            directorySeparator = "/";
            regexDS = "/";
        }
        isPlaying = false;
        repeat = false;
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
        if (mediaPlayer != null)   {
            mediaPlayer.stop();
            isPlaying = false;
        }
        
        try {
            media = new Media(new File(curDir + directorySeparator + file).toURI().toString());
            currentlyPlaying = curDir + directorySeparator + file;
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            isPlaying = true;
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
        if (mediaPlayer != null)   {
            mediaPlayer.stop();
            isPlaying = false;
        }
        
        try {
            media = new Media(file.toURI().toString());
            currentlyPlaying = file.toString();
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            isPlaying = true;
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
    
    /**
     * Attempts to play a file at the given file path. 
     * 
     * @param file full file path as file.toString()
     */
    public void play(String file)  {
        
        // Stop any ongoing playback before starting the new one
        if (mediaPlayer != null)   {
            mediaPlayer.stop();
            isPlaying = false;
        }
        
        try {
            media = new Media(new File(file).toURI().toString());
            currentlyPlaying = file;
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            isPlaying = true;
        } catch (Exception e)   {
            System.out.print("Could not play file: ");
            System.out.println(e);
        }
        
    }
    
    /**
     * Returns the name of the currently playing audio, without the
     * preceding file path.
     * 
     * @return the currently playing audio.
     */
    public String getCurrentlyPlayingFormattedString()  {
        if (media == null)  {
            return null;
        }
        //String song = mediaPlayer.toString();
        String[] split = currentlyPlaying.split(regexDS);
        return split[split.length - 1];
    }
    
    /**
     * 
     * @return the full file path to the currently playing file
     */
    public String getCurrentlyPlayingString()   {
        return currentlyPlaying;
    }
    
    /**
     * If the song playback is at 5 or more seconds, moves the playback 
     * to the beginning and returns false. If the playback is at less 
     * than 5 seconds, returns true. 
     * 
     * @see noteplayer.filebrowser.Browser#previousSong(FxPlayer) 
     * 
     * @return true if playback at less than 5 seconds.
     */
    public boolean prev()  {
        if (mediaPlayer == null)    {
            return false;
        }
        // starts the song from the beginning
        if (isPlaying())    {
            if (mediaPlayer.getCurrentTime().toSeconds() < 5)   {
                return true;
            }
            mediaPlayer.stop();
            mediaPlayer.play();
        } else  {
            mediaPlayer.stop();
        }
        return false;
    }
    
    public boolean isPlaying()  {
        return isPlaying;
    }
    
    /**
     * Resumes the playback if paused, and vice versa.
     */
    public void togglePause()   {
        if (mediaPlayer == null)   {
            return;
        }
        if (mediaPlayer.getStatus().equals(Status.PLAYING))    {
            mediaPlayer.pause();
            isPlaying = false;
        } else  {
            mediaPlayer.play();
            isPlaying = true;
        }
    }
    
    public boolean isOnRepeat() {
        return repeat;
    }
    
    /** 
     * Turns repeat on if it is off, and vice versa.
     * 
     * @return true if repeat was turned on, otherwise false. 
     */
    public boolean toggleRepeat()  {
        if (mediaPlayer == null)    {
            return false;
        } else if (repeat) {
            mediaPlayer.setOnEndOfMedia(new Runnable()  {
                public void run()    {
                } 
            });
            repeat = false;
            return false;
        } else  {
            mediaPlayer.setOnEndOfMedia(new Runnable()  {
                public void run()    {
                    mediaPlayer.seek(Duration.ZERO);
                } 
            });
            repeat = true;
            return true;
        }
    }
    
    /**
     * Pauses audio playback.
     */
    public void pausePlayback()  {
        if (mediaPlayer == null)   {
            return;
        }
        mediaPlayer.pause();
        isPlaying = false;
    }
    
    /**
     * Resumes audio playback.
     */
    public void unpausePlayback()   {
        if (mediaPlayer == null)   {
            return;
        }
        mediaPlayer.play();
        isPlaying = true;
    }
    
    /**
     * Sets the players volume
     * 
     * @param vol double between 0 and 1 
     */
    public void setVolume(double vol)   {
        if (mediaPlayer == null)    {
            return;
        }
        mediaPlayer.setVolume(vol);
    }
    
    
    
}
